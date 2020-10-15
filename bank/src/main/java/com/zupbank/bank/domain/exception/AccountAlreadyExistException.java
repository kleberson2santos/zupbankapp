package com.zupbank.bank.domain.exception;

public class AccountAlreadyExistException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public AccountAlreadyExistException(String mensagem) {
        super(mensagem);
    }
}