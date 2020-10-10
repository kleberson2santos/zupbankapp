package com.zupbank.bank.controller;

import com.zupbank.bank.controller.dto.ClientDTO;
import com.zupbank.bank.domain.CNH;
import com.zupbank.bank.domain.Proposal;
import com.zupbank.bank.domain.exception.EntidadeNaoEncontradaException;
import com.zupbank.bank.domain.exception.NegocioException;
import com.zupbank.bank.repository.CnhRepository;
import com.zupbank.bank.repository.ProposalRepository;
import com.zupbank.bank.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.UUID;

@RestController
@RequestMapping("/v1/proposals")
public class ProposalController {

    @Autowired
    private ProposalService proposalService;

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private CnhRepository cnhRepository;

    @RequestMapping(method = RequestMethod.POST, path = "/request")
    public Proposal requestProposal(@RequestBody ClientDTO client) {
        return proposalService.registerClient(client);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Proposal getProposal(@PathVariable Long id) {
        return proposalRepository.findById(id).orElseThrow(()->new EntidadeNaoEncontradaException("Entidade ou Recurso não encontrado"));
    }

    @RequestMapping(method = RequestMethod.PUT,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            path = "/{id}")
    public Proposal updateCNH(@PathVariable Long id,
                              @RequestParam MultipartFile[] files) throws IOException {

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

            cnhFile.setNomeArquivo(nomeArquivo);
            cnhFile.setContentType(file.getContentType());
            cnhFile.setTamanho(file.getSize());
            cnhFiles.add(cnhFile);
        }
        //TODO: setar o anexo no cliente, pode ser um evento...
        saveProposalWithCnh(id, cnhFiles);//poderia ser um Proposal.of()

        return proposalService.approveProposal(id);
    }

    private void saveProposalWithCnh(Long id, HashSet<CNH.CnhFile> cnhFiles) {
        final Proposal proposal = proposalRepository.findById(id)
                .orElseThrow(() -> new NegocioException("Proposal not found."));

        var cnh = new CNH();
        cnh.setFiles(cnhFiles);
        cnh.setClient(proposal.getClient());
        final CNH save = cnhRepository.save(cnh);
        System.err.println("CNH SALVA!:" + save.getId());

//        proposalService.saveProposal(proposal);
    }

}
