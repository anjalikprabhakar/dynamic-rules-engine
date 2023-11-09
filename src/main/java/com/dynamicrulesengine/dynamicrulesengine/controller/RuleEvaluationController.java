package com.dynamicrulesengine.dynamicrulesengine.controller;

import com.dynamicrulesengine.dynamicrulesengine.contract.request.DiscountRequest;
import com.dynamicrulesengine.dynamicrulesengine.contract.request.FraudOrder;
import com.dynamicrulesengine.dynamicrulesengine.contract.request.ProductRequest;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.DiscountResponse;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.FraudDetectionResponse;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.ProductResponse;
import com.dynamicrulesengine.dynamicrulesengine.service.RuleEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rule")
@RequiredArgsConstructor
public class RuleEvaluationController {
    private final RuleEvaluationService evaluationService;

    @PostMapping("/discount")
    public ResponseEntity<DiscountResponse> applyDiscountForVIP(@RequestBody DiscountRequest discountRequest){
            return  ResponseEntity.ok(evaluationService.applyDiscountForVIP(discountRequest));
    }
    @PostMapping ("/fraudDetection")
    public FraudDetectionResponse checkOrderForFraud(@RequestBody FraudOrder fraudOrder){
        return evaluationService.checkOrderForFraud(fraudOrder);
    }

    @PostMapping("/replenish")
    public ResponseEntity<ProductResponse> replenishInventory(@RequestBody ProductRequest productRequest){
        return  ResponseEntity.ok(evaluationService.replenishInventory(productRequest));
    }

}
