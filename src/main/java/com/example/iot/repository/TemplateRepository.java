package com.example.iot.repository;

import com.example.iot.domain.DeviceTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/27
 * @description:
 */
@Repository
public interface TemplateRepository extends JpaRepository<DeviceTemplate, Integer> {

    //根据名字返回模板
    Optional<DeviceTemplate> findByName(String name);

    //根据模板名字查找
    @Query("select t from DeviceTemplate t where t.name like %?1%")
    List<DeviceTemplate> findByNameLike(String name);

}
