package com.example.iot.service.ThingService;

import com.example.iot.domain.thing.Event;
import com.example.iot.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/16
 * @description:
 */
@Service
public class EventService {
    @Autowired
    MongoTemplate mongoTemplate;

    public List<Event> getEventHistory(int deviceId){
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        query.with(Sort.by(Sort.Direction.DESC, "time"));
        List<Event> eventList = mongoTemplate.find(query, Event.class);
        return eventList;
    }
}
