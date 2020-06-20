package com.example.iot.repository.rule;

import com.example.iot.domain.rule.RuleLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author Karson
 * @CreateDate 2020/6/19 15:50
 */
public interface RuleLogRepository extends JpaRepository<RuleLog, Integer> {
    public List<RuleLog> getAllByRuleIdOrderByCreateTimeDesc(Pageable pageable, int ruleId);
}
