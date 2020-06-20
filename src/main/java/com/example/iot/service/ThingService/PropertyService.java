package com.example.iot.service.ThingService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.iot.domain.Device;
import com.example.iot.domain.thing.Property;
import com.example.iot.domain.thing.TSL;
import com.example.iot.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

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
    public JSONArray getPropertyList(int deviceId){
        Device device = deviceRepository.findById(deviceId).get();
        int templateId = device.getTemplateId();
        Query query = new Query(Criteria.where("templateId").is(templateId));
        TSL templateTSL = mongoTemplate.findOne(query, TSL.class);
        if(templateTSL==null){
            return new JSONArray();
        }
        JSONArray propertyList = templateTSL.getContent().getJSONArray("properties");
        for(Object property: propertyList){
            JSONObject propertyJSON = new JSONObject((LinkedHashMap)property);
            String identifier = propertyJSON.getString("identifier");
            List<Property> propertyHistory = getPropertyHistory(deviceId, identifier);
            //获得条数
            propertyJSON.put("num", propertyHistory.size());
            //获得最近更新时间
            Date lastPost = new Date(0);
            for(Property property1: propertyHistory){
                if(property1.getTime().after(lastPost)){
                    lastPost = property1.getTime();
                }
            }
            propertyJSON.put("lastPost", lastPost);
        }
        return propertyList;
    }

    /**
     * 根据设备id、属性标识符获取上报的属性历史
     * @param deviceId
     * @param identifier
     * @return
     */
    public List<Property> getPropertyHistory(int deviceId, String identifier){
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        query.addCriteria(Criteria.where("functionInfo.identifier").is(identifier));
        List<Property> propertyList = mongoTemplate.find(query, Property.class);
        return propertyList;
    }
}
