package com.zupbank.approver.service;

import com.zupbank.approver.controller.dto.Proposal;
import com.zupbank.approver.controller.dto.ProposalInput;
import com.zupbank.approver.controller.dto.StatusProposal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExternalAprover {

	private static final Logger LOG = LoggerFactory.getLogger(ExternalAprover.class);

	public Proposal approve(Proposal proposal) {

		proposal.setStatus(StatusProposal.APPROVED);
		LOG.info("PROPOSTA APROVADA.");

		return proposal;
	}
	
	
}
