package com.example.iot.repository;

import com.example.iot.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/2
 * @description:
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    //根据模板Id和设备名获取设备
    Optional<Device> findFirstByTemplateIdAndName(int templateId, String name);

    //根据设备名查找设备
    List<Device> findAllByNameContaining(String name);

    // 根据模板获得其下所有设备
    List<Device> getAllByTemplateId(int templateId);
}
