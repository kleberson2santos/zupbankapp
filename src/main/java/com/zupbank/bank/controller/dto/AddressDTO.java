package com.zupbank.bank.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    private String cep;
    private String road;
    private String district;
    private String complement;
    private String city;
    private String state;

}
