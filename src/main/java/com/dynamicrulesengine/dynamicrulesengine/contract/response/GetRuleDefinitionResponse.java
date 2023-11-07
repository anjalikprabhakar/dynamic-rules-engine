package com.dynamicrulesengine.dynamicrulesengine.contract.response;


import lombok.Data;

@Data
public class GetRuleDefinitionResponse {

    private Long id;

    private String description;

    private String condition;

}
