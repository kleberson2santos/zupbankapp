package com.zupbank.approver.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProposalInput {

    private Long id;

    private ClientDTO client;

    private StatusProposal status;

}
