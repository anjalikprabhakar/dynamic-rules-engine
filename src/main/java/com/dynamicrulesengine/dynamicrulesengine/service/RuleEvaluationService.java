package com.dynamicrulesengine.dynamicrulesengine.service;

import com.dynamicrulesengine.dynamicrulesengine.contract.request.DiscountRequest;
import com.dynamicrulesengine.dynamicrulesengine.contract.request.FraudOrder;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.DiscountResponse;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.FraudDetectionResponse;
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

        public FraudDetectionResponse checkOrderForFraud(FraudOrder fraudOrder) {
            if (fraudOrder.getOrder().getAmount() > 5000) {
                return   flagOrderForReview(fraudOrder);
            }
            return new FraudDetectionResponse(fraudOrder.getOrderId(), fraudOrder.getOrder().getAmount(),false);
        }

        private FraudDetectionResponse flagOrderForReview(FraudOrder order) {
            return new FraudDetectionResponse(order.getOrderId(),order.getOrder().getAmount(),true);

        }
}
