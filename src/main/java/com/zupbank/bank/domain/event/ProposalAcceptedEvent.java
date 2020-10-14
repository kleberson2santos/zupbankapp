package com.zupbank.bank.domain.event;

import com.zupbank.bank.domain.model.Proposal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProposalAcceptedEvent {

    private Proposal proposal;
}
