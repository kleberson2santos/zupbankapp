package com.zupbank.bank.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@Entity
@Table(name = "proposal")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id") // voltar temp , nullable = false
    private Client client;


    @OneToOne
    @JoinColumn(name = "acount_id")
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(32) default 'PENDING'", nullable = false)
    private StatusProposal status = StatusProposal.PENDING;

    public Proposal() {
    }

    public Proposal(Client client) {
        this.client = client;
    }
}
