package com.zupbank.bank.infrastructure.service.email;

import com.zupbank.bank.core.email.EmailProperties;
import com.zupbank.bank.service.EnvioEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void enviar(Mensagem mensagem) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        System.err.println(" Enviar Email...");
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setFrom(emailProperties.getRemetente());
            helper.setSubject(mensagem.getAssunto());
            helper.setText(mensagem.getCorpo(), true);
            System.err.println("From:" + emailProperties.getRemetente());
            System.err.println("To:" + mensagem.getDestinatarios().stream().findFirst().get());

        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar e-mail", e);
        }

        mailSender.send(mimeMessage);

    }

    protected String processarTemplate(Mensagem mensagem) {
        log.info("Processar o template aqui");
        return "Template a construir";
    }
}
