package com.zupbank.bank.domain.listener;

import com.zupbank.bank.domain.event.ProposalAcceptedEvent;
import com.zupbank.bank.domain.model.Proposal;
import com.zupbank.bank.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.zupbank.bank.service.EnvioEmailService.Mensagem;

@Component
public class AccountNotificationCreatedListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @EventListener
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


}
