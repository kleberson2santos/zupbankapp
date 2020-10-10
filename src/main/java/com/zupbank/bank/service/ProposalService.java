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

@Service
public class ProposalService {

    @Autowired
    ProposalRepository proposalRespository;

    @Autowired
    ClientRepository clientRepository;

    public Proposal registerClient(ClientDTO clientDTO) {

        var proposal = getProposal(clientDTO);

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

    public void uploadFile(byte[] file) {

        System.err.println("Upload do arquivo...");
        System.err.println("Emite evento para o aprovador gerar a conta...");
        approveProposal(new Client());
    }

    private Proposal approveProposal(Client client) {
        System.err.println("Chamar serviço externo de aprovação...");

        Proposal proposal = new Proposal(client);
        HttpEntity<Proposal> httpEntity = new HttpEntity<Proposal>(proposal);

        RestTemplate restClient = new RestTemplate();
        ResponseEntity<String> exchange = restClient.exchange("http://localhost:8081/v1/approval", HttpMethod.POST, httpEntity, String.class);

        System.err.println("REST TEMPLATE:" + exchange.getBody());

        return new Proposal(client);
    }

    public Proposal saveProposal(Proposal proposal) {
        return proposalRespository.save(proposal);
    }

}
