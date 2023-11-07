package com.dynamicrulesengine.dynamicrulesengine.service;


import com.dynamicrulesengine.dynamicrulesengine.contract.request.CreateRuleRequest;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.GetRuleDefinitionResponse;
import com.dynamicrulesengine.dynamicrulesengine.model.RuleDefinition;
import com.dynamicrulesengine.dynamicrulesengine.repository.RuleDefinitionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RuleDefinitionService {

    private  final RuleDefinitionRepository ruleDefinitionRepository;

    @Autowired
    private  final ModelMapper modelMapper= new ModelMapper();




    public Long createRuleDefinition(CreateRuleRequest request){

        RuleDefinition ruleDefinition= RuleDefinition.builder()
                .description(request.getDescription())
                .condition(request.getCondition())
                .build();

         ruleDefinition=ruleDefinitionRepository.save(ruleDefinition);
         return ruleDefinition.getId();

    }

    public List<GetRuleDefinitionResponse> getRuleDefinition(){

        List<RuleDefinition> ruleDefinitionResponses=ruleDefinitionRepository.findAll();

        return  ruleDefinitionResponses.stream()
                .map(ruleDefinitionResponse->modelMapper.map(ruleDefinitionResponse, GetRuleDefinitionResponse.class))
                .collect(Collectors.toList());


    }

    public void deleteRuleDefinition(Long id){
        ruleDefinitionRepository.findById(id).
        orElseThrow(()->new RuntimeException("rule definition not found"));
        ruleDefinitionRepository.deleteById(id);

    }
}
