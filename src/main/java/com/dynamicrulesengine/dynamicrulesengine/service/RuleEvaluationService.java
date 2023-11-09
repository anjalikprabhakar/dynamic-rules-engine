package com.dynamicrulesengine.dynamicrulesengine.service;

import com.dynamicrulesengine.dynamicrulesengine.contract.request.DiscountRequest;
import com.dynamicrulesengine.dynamicrulesengine.contract.request.FraudOrder;
import com.dynamicrulesengine.dynamicrulesengine.contract.request.ProductRequest;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.DiscountResponse;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.FraudDetectionResponse;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public class RuleEvaluationService {
    public DiscountResponse applyDiscountForVIP(DiscountRequest discountRequest){
        double amount = discountRequest.getOrder().getTotal();

        if(discountRequest.getCustomer().getStatus().equals("VIP") && amount > 100.0) {
            double discountAmount = amount * 0.10;
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


    public ProductResponse replenishInventory(ProductRequest productRequest){
        int stockLevel = productRequest.getProduct().getStockLevel();
        if(stockLevel < 10){
            stockLevel = stockLevel + 50;
        }
        ProductResponse response = ProductResponse.builder()
                .productId(productRequest.getProductId())
                .stockLevel(stockLevel)
                .build();
        return response;
    }
}
