package com.zupbank.bank.controller;

import com.zupbank.bank.controller.dto.ClientDTO;
import com.zupbank.bank.domain.Account;
import com.zupbank.bank.domain.CNH;
import com.zupbank.bank.domain.Proposal;
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
import java.io.IOException;
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

    @RequestMapping(method = RequestMethod.POST, path = "/request")
    public Proposal stepOne(@RequestBody ClientDTO client) {
        return proposalService.registerClient(client);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Proposal getProposal(@PathVariable Long id) {
        return proposalService.getById(id);
    }

    @RequestMapping(method = RequestMethod.PUT,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            path = "/{id}")
    public ResponseEntity<Proposal> stepThree(@PathVariable Long id,
                                              @RequestParam MultipartFile[] files) throws IOException {

        if (Arrays.stream(files).count() == 0) {
            throw new NegocioException("Files is mandatory.");
        }
        if (!proposalRepository.existsById(id)) {
            throw new EntityNotFoundException("Resource not found.");
        }
        //TODO:validar se os passos anterirores estao completos aqui
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

        return ResponseEntity.of(Optional.of(saveProposalWithCnh(id, cnhFiles)));
    }

    private Proposal saveProposalWithCnh(Long id, HashSet<CNH.CnhFile> cnhFiles) {
        final Proposal proposal = proposalRepository.findById(id)//deveria fazer eager no cliente
                .orElseThrow(() -> new NegocioException("Proposal not found."));

//        final CNH cnh = cnhRepository.findAll().stream()
//                .filter(doc -> doc.getId().equals(proposal.getClient().getId()))
//                .findFirst()
//                .orElse(new CNH());
//
//        cnh.setFiles(cnhFiles);
//        cnh.setClient(proposal.getClient());
//        System.err.println("SALVANDO ANEXOS...");
//        cnhRepository.save(cnh);

        return proposalService.approveProposal(proposal);
    }

    //TODO: ENDPOINT NAO VAI SER USADO EXTERNAMENTE APAGAR DEPOIS
    @RequestMapping(method = RequestMethod.POST, path = "/{id}/accounts")
    public Proposal saveAccount(@PathVariable Long id, @RequestBody Account account) {
        return accountService.save(account, id);
    }


}
