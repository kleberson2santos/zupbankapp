package com.zupbank.bank.service;

import com.zupbank.bank.client.ApprovalClient;
import com.zupbank.bank.controller.dto.AddressDTO;
import com.zupbank.bank.controller.dto.ClientDTO;
import com.zupbank.bank.domain.Client;
import com.zupbank.bank.domain.Proposal;
import com.zupbank.bank.repository.ClientRepository;
import com.zupbank.bank.repository.ProposalRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ProposalService {

    private static final Logger LOG = LoggerFactory.getLogger(ProposalService.class);

    @Autowired
    ProposalRepository proposalRespository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private ApprovalClient approvalClient;

    public Proposal registerClient(ClientDTO clientDTO) {

        var proposal = getProposal(clientDTO);

        LOG.info("Salvando proposta e cliente: {} ", proposal.getClient().getName());
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
        //TODO: Registrar Endereco
        System.err.println("Registra Endereco...");
    }

    public Proposal approveProposal(Long id) {
        LOG.info("Aprovando proposta {}", id);
        final Proposal proposal = proposalRespository.findById(id).orElseThrow(() -> new EntityNotFoundException("Resource not found."));

        HttpEntity<Proposal> httpEntity = new HttpEntity<Proposal>(proposal);
//
        final String approvalStatus = approvalClient.getApproval(proposal);

        //TODO: retornar do Fallback
        LOG.info("Status da proposta: {}", approvalStatus);
        return proposal;
    }

    public Proposal saveProposal(Proposal proposal) {
        return proposalRespository.save(proposal);
    }

}
