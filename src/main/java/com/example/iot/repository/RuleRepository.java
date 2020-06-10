package com.example.iot.repository;

import com.example.iot.domain.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @author Karson
 * @CreateDate 2020/6/7 18:05
 */

@Transactional
public interface RuleRepository extends JpaRepository<Rule, Integer> {

    ArrayList<Rule> getAllByDeviceId(int deviceId);
}
