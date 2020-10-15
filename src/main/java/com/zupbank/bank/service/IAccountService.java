package com.zupbank.bank.service;

import com.zupbank.bank.controller.dto.AccountDto;
import com.zupbank.bank.domain.model.Account;

public interface IAccountService {

    Account registerNewUserAccountAccount(AccountDto accountDto);

    public void createVerificationToken(Account account, String token);
}
