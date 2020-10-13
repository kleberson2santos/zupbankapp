package com.zupbank.bank.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClientDTO {

    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String cnh;
    private LocalDate birth;
    private AddressDTO address;

}
