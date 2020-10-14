package com.zupbank.bank.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class Address {

    @Column(name = "address_road")
    private String road;
    @Column(name = "address_cep")
    private String cep;
    @Column(name = "address_district")
    private String district;
    @Column(name = "address_complement")
    private String complement;
    @Column(name = "address_city")
    private String city;
    @Column(name = "address_state")
    private String state;
}
