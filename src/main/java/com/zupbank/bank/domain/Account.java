package com.zupbank.bank.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String number;
    @OneToOne
    @JoinColumn(name = "proposal_id")
    private Proposal proposal;

    public Account() {
    }
}
