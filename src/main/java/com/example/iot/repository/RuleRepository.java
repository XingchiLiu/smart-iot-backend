package com.example.iot.repository;

import com.example.iot.domain.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @author Karson
 * @CreateDate 2020/6/7 18:05
 */

@Transactional
public interface RuleRepository extends JpaRepository<Rule, Integer> {

    ArrayList<Rule> getAllByDeviceId(int deviceId);

    //TODO 补充规则的CRUD
}
