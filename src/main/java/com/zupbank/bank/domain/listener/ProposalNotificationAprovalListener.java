package com.zupbank.bank.domain.listener;

import com.zupbank.bank.domain.event.ProposalAcceptedEvent;
import com.zupbank.bank.domain.model.Proposal;
import com.zupbank.bank.repository.ProposalRepository;
import com.zupbank.bank.service.EnvioEmailService;
import com.zupbank.bank.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.persistence.EntityNotFoundException;

import static com.zupbank.bank.service.EnvioEmailService.Mensagem;

@Component
class ProposalNotificationAcceptListener {

    @Autowired
    ProposalRepository proposalRepository;
    @Autowired
    private EnvioEmailService envioEmailService;
    @Autowired
    private ProposalService proposalService;

    /*
     * Envia email de proposta aceita e cria conta;
     * */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void aoAceitarProposta(ProposalAcceptedEvent event) {

        final Proposal proposalFound = proposalRepository.findById(event.getProposal().getId())
                .orElseThrow(() -> new EntityNotFoundException("Proposta nao encontrada com o id passado no envento."));
        proposalRepository.save(proposalFound);


        var mensagem = Mensagem.builder()
                .assunto("ZupBank - Proposta aceita.")
                .destinatario(proposalFound.getClient().getEmail())
                .corpo("Em breve você será notificado da criação da conta.")
//                .variavel("proposal", proposal)
                .build();

        envioEmailService.enviar(mensagem);

    }

}
