package com.dynamicrulesengine.dynamicrulesengine.repository;

import com.dynamicrulesengine.dynamicrulesengine.model.RuleDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RuleDefinitionRepository  extends JpaRepository<RuleDefinition,Long> {

    RuleDefinition findById(long id);


}
