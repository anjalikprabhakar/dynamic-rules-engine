package com.dynamicrulesengine.dynamicrulesengine.contract.request;


import lombok.Data;

@Data
public class CreateRuleRequest {
    private String description;
    private String  condition;
}
