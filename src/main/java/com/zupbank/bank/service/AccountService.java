package com.zupbank.bank.service;

import com.zupbank.bank.controller.dto.AccountDto;
import com.zupbank.bank.domain.VerificationToken;
import com.zupbank.bank.domain.exception.AccountAlreadyExistException;
import com.zupbank.bank.domain.exception.EntidadeNaoEncontradaException;
import com.zupbank.bank.domain.model.Account;
import com.zupbank.bank.domain.model.Proposal;
import com.zupbank.bank.repository.ClientRepository;
import com.zupbank.bank.repository.ProposalRepository;
import com.zupbank.bank.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService implements IAccountService {

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    /*@Transactional*/
    @Override
    public Account registerNewUserAccountAccount(AccountDto accountDto)
            throws AccountAlreadyExistException {

        if (emailExists(accountDto.getEmail())) {
            throw new AccountAlreadyExistException("There is an account with that email address:"
                    + accountDto.getEmail()
            );
        }
        Account account = new Account();

        Proposal proposal = proposalRepository.findById(accountDto.getProposalId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Proprosta não encontrada."));

        proposal.createAccount(account);

        return proposalRepository.save(proposal).getAccount();
    }

    @Override
    public void createVerificationToken(Account account, String token) {
        VerificationToken myToken = new VerificationToken(token, account);
        verificationTokenRepository.save(myToken);
    }

    private boolean emailExists(String email) {
        return clientRepository.findByEmail(email) != null;
    }


}
