package com.zupbank.bank.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@Data
@Entity
@Table(name = "proposal")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_proposal_client"))
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "acount_id",
            foreignKey = @ForeignKey(name = "fk_proposal_account"))
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
