package com.zupbank.bank.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClientDTO {

    private String nome;
    private String sobrenome;
    private String email;
    private String cnh;
    private LocalDate dataNascimento;

}
