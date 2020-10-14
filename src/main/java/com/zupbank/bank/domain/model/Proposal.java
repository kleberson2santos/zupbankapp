package com.zupbank.bank.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zupbank.bank.domain.event.ProposalAcceptedEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "proposal")
public class Proposal extends AbstractAggregateRoot<Proposal> {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
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

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(32) default 'TO_ACCEPT'", nullable = false)
    private StatusAccept accept = StatusAccept.TO_ACCEPT;

    public Proposal() {
    }

    public Proposal(Client client) {
        this.client = client;
    }

    public void accepted() {
        setAccept(StatusAccept.ACCEPTED);

        registerEvent(new ProposalAcceptedEvent(this));
        System.err.println("REGISTRANDO EVENTO ACEITACAO");
    }

}
