package com.dynamicrulesengine.dynamicrulesengine.contract.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FraudDetectionResponse {
    private String orderId;
    private double amount;
    private boolean flag;



}
