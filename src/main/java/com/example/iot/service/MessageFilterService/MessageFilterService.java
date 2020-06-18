package com.example.iot.service.MessageFilterService;

import com.example.iot.controller.VO.MessageForm;
import com.example.iot.domain.DeviceChannel;
import com.example.iot.service.ChannelDataFieldService;
import com.example.iot.service.ChannelService;
import com.example.iot.service.MessageFilterService.MessageFilterExceptions.DataFieldException;
import com.example.iot.service.MessageFilterService.MessageFilterExceptions.DeviceChannelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageFilterService {
    @Autowired
    ChannelService channelService;
    @Autowired
    ChannelDataFieldService channelDataFieldService;

    public void validateForm(MessageForm messageForm) throws DataFieldException, DeviceChannelException {
        DeviceChannel deviceChannel = channelService.getDeviceChannelByIdAndDeviceIdAndChannelType(
                messageForm.getChannelId(), messageForm.getDeviceId(), 1);
        if(deviceChannel == null){
            throw new DeviceChannelException();
        }

        int channelType = 1;
        int channelId = deviceChannel.getId();
        if(deviceChannel.getTemplateChannelId() != -1){
            channelType = 0;
            channelId = deviceChannel.getTemplateChannelId();
        }
        channelDataFieldService.verifyDataFieldsInMessageForm(channelId,channelType,messageForm.getData());
    }
}
