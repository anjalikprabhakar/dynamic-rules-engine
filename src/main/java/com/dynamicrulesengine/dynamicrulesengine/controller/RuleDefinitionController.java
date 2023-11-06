package com.dynamicrulesengine.dynamicrulesengine.controller;


import com.dynamicrulesengine.dynamicrulesengine.contract.request.CreateRuleRequest;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.GetRuleDefinitionResponse;
import com.dynamicrulesengine.dynamicrulesengine.service.RuleDefinitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rule")
public class RuleDefinitionController {


    private  final RuleDefinitionService ruleDefinitionService;

    @PostMapping("/create")
    public ResponseEntity<String> createRuleDefinition(@RequestBody CreateRuleRequest createRuleRequest){
        try
        {
            long id= ruleDefinitionService.createRuleDefinition(createRuleRequest);
            return  ResponseEntity.ok("Rule Created Successfully");
        }
        catch (Exception  e){
            e.printStackTrace(); // or log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the rule.");
        }

    }


    @GetMapping()
    public ResponseEntity<List<GetRuleDefinitionResponse>> getRuleDefinition(){
//        try
//        {

            return ResponseEntity.ok(ruleDefinitionService.getRuleDefinition());
//        }
//        catch (Exception  e){
//            e.printStackTrace(); // or log the exception
//            return  "An error occurred while creating the rule";
//        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRuleDefinition(@PathVariable Long id){
        try
        {
            ruleDefinitionService.deleteRuleDefinition(id);
            return  ResponseEntity.ok("Rule Deleted Successfully");
        }
        catch (Exception  e){
            e.printStackTrace(); // or log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the rule.");
        }

    }


}
