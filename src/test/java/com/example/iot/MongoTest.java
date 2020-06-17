package com.example.iot;

import com.alibaba.fastjson.JSON;
import com.example.iot.domain.thing.TSL;
import com.example.iot.model.TestForMongo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/21
 * @description:
 */
public class MongoTest extends IotApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void test1() {
        TestForMongo test = new TestForMongo();
        test.setName("lxc");
        test.setInfo("test");
        mongoTemplate.save(test);
        Query query = new Query(Criteria.where("name").is("lxc"));
        TestForMongo result = mongoTemplate.findOne(query, TestForMongo.class);
        assertNotNull(result);
        System.out.println(result);
        assertEquals("test", result.getInfo());
    }

    @Test
    public void test2(){
        TSL tsl = new TSL(0);
        mongoTemplate.save(tsl);
    }
}
