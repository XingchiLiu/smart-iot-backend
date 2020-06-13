package com.example.iot.repository;

import com.example.iot.domain.ReceivedMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<ReceivedMessage,String> {
}
