package com.example.iot.repository;

import com.example.iot.domain.DeviceDemand;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceDemandRepository extends MongoRepository<DeviceDemand,String> {
}
