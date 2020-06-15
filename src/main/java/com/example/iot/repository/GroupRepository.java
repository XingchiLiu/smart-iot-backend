package com.example.iot.repository;

import com.example.iot.domain.DeviceGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/2
 * @description:
 */
@Repository
public interface GroupRepository extends JpaRepository<DeviceGroup, Integer> {

    /**
     * 以下是对表device_and_group的操作
     * (device_id, group_id)
     */
    //插入一条映射关系
    @Query(value = "insert into device_and_group (device_id, group_id) values (?1, ?2)", nativeQuery = true)
    void insertDeviceGroup(int deviceId, int groupId);

    @Query(value = "select device_id from device_and_group where group_id=?1", nativeQuery = true)
    List<Integer> findDeviceIdListByGroupId(int groupId);

}
