package com.zupbank.bank.controller;

import com.zupbank.bank.api.ResourceUriHelper;
import com.zupbank.bank.controller.dto.AddressDTO;
import com.zupbank.bank.controller.dto.ClientDTO;
import com.zupbank.bank.domain.Account;
import com.zupbank.bank.domain.CNH;
import com.zupbank.bank.domain.Proposal;
import com.zupbank.bank.domain.StatusProposal;
import com.zupbank.bank.domain.exception.NegocioException;
import com.zupbank.bank.repository.CnhRepository;
import com.zupbank.bank.repository.ProposalRepository;
import com.zupbank.bank.service.AccountService;
import com.zupbank.bank.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/proposals")
public class ProposalController {

    @Autowired
    private ProposalService proposalService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private CnhRepository cnhRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Proposal stepOne(@RequestBody ClientDTO client) {
        System.err.println("REQUEST");
        final Proposal proposal = proposalService.registerClient(client);
        ResourceUriHelper.addUriInResponseHeader(proposal.getId());
        return proposal;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/step-2")
    public Proposal stepTwo(@PathVariable Long id, @RequestBody AddressDTO address) {
        final Proposal proposal = proposalService.registerAdress(address, id);
        ResourceUriHelper.addUriInResponseHeaderStep2(proposal.getId());
        return proposal;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Proposal getProposal(@PathVariable Long id) {
        return proposalService.getById(id);
    }

    @RequestMapping(method = RequestMethod.PUT,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            path = "/{id}/step-3")
    public ResponseEntity<Proposal> stepThree(@PathVariable Long id,
                                              @RequestParam MultipartFile[] files,
                                              HttpServletResponse response
    ) {

        if (Arrays.stream(files).count() == 0) {
            throw new NegocioException("Files is mandatory.");
        }
        if (!proposalRepository.existsById(id)) {
            throw new EntityNotFoundException("Resource not found.");
        }
        //TODO:validar se os passos anterirores estao completos aqui
        final Proposal proposal = proposalRepository.findById(id)
                .orElseThrow(() -> new NegocioException("Proposal not found."));

        Proposal proposalSaved = saveCnhFiles(files, proposal);
        final Proposal proposalApproved = approve(proposalSaved);
        if (StatusProposal.APPROVED.equals(proposalApproved.getStatus())) {
            ResourceUriHelper.addUriInResponseHeaderStep3(proposal.getId());
        }

        return ResponseEntity.of(Optional.of(proposalApproved));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}/accounts")
    public Proposal saveAccount(@PathVariable Long id, @RequestBody Account account) {
        return accountService.save(account, id);
    }

    private Proposal approve(Proposal proposal) {
        return proposalService.approveProposal(proposal);
    }


    private Proposal saveCnhFiles(MultipartFile[] files, Proposal proposal) {
        var cnhFiles = new HashSet<CNH.CnhFile>();
        for (MultipartFile file : files) {
            var cnhFile = new CNH.CnhFile();
            var nomeArquivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            var arquivoFoto = Path.of("C:\\Users\\Kleberson\\catalogo", nomeArquivo);
            try {
                file.transferTo(arquivoFoto);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            cnhFile.setFileName(nomeArquivo);
            cnhFile.setContentType(file.getContentType());
            cnhFile.setSize(file.getSize());
            cnhFiles.add(cnhFile);
        }

        final CNH cnh = cnhRepository.findAll().stream()
                .filter(doc -> doc.getId().equals(proposal.getClient().getId()))
                .findFirst().orElse(new CNH(cnhFiles, proposal.getClient()));

        cnhRepository.save(cnh);

        return proposal;
    }

}
