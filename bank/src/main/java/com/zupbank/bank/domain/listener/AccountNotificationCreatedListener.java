package com.zupbank.bank.domain.listener;

import com.zupbank.bank.domain.event.ProposalAccountCreatedEvent;
import com.zupbank.bank.domain.model.Proposal;
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

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void aoAceitarPropostaCriarConta(ProposalAccountCreatedEvent event) {
        final Proposal proposal = event.getProposal();

        var mensagem = Mensagem.builder()
                .assunto("ZupBank - sua conta na agência " + proposal.getAccount().getAgency()
                        + " foi criada com sucesso.")
                .destinatario(proposal.getClient().getEmail())
                .corpo("Por favor crie seu primeiro acesso em nosso site.")
                /*.variavel("proposal", proposal)*/
                .build();

        envioEmailService.enviar(mensagem);

    }


}
