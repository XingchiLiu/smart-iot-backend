package com.example.iot.repository;

import com.example.iot.domain.ChannelDataField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelDataFieldRepository extends JpaRepository<ChannelDataField, Integer> {
    ChannelDataField getChannelDataFieldById(int id);

    List<ChannelDataField> getAllByChannelId(int channelId);
    
    List<ChannelDataField> getAllByChannelIdAndChannelType(int channelId, int channelType);

    void deleteAllByChannelIdAndChannelType(int channelId, int channelType);
}
