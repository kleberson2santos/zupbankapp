package com.zupbank.bank.client;

import com.zupbank.bank.domain.model.Proposal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("approver")
public interface ApprovalClient {

    @RequestMapping(path = "/v1/approval", method = RequestMethod.PUT)
    Proposal getApproval(@RequestBody Proposal proposal);
}
