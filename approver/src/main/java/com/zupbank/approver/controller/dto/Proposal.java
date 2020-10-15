package com.zupbank.approver.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Proposal {

    private Long id;

    private Client client;

    private StatusAccept accept;

    private StatusProposal status;
}
