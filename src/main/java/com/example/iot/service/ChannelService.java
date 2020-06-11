package com.example.iot.service;

import com.example.iot.controller.VO.DeviceChannelForm;
import com.example.iot.controller.VO.TemplateChannelForm;
import com.example.iot.domain.Device;
import com.example.iot.domain.DeviceChannel;
import com.example.iot.domain.TemplateChannel;
import com.example.iot.repository.DeviceChannelRepository;
import com.example.iot.repository.TemplateChannelRepository;
import com.example.iot.service.ConnectionService.connection.connectionImpl.MQTTConnectionService;
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
    @Autowired
    MQTTConnectionService mqttConnectionService;
    @Autowired
    DeviceService deviceService;

    public int addDeviceChannel(DeviceChannel deviceChannel){
        DeviceChannel result = deviceChannelRepository.save(deviceChannel);
        return result.getId();
    }

    public int addTemplateChannel(TemplateChannel templateChannel){
        TemplateChannel result = templateChannelRepository.save(templateChannel);
        return result.getId();
    }

    /**
     * 再新建一个模板时添加的模板数据通道。因为此时还没有设备，所以不需要订阅。
     * @param channelType
     * @param templateId
     * @param channelName
     * @return
     */
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

        String deviceName = deviceService.getDeviceById(deviceId).getName();
        mqttConnectionService.addSub("/" + deviceName + "/" + channelName, 0);
        return addDeviceChannel(deviceChannel);
    }

    public int addTemplateChannel(TemplateChannelForm templateChannelForm){
        TemplateChannel templateChannel = createTemplateChannel(templateChannelForm);
        // TODO 下属设备数据通道和消息订阅添加
        return addTemplateChannel(templateChannel);
    }

    public int addDeviceChannel(DeviceChannelForm deviceChannelForm){
        DeviceChannel deviceChannel = createDeviceChannel(deviceChannelForm);
        String deviceName = deviceService.getDeviceById(deviceChannelForm.getDeviceId()).getName();

        mqttConnectionService.addSub("/" + deviceName + "/" + deviceChannelForm.getChannelName(),0);
        return addDeviceChannel(deviceChannel);
    }

    /**
     * 给新添加的设备加上模板包含的数据通道
     * @param deviceId
     * @param templateId
     */
    public void addChannelsToNewlyAddedDevice(int deviceId, int templateId){
        ArrayList<String> topicsToAdd = new ArrayList<>();
        String deviceName = deviceService.getDeviceById(deviceId).getName();

        ArrayList<TemplateChannel> templateChannels = getTemplateChannelsByTemplateId(templateId);
        if(templateChannels != null && templateChannels.size() > 0){
            for(int i = 0; i < templateChannels.size(); i++){
                addDeviceChannel(templateChannels.get(i).getChannelType(),
                        deviceId, templateChannels.get(i).getId(),
                        templateChannels.get(i).getChannelName());
                topicsToAdd.add("/" + deviceName + "/" + templateChannels.get(i).getChannelName());
            }
        }
        mqttConnectionService.addSub(topicsToAdd.toArray(new String[topicsToAdd.size()]),0);
    }

    public ArrayList<TemplateChannel> getTemplateChannelsByTemplateId(int templateId){
        return templateChannelRepository.getAllByTemplateId(templateId);
    }

    public ArrayList<DeviceChannel> getDeviceChannelByTemplateChannelId(int templateChannelId){
        return deviceChannelRepository.getAllByTemplateChannelId(templateChannelId);
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

    /**
     * 更新模板数据通道的【名字】，会一并更新对应的数据设备通道
     * @param templateChannelForm
     * @return
     */
    public int updateTemplateChannel(TemplateChannelForm templateChannelForm){
        ArrayList<String> topicsToDelete = new ArrayList<>();
        ArrayList<String> topicsToAdd = new ArrayList<>();

        String oldChannelName = getTemplateChannelById(templateChannelForm.getId()).getChannelName();
        String newChannelName = templateChannelForm.getChannelName();

        TemplateChannel templateChannel = createTemplateChannel(templateChannelForm);
        templateChannel.setId(templateChannelForm.getId());
        ArrayList<DeviceChannel> deviceChannels = deviceChannelRepository.getAllByTemplateChannelId(templateChannelForm.getId());
        if(deviceChannels != null & deviceChannels.size() > 0){
            for(int i = 0; i < deviceChannels.size(); i++){
                deviceChannels.get(i).setChannelName(templateChannelForm.getChannelName());
                addDeviceChannel(deviceChannels.get(i));

                String deviceName = deviceService.getDeviceById(deviceChannels.get(i).getDeviceId()).getName();
                topicsToAdd.add("/" + deviceName + "/" + newChannelName);
                topicsToDelete.add("/" + deviceName + "/" + oldChannelName);
            }

            mqttConnectionService.removeSub(topicsToDelete.toArray(new String[topicsToDelete.size()]));
            mqttConnectionService.addSub(topicsToAdd.toArray(new String[topicsToAdd.size()]), 0);
        }
        return addTemplateChannel(templateChannel);
    }

    /**
     * 更新设备数据通道的【名字】
     * @param deviceChannelForm
     * @return
     */
    public int updateDeviceChannel(DeviceChannelForm deviceChannelForm){
        String originalName = getDeviceChannelById(deviceChannelForm.getId()).getChannelName();
        String newName = deviceChannelForm.getChannelName();
        String deviceName = deviceService.getDeviceById(deviceChannelForm.getDeviceId()).getName();

        DeviceChannel deviceChannel = createDeviceChannel(deviceChannelForm);
        deviceChannel.setId(deviceChannelForm.getId());

        mqttConnectionService.removeSub("/" + deviceName + "/" + originalName);
        mqttConnectionService.addSub("/" + deviceName + "/" + newName,0);
        return addDeviceChannel(deviceChannel);
    }

    /**
     * 删除模板数据通道，会一并删除对应的设备数据通道和channelDataField
     * @param templateChannelId
     */
    public void deleteTemplateChannel(int templateChannelId){
        ArrayList<String> topicsToDelete = new ArrayList<>();

        ArrayList<DeviceChannel> deviceChannels = deviceChannelRepository.getAllByTemplateChannelId(templateChannelId);
        if(deviceChannels != null & deviceChannels.size() > 0){
            for(int i = 0; i < deviceChannels.size(); i++){
                String deviceName = deviceService.getDeviceById(deviceChannels.get(i).getDeviceId()).getName();
                topicsToDelete.add("/" + deviceName + "/" + deviceChannels.get(i).getChannelName());

                deviceChannelRepository.deleteById(deviceChannels.get(i).getId());
            }
        }
        channelDataFieldService.deleteChannelRelatedFields(templateChannelId, 0);
        templateChannelRepository.deleteById(templateChannelId);

        mqttConnectionService.removeSub(topicsToDelete.toArray(new String[topicsToDelete.size()]));
    }

    /**
     * 删除设备数据通道，会一并删除对应的channelDataField
     * @param deviceChannelId
     */
    public void deleteDeviceChannel(int deviceChannelId){
        DeviceChannel deviceChannel= getDeviceChannelById(deviceChannelId);
        String deviceName = deviceService.getDeviceById(deviceChannel.getDeviceId()).getName();

        deviceChannelRepository.deleteById(deviceChannelId);
        channelDataFieldService.deleteChannelRelatedFields(deviceChannelId, 1);
        mqttConnectionService.removeSub("/" + deviceName + "/" + deviceChannel.getChannelName());
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
