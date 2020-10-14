package com.zupbank.bank.service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Set;


public interface EnvioEmailService {
    //TODO: alterar tudo para p padrao ingles
    void enviar(Mensagem mensagem);

    @Getter
    @Builder
    class Mensagem {

        @Singular
        private Set<String> destinatarios;

        @NonNull
        private String assunto;

        @NonNull
        private String corpo;
    }

}
