package com.example.iot.repository;

import com.example.iot.domain.DeviceMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceMessageRepository extends MongoRepository<DeviceMessage, String> {
}
