package com.zupbank.bank.controller;

import com.zupbank.bank.api.ResourceUriHelper;
import com.zupbank.bank.controller.dto.AddressDTO;
import com.zupbank.bank.controller.dto.ClientDTO;
import com.zupbank.bank.domain.exception.NegocioException;
import com.zupbank.bank.domain.model.CNH;
import com.zupbank.bank.domain.model.Proposal;
import com.zupbank.bank.repository.CnhRepository;
import com.zupbank.bank.repository.ProposalRepository;
import com.zupbank.bank.service.IAccountService;
import com.zupbank.bank.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
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
    private IAccountService IAccountService;

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private CnhRepository cnhRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Proposal stepOne(@RequestBody ClientDTO client) {
        final Proposal proposal = proposalService.registerClient(client);
        ResourceUriHelper.addUriInResponseHeader(proposal.getId());
        return proposal;
    }

    @RequestMapping(path = "/{id}/step-2", method = RequestMethod.PUT)
    public Proposal stepTwo(@PathVariable Long id, @RequestBody AddressDTO address) {
        var proposal = proposalService.registerAdress(address, id);
        ResourceUriHelper.addUriInResponseHeaderStep2(proposal.getId());
        return proposal;
    }

    @RequestMapping(path = "/{id}/step-3", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Proposal> stepThree(@PathVariable Long id, @RequestParam MultipartFile[] files) {
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

        ResourceUriHelper.addUriInResponseHeaderStep3(proposal.getId());

        return ResponseEntity.of(Optional.of(proposalSaved));
    }

    @RequestMapping(path = "/{id}/accept", method = RequestMethod.PUT)
    public Proposal acceptProposal(@PathVariable Long id) {
        final Proposal proposal = proposalService.accept(id); // EventListener
        //TODO: deveria ser um event para nao bloquear request
        final Proposal proposalApproved = proposalService.approve(proposal); // TransactionalEventListener
        ResourceUriHelper.addUriInResponseHeaderToAccept(proposal.getId());
        return proposalApproved;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Proposal getProposal(@PathVariable Long id) {
        return proposalService.getProposalById(id);
    }

    private Proposal saveCnhFiles(MultipartFile[] files, Proposal proposal) {
        var cnhFiles = new HashSet<CNH.CnhFile>();
        for (MultipartFile file : files) {
            var cnhFile = new CNH.CnhFile();
            var nomeArquivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            //TODO: Alterar caminho em producao
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

        var cnh = cnhRepository.findAll().stream()
                .filter(doc -> doc.getId().equals(proposal.getClient().getId()))
                .findFirst().orElse(new CNH(cnhFiles, proposal.getClient()));

        cnhRepository.save(cnh);
        return proposal;
    }

}
