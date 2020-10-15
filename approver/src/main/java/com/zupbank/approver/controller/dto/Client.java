package com.zupbank.approver.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Client {

    private Long id;

    @NotNull
    private String name;

    private String lastname;

    @Email
    private String email;

    private String cnh;
    private LocalDate birth;
    private Address address;
}
