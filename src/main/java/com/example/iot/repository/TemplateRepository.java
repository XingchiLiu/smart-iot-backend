package com.example.iot.repository;

import com.example.iot.domain.DeviceTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/27
 * @description:
 */
@Repository
public interface TemplateRepository extends JpaRepository<DeviceTemplate, Integer> {

}
