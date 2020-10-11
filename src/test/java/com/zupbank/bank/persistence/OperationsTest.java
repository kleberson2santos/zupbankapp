package com.zupbank.bank.persistence;

import com.zupbank.bank.domain.Account;
import com.zupbank.bank.domain.Client;
import com.zupbank.bank.domain.Proposal;
import com.zupbank.bank.repository.ClientRepository;
import com.zupbank.bank.repository.ProposalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OperationsTest {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ClientRepository clientRepository;


    @Test
    public void given_proposal_when_persist_then_proposal_saved() {
        Client newClient = new Client();
        newClient.setName("Maria");

        final Client client = clientRepository.save(newClient);
        Proposal newProposal = new Proposal(client);

        final Proposal proposal = proposalRepository.save(newProposal);

        Assertions.assertNotNull(proposal);
        Assertions.assertNotNull(proposal.getClient());
    }

    @Test
    public void given_proposal_when_approved_then_account_created() {
        final Proposal proposalFound = proposalRepository.findById(1L).get();

        Account newAccount = new Account();
        proposalFound.setAccount(newAccount);

        final Proposal proposal = proposalRepository.save(proposalFound);

        Assertions.assertNotNull(proposal);
        Assertions.assertNotNull(proposal.getClient().getId());
        Assertions.assertNotNull(proposal.getAccount().getId());
    }
}
