package com.example.iot.repository;

import com.example.iot.domain.DeviceGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/2
 * @description:
 */
@Repository
public interface GroupRepository extends JpaRepository<DeviceGroup, Integer> {
}
