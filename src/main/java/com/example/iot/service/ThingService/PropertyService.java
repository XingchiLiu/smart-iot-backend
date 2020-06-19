package com.example.iot.service.ThingService;

import com.alibaba.fastjson.JSONObject;
import com.example.iot.domain.Device;
import com.example.iot.domain.thing.TSL;
import com.example.iot.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/16
 * @description: 物模型，属性服务
 */
@Service
public class PropertyService {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    DeviceRepository deviceRepository;

    /**
     * 根据设备id获取 物模型-属性 列表
     * @param deviceId
     * @return
     */
    public JSONObject getPropertyList(int deviceId){
        Device device = deviceRepository.findById(deviceId).get();
        int templateId = device.getTemplateId();
        Query query = new Query(Criteria.where("templateId").is(templateId));
        TSL templateTSL = mongoTemplate.findOne(query, TSL.class);
        JSONObject jsonObject = templateTSL.getContent();
        jsonObject.remove("events");
        jsonObject.remove("services");
        jsonObject.remove("schema");
        return jsonObject;
    }

    /**
     * 根据设备id、属性标识符、时间上下限获取上报的属性历史
     * @param deviceId
     * @param identifier
     * @param days 最近多少天的数据
     * @return
     */
    public JSONObject getPropertyHistory(int deviceId, String identifier, int days){
        return null;
    }
}
