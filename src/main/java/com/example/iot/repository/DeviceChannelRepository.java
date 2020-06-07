package com.example.iot.repository;

import com.example.iot.domain.DeviceChannel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface DeviceChannelRepository extends JpaRepository<DeviceChannel, Integer> {
    ArrayList<DeviceChannel> getAllByDeviceId(int deviceId);

    ArrayList<DeviceChannel> getAllByTemplateChannelId(int templateChannelId);

    ArrayList<DeviceChannel> getAllByChannelType(int channelType);

    DeviceChannel getById(int id);
}
