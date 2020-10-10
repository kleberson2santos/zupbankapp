package com.zupbank.bank.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "proposal")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @OneToOne(mappedBy = "proposal", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
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
