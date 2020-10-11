package com.zupbank.bank.service;

import com.zupbank.bank.domain.Account;
import com.zupbank.bank.domain.Proposal;
import com.zupbank.bank.domain.exception.EntidadeNaoEncontradaException;
import com.zupbank.bank.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    ProposalRepository proposalRepository;

    public Proposal save(Account account, Long proposalId) {
        final Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Proposal not found."));
        proposal.setAccount(account);

        return proposalRepository.save(proposal);

    }
}
