package com.dynamicrulesengine.dynamicrulesengine.contract.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MembershipRenewalResponse {
    private String customerId;
    private int loyaltyPoints;
    private String membershipType;
}
