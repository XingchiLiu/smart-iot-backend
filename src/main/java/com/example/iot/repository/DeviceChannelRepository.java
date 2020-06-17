package com.example.iot.repository;

import com.example.iot.domain.DeviceChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface DeviceChannelRepository extends JpaRepository<DeviceChannel, Integer> {
    ArrayList<DeviceChannel> getAllByDeviceId(int deviceId);

    ArrayList<DeviceChannel> getAllByTemplateChannelId(int templateChannelId);

    ArrayList<DeviceChannel> getAllByChannelType(int channelType);

    DeviceChannel getByIdAndDeviceIdAndChannelType(int id, int deviceId, int channelType);

    DeviceChannel getById(int id);

    ArrayList<DeviceChannel> getAllByDeviceIdAndTemplateChannelId(int deviceId, int templateChannelId);

    DeviceChannel getByDeviceIdAndChannelTypeAndChannelName(int deviceId, int channelType, String channelName);
}
