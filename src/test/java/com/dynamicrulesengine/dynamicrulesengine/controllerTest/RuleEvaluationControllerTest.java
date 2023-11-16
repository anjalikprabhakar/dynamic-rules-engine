package com.dynamicrulesengine.dynamicrulesengine.controllerTest;
import com.dynamicrulesengine.dynamicrulesengine.contract.request.*;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.DiscountResponse;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.FraudDetectionResponse;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.MembershipRenewalResponse;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.ProductResponse;
import com.dynamicrulesengine.dynamicrulesengine.controller.RuleEvaluationController;
import com.dynamicrulesengine.dynamicrulesengine.service.RuleEvaluationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;


@SpringBootTest(classes = {RuleEvaluationController.class})
@AutoConfigureMockMvc
public class RuleEvaluationControllerTest {
    @Autowired
    private RuleEvaluationController ruleEvaluationController;

    @MockBean
    private RuleEvaluationService ruleEvaluationService;
    @Test
    void testApplyDiscountForVIP() throws Exception {
        DiscountResponse buildResult = DiscountResponse.builder().orderId("42").totalAmount(10.0d).build();
        when(ruleEvaluationService.applyDiscountForVIP(Mockito.<DiscountRequest>any())).thenReturn(buildResult);

        Customer customer = new Customer();
        customer.setId("42");
        customer.setName("Name");
        customer.setStatus("Status");

        DiscountRequest discountRequest = new DiscountRequest();
        discountRequest.setCustomer(customer);
        discountRequest.setOrder(new Order());
        discountRequest.setOrderId("42");
        String content = (new ObjectMapper()).writeValueAsString(discountRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/rule/discount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(ruleEvaluationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"orderId\":\"42\",\"totalAmount\":10.0}"));
    }
    @Test
    void testCheckOrderForFraud() throws Exception {
        FraudDetectionResponse buildResult = FraudDetectionResponse.builder()
                .amount(10.0d)
                .flag(true)
                .orderId("42")
                .build();
        when(ruleEvaluationService.checkOrderForFraud(Mockito.<FraudOrder>any())).thenReturn(buildResult);

        Customer customer = new Customer();
        customer.setId("42");
        customer.setName("Name");
        customer.setStatus("Status");

        FraudOrder fraudOrder = new FraudOrder();
        fraudOrder.setCustomer(customer);
        fraudOrder.setOrder(new FraudOrderDetails());
        fraudOrder.setOrderId("42");
        String content = (new ObjectMapper()).writeValueAsString(fraudOrder);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/rule/fraudDetection")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(ruleEvaluationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"orderId\":\"42\",\"amount\":10.0,\"flag\":true}"));
    }
    @Test
    void testCheckOrderForFraudException() throws Exception {
        FraudDetectionResponse buildResult = FraudDetectionResponse.builder()
                .amount(10.0d)
                .flag(false)
                .orderId("42")
                .build();
        when(ruleEvaluationService.checkOrderForFraud(Mockito.<FraudOrder>any())).thenReturn(buildResult);

        Customer customer = new Customer();
        customer.setId("42");
        customer.setName("Name");
        customer.setStatus("Status");

        FraudOrder fraudOrder = new FraudOrder();
        fraudOrder.setCustomer(customer);
        fraudOrder.setOrder(new FraudOrderDetails());
        fraudOrder.setOrderId("42");
        String content = (new ObjectMapper()).writeValueAsString(fraudOrder);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/rule/fraudDetection")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(ruleEvaluationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"orderId\":\"42\",\"amount\":10.0,\"flag\":false}"));
    }
    @Test
    void testReplenishInventory() throws Exception {
        ProductResponse buildResult = ProductResponse.builder().productId("42").stockLevel(1).build();
        when(ruleEvaluationService.replenishInventory(Mockito.<ProductRequest>any())).thenReturn(buildResult);
        Product product = new Product();
        product.setName("Name");
        product.setStockLevel(1);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProduct(product);
        productRequest.setProductId("42");
        String content = (new ObjectMapper()).writeValueAsString(productRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/rule/replenish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(ruleEvaluationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"productId\":\"42\",\"stockLevel\":1}"));
    }
    @Test
    void testRenewMembership() throws Exception {
        MembershipRenewalResponse buildResult = MembershipRenewalResponse.builder()
                .customerId("42")
                .loyaltyPoints(1)
                .membershipType("Membership Type")
                .build();
        when(ruleEvaluationService.renewMembership(Mockito.<MembershipRenewalRequest>any())).thenReturn(buildResult);

        MembershipDetails membershipDetails = new MembershipDetails();
        membershipDetails.setLoyaltyPoints(1);
        membershipDetails.setMembershipType("Membership Type");

        MembershipRenewalRequest membershipRenewalRequest = new MembershipRenewalRequest();
        membershipRenewalRequest.setCustomerId("42");
        membershipRenewalRequest.setMembershipDetails(membershipDetails);
        String content = (new ObjectMapper()).writeValueAsString(membershipRenewalRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/rule/renewal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(ruleEvaluationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"customerId\":\"42\",\"loyaltyPoints\":1,\"membershipType\":\"Membership Type\"}"));
    }

}
