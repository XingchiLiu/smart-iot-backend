package com.example.iot.repository.rule;

import com.example.iot.domain.rule.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @author Karson
 * @CreateDate 2020/6/7 18:05
 */

@Transactional
public interface RuleRepository extends JpaRepository<Rule, Integer> {

    ArrayList<Rule> getAllByDeviceId(int deviceId);

    @Query("select r from Rule r")
    ArrayList<Rule> get();
}
