package com.zupbank.bank.domain.listener;

import com.zupbank.bank.domain.event.ProposalAcceptedEvent;
import com.zupbank.bank.domain.event.ProposalAccountCreatedEvent;
import com.zupbank.bank.domain.model.Proposal;
import com.zupbank.bank.service.AccountService;
import com.zupbank.bank.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.zupbank.bank.service.EnvioEmailService.Mensagem;

@Component
public class AccountNotificationCreatedListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @Autowired
    private AccountService accountService;


    @TransactionalEventListener
    public void aoAceitarProposta(ProposalAcceptedEvent event) {
        System.err.println("PROPOSTA ACEITA ENVIANDO EMAIL...");
        Proposal proposal = event.getProposal();

        var mensagem = Mensagem.builder()
                .assunto("ZupBank - sua conta está sendo criada.")
                .destinatario(proposal.getClient().getEmail())
                .corpo("Em breve você receberá um email com o link para cadastrar sua senha.")
//                .variavel("proposal", proposal)
                .build();

        envioEmailService.enviar(mensagem);

    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void aoAceitarPropostaCriarConta(ProposalAccountCreatedEvent event) {
        System.err.println(">>CRIAR CONTA E ENVIAR LINK POR EMAIL...");
        final Proposal proposal = event.getProposal();
//        accountService.createAccount(proposal);

        var mensagem = Mensagem.builder()
                .assunto("ZupBank - sua conta foi criada com sucesso.")
                .destinatario(proposal.getClient().getEmail())
                .corpo("Por favor crie sua senha em nosso site.")
//                .variavel("proposal", proposal)
                .build();

        envioEmailService.enviar(mensagem);

    }


}
