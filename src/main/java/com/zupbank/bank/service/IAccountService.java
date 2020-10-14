package com.zupbank.bank.service;

import com.zupbank.bank.controller.dto.AccountDto;
import com.zupbank.bank.domain.exception.AccountAlreadyExistException;
import com.zupbank.bank.domain.model.Account;
import org.springframework.transaction.annotation.Transactional;

public interface IAccountService {

    @Transactional
    Account registerNewUserAccount(AccountDto accountDto)
            throws AccountAlreadyExistException;

    void createVerificationToken(Account account, String token);
}
