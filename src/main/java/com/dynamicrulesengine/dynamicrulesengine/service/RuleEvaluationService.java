package com.dynamicrulesengine.dynamicrulesengine.service;

import com.dynamicrulesengine.dynamicrulesengine.contract.request.DiscountRequest;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.DiscountResponse;
import org.springframework.stereotype.Service;

@Service
public class RuleEvaluationService {
    public DiscountResponse applyDiscountForVIP(DiscountRequest discountRequest){
        double amount = discountRequest.getOrder().getTotal();
        double discountAmount = amount * 0.10;
        if(discountRequest.getCustomer().getStatus().equals("VIP") && amount > 100.0) {
           amount = amount - discountAmount;
        }
        DiscountResponse discountResponse = DiscountResponse.builder()
                .orderId(discountRequest.getOrderId())
                .totalAmount(amount)
                .build();
        return discountResponse;
    }
}
