package com.zupbank.bank.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zupbank.bank.client.ApprovalClient;
import com.zupbank.bank.controller.dto.AddressDTO;
import com.zupbank.bank.controller.dto.ClientDTO;
import com.zupbank.bank.domain.exception.EntidadeNaoEncontradaException;
import com.zupbank.bank.domain.model.*;
import com.zupbank.bank.repository.ClientRepository;
import com.zupbank.bank.repository.ProposalRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class ProposalService {

    private static final Logger LOG = LoggerFactory.getLogger(ProposalService.class);

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private ApprovalClient approvalClient;

    @Autowired
    private ModelMapper modelMapper;

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

    public Proposal registerAdress(AddressDTO addressDTO, Long idProposal) {
        Proposal proposal = proposalRepository.findById(idProposal)
                .orElseThrow(EntityNotFoundException::new);

        Address address = addressToEntity(addressDTO);
        proposal.getClient().setAddress(address);
        return proposalRepository.save(proposal);
    }

    @HystrixCommand(threadPoolKey = "getByIdThreatPool")
    public Proposal getProposalById(Long id) {
        return proposalRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade ou Recurso não encontrado"));
    }

    //    @HystrixCommand(
//            allbackMethod = "approveProposalFallback",
//            threadPoolKey = "approveProposalThreadPool")
    public Proposal approveProposal(Proposal proposal) {
        LOG.info("TENTATIVA DE APROVAÇÃO PARA A PROPOSTA {}", proposal.getId());
//        final CNH cnhUploaded = uploadClient.upFiles(files);
        var proposalApproved = approvalClient.getApproval(proposal);
        proposalApproved.setAccept(proposal.getAccept());

        var account = new Account();
        proposalApproved.createAccount(account);
        return proposalRepository.save(proposalApproved);

//        final Account accountCreated = accountClient.createAccount(proposalApproved);

    }

    public Proposal approveProposalFallback(Proposal proposal) {
        if (StatusProposal.APPROVED.equals(proposal.getStatus())) {
            return proposalRepository.findById(proposal.getId()).get();
        }
        proposal.setStatus(StatusProposal.PENDING);
        return proposal;
    }

    public Proposal saveProposal(Proposal proposal) {
        return proposalRepository.save(proposal);
    }

    private Address addressToEntity(AddressDTO addressDTO) {
        return modelMapper.map(addressDTO, Address.class);
    }

    @Transactional
    public Proposal accept(Long id) {
        var proposal = getProposalById(id);
        proposal.accepted();
        return proposalRepository.save(proposal);
    }

}
