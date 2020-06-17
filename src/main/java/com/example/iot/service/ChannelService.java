package com.example.iot.service;

import com.example.iot.controller.VO.DeviceChannelForm;
import com.example.iot.controller.VO.TemplateChannelForm;
import com.example.iot.domain.Device;
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
    @Autowired
    InMessageService inMessageService;
    @Autowired
    DeviceService deviceService;

    public int addDeviceChannel(DeviceChannel deviceChannel) {
        DeviceChannel result = deviceChannelRepository.save(deviceChannel);
        return result.getId();
    }

    public int addTemplateChannel(TemplateChannel templateChannel) {
        TemplateChannel result = templateChannelRepository.save(templateChannel);
        return result.getId();
    }

    /**
     * 再新建一个模板时添加的模板数据通道。因为此时还没有设备，所以不需要订阅。
     *
     * @param channelType
     * @param templateId
     * @param channelName
     * @return
     */
    public int addTemplateChannel(int channelType, int templateId, String channelName) {
        TemplateChannel templateChannel = new TemplateChannel();
        templateChannel.setChannelType(channelType);
        templateChannel.setTemplateId(templateId);
        templateChannel.setChannelName(channelName);
        return addTemplateChannel(templateChannel);
    }

    public int addDeviceChannel(int channelType, int deviceId, int templateChannelId, String channelName) {
        DeviceChannel deviceChannel = new DeviceChannel();
        deviceChannel.setChannelType(channelType);
        deviceChannel.setDeviceId(deviceId);
        deviceChannel.setTemplateChannelId(templateChannelId);
        deviceChannel.setChannelName(channelName);

        int id = addDeviceChannel(deviceChannel);
        inMessageService.addSub(String.valueOf(id), 0);
        return id;
    }

    public int addTemplateChannel(TemplateChannelForm templateChannelForm) {
        TemplateChannel templateChannel = createTemplateChannel(templateChannelForm);
        List<Device> devices = deviceService.getDeviceByTemplateId(templateChannelForm.getId());
        if(devices != null && devices.size() > 0){
            for(int i = 0; i < devices.size(); i++){
                addDeviceChannel(templateChannelForm.getChannelType(),
                        devices.get(i).getId(),templateChannelForm.getId(),
                        templateChannelForm.getChannelName());
            }
        }
        return addTemplateChannel(templateChannel);
    }

    public int addDeviceChannel(DeviceChannelForm deviceChannelForm) {
        DeviceChannel deviceChannel = createDeviceChannel(deviceChannelForm);
        int deviceChannelId = addDeviceChannel(deviceChannel);
        inMessageService.addSub(String.valueOf(deviceChannelId), 0);
        return deviceChannelId;
    }

    /**
     * 给新添加的设备加上模板包含的数据通道
     *
     * @param deviceId
     * @param templateId
     */
    public void addChannelsToNewlyAddedDevice(int deviceId, int templateId) {
        ArrayList<String> topicsToAdd = new ArrayList<>();

        ArrayList<TemplateChannel> templateChannels = getTemplateChannelsByTemplateId(templateId);
        if (templateChannels != null && templateChannels.size() > 0) {
            for (int i = 0; i < templateChannels.size(); i++) {
                int deviceChannelId = addDeviceChannel(templateChannels.get(i).getChannelType(),
                        deviceId, templateChannels.get(i).getId(),
                        templateChannels.get(i).getChannelName());
                topicsToAdd.add(String.valueOf(deviceChannelId));
            }
        }
        inMessageService.addSub(topicsToAdd.toArray(new String[topicsToAdd.size()]), 0);
    }

    public ArrayList<TemplateChannel> getTemplateChannelsByTemplateId(int templateId) {
        return templateChannelRepository.getAllByTemplateId(templateId);
    }

    public ArrayList<DeviceChannel> getDeviceChannelByTemplateChannelId(int templateChannelId) {
        return deviceChannelRepository.getAllByTemplateChannelId(templateChannelId);
    }

    public TemplateChannel getTemplateChannelById(int templateChannelId) {
        return templateChannelRepository.getById(templateChannelId);
    }

    public DeviceChannel getDeviceChannelById(int deviceChannelId) {
        return deviceChannelRepository.getById(deviceChannelId);
    }

    public ArrayList<DeviceChannel> getDeviceChanelByDeviceId(int deviceId){
        return deviceChannelRepository.getAllByDeviceId(deviceId);
    }

    public DeviceChannel getDeviceChannelByIdAndDeviceIdAndChannelType(int id, int deviceId, int channelType){
        return deviceChannelRepository.getByIdAndDeviceIdAndChannelType(id, deviceId, channelType);
    }

    public List<TemplateChannel> getAllTemplateChannels() {
        return templateChannelRepository.findAll();
    }

    public List<DeviceChannel> getAllDeviceChannels() {
        return deviceChannelRepository.findAll();
    }

    /**
     * 更新模板数据通道的【名字】，会一并更新对应的数据设备通道
     *
     * @param templateChannelForm
     * @return
     */
    public int updateTemplateChannel(TemplateChannelForm templateChannelForm) {

        TemplateChannel templateChannel = createTemplateChannel(templateChannelForm);
        templateChannel.setId(templateChannelForm.getId());
        ArrayList<DeviceChannel> deviceChannels = deviceChannelRepository.getAllByTemplateChannelId(templateChannelForm.getId());
        if (deviceChannels != null & deviceChannels.size() > 0) {
            for (int i = 0; i < deviceChannels.size(); i++) {
                deviceChannels.get(i).setChannelName(templateChannelForm.getChannelName());
                addDeviceChannel(deviceChannels.get(i));
            }
        }
        return addTemplateChannel(templateChannel);
    }

    /**
     * 更新设备数据通道的【名字】
     *
     * @param deviceChannelForm
     * @return
     */
    public int updateDeviceChannel(DeviceChannelForm deviceChannelForm) {
        DeviceChannel deviceChannel = createDeviceChannel(deviceChannelForm);
        deviceChannel.setId(deviceChannelForm.getId());
        return addDeviceChannel(deviceChannel);
    }

    /**
     * 删除模板数据通道，会一并删除对应的设备数据通道和channelDataField
     *
     * @param templateChannelId
     */
    public void deleteTemplateChannel(int templateChannelId) {
        ArrayList<String> topicsToDelete = new ArrayList<>();

        // 删掉对应的设备数据通道
        ArrayList<DeviceChannel> deviceChannels = deviceChannelRepository.getAllByTemplateChannelId(templateChannelId);
        if (deviceChannels != null & deviceChannels.size() > 0) {
            for (int i = 0; i < deviceChannels.size(); i++) {
                deviceChannelRepository.deleteById(deviceChannels.get(i).getId());
                topicsToDelete.add(String.valueOf(deviceChannels.get(i).getId()));
            }
        }

        channelDataFieldService.deleteChannelRelatedFields(templateChannelId, 0);
        templateChannelRepository.deleteById(templateChannelId);

        inMessageService.removeSub(topicsToDelete.toArray(new String[topicsToDelete.size()]));
    }

    /**
     * 删除设备数据通道，会一并删除对应的channelDataField
     *
     * @param deviceChannelId
     */
    public void deleteDeviceChannel(int deviceChannelId) {
        deviceChannelRepository.deleteById(deviceChannelId);
        channelDataFieldService.deleteChannelRelatedFields(deviceChannelId, 1);
        inMessageService.removeSub(String.valueOf(deviceChannelId));
    }


    public void deleteDeviceOwnDeviceChannel(int deviceId){
        ArrayList<DeviceChannel> deviceChannels = deviceChannelRepository.getAllByDeviceIdAndTemplateChannelId(deviceId,-1);
        if(deviceChannels != null && deviceChannels.size() > 0){
            for(int i = 0; i < deviceChannels.size(); i++) {
                deviceChannelRepository.deleteById(deviceChannels.get(i).getId());
            }
        }
    }

    public int getChannelIdByDeviceIdAndChannelTypeAndChannelName(int deviceId, int channelType, String channelName) {
        return deviceChannelRepository.getByDeviceIdAndChannelTypeAndChannelName(deviceId, channelType, channelName).getId();
    }

    private TemplateChannel createTemplateChannel(TemplateChannelForm templateChannelForm) {
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
