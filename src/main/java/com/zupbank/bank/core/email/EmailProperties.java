package com.zupbank.bank.core.email;

//import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("zupbank.email")
public class EmailProperties {

    //Fake será o valor default
    private Implementacao impl = Implementacao.SMTP;

    private String remetente;

    public enum Implementacao {
        SMTP, FAKE
    }


}
