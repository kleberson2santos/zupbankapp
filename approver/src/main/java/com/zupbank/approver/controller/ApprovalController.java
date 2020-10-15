package com.zupbank.approver.controller;

import com.zupbank.approver.controller.dto.Proposal;
import com.zupbank.approver.controller.dto.ProposalInput;
import com.zupbank.approver.service.ExternalAprover;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/approval")
public class ApprovalController {

    private static final Logger LOG = LoggerFactory.getLogger(ApprovalController.class);

    @Autowired
    private ExternalAprover externalApprover;

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Proposal> getApproval(@RequestBody Proposal proposal) {
		/*if (isServiceUnavailable()) {
			LOG.info("SERVIÇO INDISPONIVEL!!!");
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}*/
        return ResponseEntity.ok(externalApprover.approve(proposal));
    }

	/*private boolean isServiceUnavailable() {
		final int value = 0 + (int) (Math.random() * ((2 - 0) + 1));
		LOG.info("RANDON: {}", value);
		return value != 0;
	}*/

}
