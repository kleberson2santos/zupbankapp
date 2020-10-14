package com.zupbank.bank.domain.listener;

import com.zupbank.bank.domain.event.ProposalAcceptedEvent;
import com.zupbank.bank.domain.model.Proposal;
import com.zupbank.bank.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.zupbank.bank.service.EnvioEmailService.Mensagem;

@Component
public class ProposalNotificationCreatedListener {

    @Autowired
    private EnvioEmailService envioEmailService;


    @TransactionalEventListener
    public void aoAceitarProposta(ProposalAcceptedEvent event) {
        System.err.println("PROPOSTA ACEITA -> ENVIANDO EMAIL...");
        Proposal proposal = event.getProposal();

        var mensagem = Mensagem.builder()
                .assunto("ZupBank - Proposta aceita.")
                .destinatario(proposal.getClient().getEmail())
                .corpo("Em breve você será notificado da criação da conta.")
//                .variavel("proposal", proposal)
                .build();

        envioEmailService.enviar(mensagem);

    }
}
