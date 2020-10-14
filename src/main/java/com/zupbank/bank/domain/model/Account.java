package com.zupbank.bank.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String number;

    @JsonIgnore
    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private Proposal proposal;

    public Account() {
    }
}
