package com.zupbank.bank.service;

import com.zupbank.bank.domain.model.Account;
import com.zupbank.bank.domain.model.Proposal;
import com.zupbank.bank.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    ProposalRepository proposalRepository;

    //    public Proposal accept(Long id) {
//        var proposal = getProposalById(id);
//        proposal.accepted();
//        return proposalRepository.save(proposal);
//    }
    @Transactional
    public Proposal createAccount(Proposal proposal) {
        System.err.println("CRIAR CONTA PARA A PROPOSTA:" + proposal.getId());
        var account = new Account();
        System.err.println("Conta criada:" + account.getAgency());
//        proposal.createAccount(account);
        proposal.setAccount(account);
        return proposalRepository.save(proposal);

    }

}
