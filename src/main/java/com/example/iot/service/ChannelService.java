package com.example.iot.service;

import com.example.iot.controller.VO.DeviceChannelForm;
import com.example.iot.controller.VO.TemplateChannelForm;
import com.example.iot.domain.ChannelDataField;
import com.example.iot.domain.DeviceChannel;
import com.example.iot.domain.TemplateChannel;
import com.example.iot.repository.DeviceChannelRepository;
import com.example.iot.repository.TemplateChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ChannelService {
    @Autowired
    DeviceChannelRepository deviceChannelRepository;
    @Autowired
    TemplateChannelRepository templateChannelRepository;
    @Autowired
    ChannelDataFieldService channelDataFieldService;

    public int addDeviceChannel(DeviceChannel deviceChannel){
        DeviceChannel result = deviceChannelRepository.save(deviceChannel);
        return result.getId();
    }

    public int addTemplateChannel(TemplateChannel templateChannel){
        TemplateChannel result = templateChannelRepository.save(templateChannel);
        return result.getId();
    }

    public int addTemplateChannel(int channelType, int templateId, String channelName){
        TemplateChannel templateChannel  = new TemplateChannel();
        templateChannel.setChannelType(channelType);
        templateChannel.setTemplateId(templateId);
        templateChannel.setChannelName(channelName);
        return addTemplateChannel(templateChannel);
    }

    public int addDeviceChannel(int channelType, int deviceId, int templateChannelId, String channelName){
        DeviceChannel deviceChannel = new DeviceChannel();
        deviceChannel.setChannelType(channelType);
        deviceChannel.setDeviceId(deviceId);
        deviceChannel.setTemplateChannelId(templateChannelId);
        deviceChannel.setChannelName(channelName);
        return addDeviceChannel(deviceChannel);
    }

    public int addTemplateChannel(TemplateChannelForm templateChannelForm){
        TemplateChannel templateChannel = createTemplateChannel(templateChannelForm);
        return addTemplateChannel(templateChannel);
    }

    public int addDeviceChannel(DeviceChannelForm deviceChannelForm){
        DeviceChannel deviceChannel = createDeviceChannel(deviceChannelForm);
        return addDeviceChannel(deviceChannel);
    }

    public void addChannelsToNewlyAddedDevice(int deviceId, int templateId){
        ArrayList<TemplateChannel> templateChannels = getTemplateChannelsByTemplateId(templateId);
        if(templateChannels != null && templateChannels.size() > 0){
            for(int i = 0; i < templateChannels.size(); i++){
                addDeviceChannel(templateChannels.get(i).getChannelType(),
                        deviceId, templateChannels.get(i).getId(),
                        templateChannels.get(i).getChannelName());
            }
        }
    }

    public ArrayList<TemplateChannel> getTemplateChannelsByTemplateId(int templateId){
        return templateChannelRepository.getAllByTemplateId(templateId);
    }

    public TemplateChannel getTemplateChannelById(int templateChannelId){
        return templateChannelRepository.getById(templateChannelId);
    }

    public DeviceChannel getDeviceChannelById(int deviceChannelId){
        return deviceChannelRepository.getById(deviceChannelId);
    }

    public List<TemplateChannel> getAllTemplateChannels(){
        return templateChannelRepository.findAll();
    }

    public List<DeviceChannel> getAllDeviceChannels() {
        return deviceChannelRepository.findAll();
    }

    public int updateTemplateChannel(TemplateChannelForm templateChannelForm){
        TemplateChannel templateChannel = createTemplateChannel(templateChannelForm);
        templateChannel.setId(templateChannelForm.getId());
        ArrayList<DeviceChannel> deviceChannels = deviceChannelRepository.getAllByTemplateChannelId(templateChannelForm.getId());
        if(deviceChannels != null & deviceChannels.size() > 0){
            for(int i = 0; i < deviceChannels.size(); i++){
                deviceChannels.get(i).setChannelName(templateChannelForm.getChannelName());
                addDeviceChannel(deviceChannels.get(i));
            }
        }
        return addTemplateChannel(templateChannel);
    }

    public int updateDeviceChannel(DeviceChannelForm deviceChannelForm){
        DeviceChannel deviceChannel = createDeviceChannel(deviceChannelForm);
        deviceChannel.setId(deviceChannelForm.getId());
        return addDeviceChannel(deviceChannel);
    }

    public void deleteTemplateChannel(int templateChannelId){
        ArrayList<DeviceChannel> deviceChannels = deviceChannelRepository.getAllByTemplateChannelId(templateChannelId);
        if(deviceChannels != null & deviceChannels.size() > 0){
            for(int i = 0; i < deviceChannels.size(); i++){
                deviceChannelRepository.deleteById(deviceChannels.get(i).getId());
            }
        }
        channelDataFieldService.deleteChannelRelatedFields(templateChannelId, 0);
        templateChannelRepository.deleteById(templateChannelId);
    }

    public void deleteDeviceChannel(int deviceChannelId){
        deviceChannelRepository.deleteById(deviceChannelId);
        channelDataFieldService.deleteChannelRelatedFields(deviceChannelId, 1);
    }

    private TemplateChannel createTemplateChannel(TemplateChannelForm templateChannelForm){
        TemplateChannel templateChannel = new TemplateChannel();
        templateChannel.setChannelType(templateChannelForm.getChannelType());
        templateChannel.setTemplateId(templateChannelForm.getTemplateId());
        templateChannel.setChannelName(templateChannelForm.getChannelName());
        return templateChannel;
    }

    private DeviceChannel createDeviceChannel(DeviceChannelForm deviceChannelForm) {
        DeviceChannel deviceChannel = new DeviceChannel();
        deviceChannel.setChannelType(deviceChannelForm.getChannelType());
        deviceChannel.setDeviceId(deviceChannelForm.getDeviceId());
        deviceChannel.setTemplateChannelId(deviceChannelForm.getTemplateChannelId());
        deviceChannel.setChannelName(deviceChannelForm.getChannelName());
        return deviceChannel;
    }

}
