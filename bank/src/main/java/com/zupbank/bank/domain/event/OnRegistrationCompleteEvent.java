package com.zupbank.bank.domain.event;

import com.zupbank.bank.domain.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;

@AllArgsConstructor
@Getter
public class OnRegistrationCompleteEvent {

    private Account account;
    private String appUrl;
    private Locale locale;

    public OnRegistrationCompleteEvent(
            Account user, Locale locale, String appUrl) {

        this.account = account;
        this.locale = locale;
        this.appUrl = appUrl;
    }

}
