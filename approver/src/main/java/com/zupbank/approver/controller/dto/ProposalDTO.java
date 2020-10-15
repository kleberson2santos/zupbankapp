package com.zupbank.approver.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProposalDTO {	

	public ProposalDTO() {
	}

	public ProposalDTO(ClientDTO client) {
		this.client = client;
	}

	private Long id;

	private ClientDTO client;

	private AccountDTO account;

	private StatusProposal status;
}
