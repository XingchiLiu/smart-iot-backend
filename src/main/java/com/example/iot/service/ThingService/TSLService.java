package com.example.iot.service.ThingService;

import com.alibaba.fastjson.JSONObject;
import com.example.iot.domain.thing.TSL;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/16
 * @description:
 */
@Service
public class TSLService {

    @Autowired
    MongoTemplate mongoTemplate;

    public TSL getTSLByTemplateId(int templateId){
        Query query = new Query(Criteria.where("templateId").is(templateId));
        TSL result = mongoTemplate.findOne(query, TSL.class);
        return result;
    }

    public TSL updateTSL(int templateId, JSONObject content){
        Query query = new Query(Criteria.where("templateId").is(templateId));
        TSL result = mongoTemplate.findOne(query, TSL.class);
        result.setUpdateTime(new Date());
        result.setContent(content);

        mongoTemplate.save(result);
        return result;
    }
}
