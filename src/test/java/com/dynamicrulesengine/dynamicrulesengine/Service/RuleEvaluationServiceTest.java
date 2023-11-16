package com.dynamicrulesengine.dynamicrulesengine.Service;

import com.dynamicrulesengine.dynamicrulesengine.contract.request.*;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.DiscountResponse;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.MembershipRenewalResponse;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.ProductResponse;
import com.dynamicrulesengine.dynamicrulesengine.service.RuleEvaluationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RuleEvaluationServiceTest {
    @InjectMocks
    private RuleEvaluationService ruleEvaluationService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testApplyDiscountForVIP() {
        Customer customer = new Customer();
        customer.setStatus("VIP");

        Order order = new Order();
        order.setTotal(150.0);

        DiscountRequest discountRequest = new DiscountRequest();
        discountRequest.setOrderId("123456");
        discountRequest.setCustomer(customer);
        discountRequest.setOrder(order);

        DiscountResponse discountResponse = ruleEvaluationService.applyDiscountForVIP(discountRequest);

        assertEquals("123456", discountResponse.getOrderId());
        assertEquals(135.0, discountResponse.getTotalAmount());
    }

    @Test
    public void testReplenishInventory() {
        Product product = new Product();
        product.setName("Wireless Mouse");
        product.setStockLevel(8);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductId("prod-305");
        productRequest.setProduct(product);

        ProductResponse productResponse = ruleEvaluationService.replenishInventory(productRequest);


        assertEquals("prod-305", productResponse.getProductId());
        assertEquals(58, productResponse.getStockLevel());
    }

    @Test
    public void testRenewMembershipPremium() {
        MembershipDetails membershipDetails = new MembershipDetails();
        membershipDetails.setLoyaltyPoints(1200);

        MembershipRenewalRequest renewalRequest = new MembershipRenewalRequest();
        renewalRequest.setCustomerId("cust303");
        renewalRequest.setMembershipDetails(membershipDetails);

        MembershipRenewalResponse renewalResponse = ruleEvaluationService.renewMembership(renewalRequest);

        assertEquals("cust303", renewalResponse.getCustomerId());
        assertEquals(1200, renewalResponse.getLoyaltyPoints());
        assertEquals("PREMIUM", renewalResponse.getMembershipType());
    }
}
