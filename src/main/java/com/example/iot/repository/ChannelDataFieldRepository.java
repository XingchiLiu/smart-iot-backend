package com.example.iot.repository;

import com.example.iot.domain.ChannelDataField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelDataFieldRepository extends JpaRepository<ChannelDataField, Integer> {
    ChannelDataField getChannelDataFieldById(int id);

    List<ChannelDataField> getAllByChannelId(int channelId);

    void deleteAllByChannelIdAndChannelType(int channelId, int channelType);
}
