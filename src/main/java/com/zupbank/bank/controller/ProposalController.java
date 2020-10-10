package com.zupbank.bank.controller;

import com.zupbank.bank.controller.dto.ClientDTO;
import com.zupbank.bank.domain.Proposal;
import com.zupbank.bank.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/v1/proposals")
public class ProposalController {

    @Autowired
    private ProposalService proposalService;

    @RequestMapping(method = RequestMethod.POST, path = "/request")
    public Proposal requestProposal(@RequestBody ClientDTO client) {
        return proposalService.registerClient(client);
    }

    @RequestMapping(method = RequestMethod.PUT,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            path = "/{id}")
    public void updateCNH(@PathVariable Long id,
                          @RequestParam MultipartFile[] files) throws IOException {

        //TODO:validar se os passos anterirores estao completos aqui
        for (MultipartFile file : files) {
            var nomeArquivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            var arquivoFoto = Path.of("C:\\Users\\Kleberson\\catalogo", nomeArquivo);

            System.err.println(arquivoFoto);
            System.err.println(file.getContentType());

            try {
                file.transferTo(arquivoFoto);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

//        proposalService.uploadFile(arquivo.getBytes());
    }

}
