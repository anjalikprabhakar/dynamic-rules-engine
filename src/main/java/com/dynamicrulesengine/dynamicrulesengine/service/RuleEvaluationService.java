package com.dynamicrulesengine.dynamicrulesengine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import com.dynamicrulesengine.dynamicrulesengine.contract.request.*;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.DiscountResponse;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.FraudDetectionResponse;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.MembershipRenewalResponse;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.ProductResponse;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class RuleEvaluationService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;;
    @Value("${notification.recipient}")
    private String recipient;

    @Value("${notification.message}")
    private String message;


    public DiscountResponse applyDiscountForVIP(DiscountRequest discountRequest){
        double amount = discountRequest.getOrder().getTotal();

        if(discountRequest.getCustomer().getStatus().equals("VIP") && amount > 100.0) {
            double discountAmount = amount * 0.10;
            amount = amount - discountAmount;
        }
        return DiscountResponse.builder()
                .orderId(discountRequest.getOrderId())
                .totalAmount(amount)
                .build();
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
        return ProductResponse.builder()
                .productId(productRequest.getProductId())
                .stockLevel(stockLevel)
                .build();
    }

    public MembershipRenewalResponse renewMembership(MembershipRenewalRequest membershipRenewalRequest){
        if(membershipRenewalRequest.getMembershipDetails().getLoyaltyPoints() >= 1000){
            membershipRenewalRequest.getMembershipDetails().setMembershipType("PREMIUM");
        }
        return MembershipRenewalResponse.builder()
                .customerId(membershipRenewalRequest.getCustomerId())
                .loyaltyPoints(membershipRenewalRequest.getMembershipDetails().getLoyaltyPoints())
                .membershipType(membershipRenewalRequest.getMembershipDetails().getMembershipType())
                .build();
    }

    public Boolean sendLowStockNotification(ProductRequest productRequest) {
        if (productRequest.getProduct().getStockLevel() < 5) {
            SimpleMailMessage mailMessage= new SimpleMailMessage();

            String content = message.replace("{{productId}}", productRequest.getProductId());
            String subject="Low Stock";
            mailMessage.setFrom(sender);
            mailMessage.setTo(recipient);
            mailMessage.setText(content);
            mailMessage.setSubject(subject);

            javaMailSender.send(mailMessage);
            return true;

        }
        return false;
    }
}
