package com.zupbank.bank.domain.listener;

import com.zupbank.bank.domain.event.OnRegistrationCompleteEvent;
import com.zupbank.bank.domain.model.Account;
import com.zupbank.bank.domain.model.Client;
import com.zupbank.bank.service.AccountService;
import com.zupbank.bank.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.zupbank.bank.service.EnvioEmailService.Mensagem;

@Component
public class OnRegistrationCompleteListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private AccountService service;

    @Autowired
    private MessageSource message;

    @Autowired
    private EnvioEmailService envioEmailService;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    //TODO: só pode enviar email de a conta foi savla com o token
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        Account account = event.getAccount();
        Client client = account.getProposal().getClient();
        String token = UUID.randomUUID().toString().substring(0, 8);

        service.createVerificationToken(account, token);
        String confirmationUrl
                = event.getAppUrl() + "/register?token=" + token;
        String uri = "http://localhost:8080/v1/accounts" + confirmationUrl; //TODO: pegar dinamicamente context ou enviar somente o token

        /*String recipientAddress = account.getEmail();
        String subject = "Registration Confirmation";
        String message = messages.getMessage("message.regSucc", null, event.getLocale());*/

        /*SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
        mailSender.send(email);*/

        Mensagem mensagem = Mensagem.builder()
                .assunto("ZupBank - Registre seu primeiro acesso!")
                .destinatario(client.getEmail())
                .corpo("Cliente com cpf começando com " + /*client.getCpf().substring(0, 3)*/ 123 + "." +
                        "<br> link de acesso <strong>" + uri + "</strong>" +
                        " para registrar suas credenciais de acesso.")
                .build();
        envioEmailService.enviar(mensagem);
    }
}

