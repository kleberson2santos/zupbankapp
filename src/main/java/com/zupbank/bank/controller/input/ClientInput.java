package com.zupbank.bank.controller.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@AllArgsConstructor
public class ClientInput {
    @NonNull
    private String email;

    @NonNull
    private String cpf;
}
