package com.example.iot.repository;

import com.example.iot.domain.DeviceChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface DeviceChannelRepository extends JpaRepository<DeviceChannel, Integer> {
    ArrayList<DeviceChannel> getAllByDeviceId(int deviceId);

    ArrayList<DeviceChannel> getAllByTemplateChannelId(int templateChannelId);

    ArrayList<DeviceChannel> getAllByChannelType(int channelType);

    @Modifying
    @Query("delete from DeviceChannel where templateChannelId = ?1")
    void deleteAllByTemplateChannelId(int templateChannelId);

    DeviceChannel getById(int id);
}
