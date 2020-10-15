package com.zupbank.approver.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    private String road;
    private String cep;
    private String district;
    private String complement;
    private String city;
    private String state;
}
