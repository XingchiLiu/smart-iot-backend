package com.example.iot.service;

import com.example.iot.domain.Device;
import com.example.iot.domain.DeviceChannel;
import com.example.iot.domain.TemplateChannel;
import com.example.iot.repository.DeviceChannelRepository;
import com.example.iot.repository.TemplateChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class ChannelService {
    @Autowired
    DeviceChannelRepository deviceChannelRepository;
    @Autowired
    TemplateChannelRepository templateChannelRepository;

    public int addDeviceChannel(DeviceChannel deviceChannel){
        DeviceChannel result = deviceChannelRepository.save(deviceChannel);
        return result.getId();
    }

    public int addTemplateChannel(TemplateChannel templateChannel){
        TemplateChannel result = templateChannelRepository.save(templateChannel);
        return result.getId();
    }

    public ArrayList<TemplateChannel> getTemplateChannelsByTemplateId(int templateId){
        return templateChannelRepository.getAllByTemplateId(templateId);
    }

    public int addTemplateChannel(int channelType, int templateId){
        TemplateChannel templateChannel  = new TemplateChannel();
        templateChannel.setChannelType(channelType);
        templateChannel.setTemplateId(templateId);
        return addTemplateChannel(templateChannel);
    }

    public int addDeviceChannel(int channelType, int deviceId, int templateChannelId){
        DeviceChannel deviceChannel = new DeviceChannel();
        deviceChannel.setChannelType(channelType);
        deviceChannel.setDeviceId(deviceId);
        deviceChannel.setTemplateChannelId(templateChannelId);
        return addDeviceChannel(deviceChannel);
    }

    public void addChannelsToNewlyAddedDevice(int deviceId, int templateId){
        ArrayList<TemplateChannel> templateChannels = getTemplateChannelsByTemplateId(templateId);
        if(templateChannels != null && templateChannels.size() > 0){
            for(int i = 0; i < templateChannels.size(); i++){
                addDeviceChannel(templateChannels.get(i).getChannelType(), deviceId, templateChannels.get(i).getId());
            }
        }
    }
}
