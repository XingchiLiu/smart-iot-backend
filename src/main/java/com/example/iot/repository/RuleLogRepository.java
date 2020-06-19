package com.example.iot.repository;

import com.example.iot.domain.RuleLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author Karson
 * @CreateDate 2020/6/19 15:50
 */
public interface RuleLogRepository extends JpaRepository<RuleLog, Integer> {
    public List<RuleLog> getAllByRuleIdOrderByCreateTimeDesc(int ruleId);
}
