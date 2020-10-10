package com.zupbank.bank.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    private String cep;
    private String rua;
    private String bairro;
    private String complemento;
    private String cidade;
    private String estado;

}
