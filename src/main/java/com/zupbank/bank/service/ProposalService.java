package com.zupbank.bank.service;

import com.zupbank.bank.controller.dto.AddressDTO;
import com.zupbank.bank.controller.dto.ClientDTO;
import com.zupbank.bank.domain.Client;
import com.zupbank.bank.domain.Proposal;
import com.zupbank.bank.repository.ClientRepository;
import com.zupbank.bank.repository.ProposalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;

@Service
public class ProposalService {

    @Autowired
    ProposalRepository proposalRespository;

    @Autowired
    ClientRepository clientRepository;

    public Proposal registerClient(ClientDTO clientDTO) {

        var proposal = getProposal(clientDTO);

        System.err.println("PROSTA E CLIENTE SALVOS...");
        return saveProposal(proposal);

    }

    private Proposal getProposal(ClientDTO clientDTO) {
        assert clientDTO != null : "ClientDTO is null";
        final Client client = clientRepository.save(toEntity(clientDTO));

        Proposal proposal = new Proposal(client);
        return proposal;
    }

    private Client toEntity(ClientDTO clientDTO) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(clientDTO, Client.class);
    }

    public void registerAdress(AddressDTO address) {

        System.err.println("Registra Endereco...");
    }

    public Proposal approveProposal(Long id) {
        System.err.println("Chamando serviço externo de aprovação...");
        final Proposal proposal = proposalRespository.findById(id).orElseThrow(() -> new EntityNotFoundException("Resource not found."));

        HttpEntity<Proposal> httpEntity = new HttpEntity<Proposal>(proposal);

        RestTemplate restClient = new RestTemplate();
        ResponseEntity<String> exchange = restClient.exchange("http://localhost:8081/v1/approval", HttpMethod.PUT, httpEntity, String.class);

        //TODO: retornar do Histrix
        System.err.println("RETURN:" + exchange.getBody());

        return proposal;
    }

    public Proposal saveProposal(Proposal proposal) {
        return proposalRespository.save(proposal);
    }

}
