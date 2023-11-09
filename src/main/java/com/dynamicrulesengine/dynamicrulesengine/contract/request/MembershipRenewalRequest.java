package com.dynamicrulesengine.dynamicrulesengine.contract.request;

import lombok.Data;

@Data
public class MembershipRenewalRequest {
    private String customerId;
    private MembershipDetails membershipDetails;
}
