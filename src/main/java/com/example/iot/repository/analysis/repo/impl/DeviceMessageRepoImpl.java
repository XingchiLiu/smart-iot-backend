package com.example.iot.repository.analysis.repo.impl;

import com.example.iot.domain.DeviceMessage;
import com.example.iot.repository.analysis.repo.DeviceMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhm
 */
@Repository
public class DeviceMessageRepoImpl implements DeviceMessageRepo {
    private MongoTemplate mongoTemplate;

    @Autowired
    public DeviceMessageRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public DeviceMessage getLastMsgByTopic(String topic) {
        Criteria criteria = Criteria.where("topic").is(topic);
        Query query = Query.query(criteria)
                .with(Sort.by("date").descending());
        return mongoTemplate.findOne(query, DeviceMessage.class, "device_message");
    }

    @Override
    public List<DeviceMessage> getAllMsgByTopic(String topic) {
        Criteria criteria = Criteria.where("topic").is(topic);
        Query query = Query.query(criteria);
        return mongoTemplate.find(query, DeviceMessage.class, "device_message");
    }

    @Override
    public List<DeviceMessage> getMsgByTopicAndTimeInterval(String topic, LocalDateTime startTime, LocalDateTime endTime) {
        Criteria criteria = Criteria.where("topic").is(topic)
                .andOperator(Criteria.where("date").gte(startTime).lte(endTime));
        Query query = Query.query(criteria);
        return mongoTemplate.find(query, DeviceMessage.class, "device_message");
    }
}
