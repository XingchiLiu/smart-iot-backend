package com.example.iot.service.RuleService;

import com.alibaba.fastjson.JSONObject;
import com.example.iot.controller.VO.MessageForm;
import com.example.iot.service.ChannelService;
import com.example.iot.service.OutMessageService;
import com.example.iot.util.SpringContextUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * @author Karson
 * @CreateDate 2020/6/10 0:45
 */
public class LinkageAction extends Action {
    public LinkageAction(String target, JSONObject data) {
        super(target, data);
    }

    @Override
    public void execute() throws Exception {
        String[] array = target.split(":", 2);
        int deviceId = Integer.parseInt(array[0]);
        String message = array[1];

        int channelId;

        ChannelService channelService = SpringContextUtil.getBean(ChannelService.class);

        try {
            channelId = channelService.getChannelIdByDeviceIdAndChannelTypeAndChannelName(deviceId, 1, "send_to_device_" + deviceId);
        } catch (Exception e) {
            channelId = channelService.addDeviceChannel(1, deviceId, -1, "send_to_device_" + deviceId);
        }

        ArrayList<Map<String, Object>> data = new ArrayList<>();
        JSONObject json = new JSONObject();
        json.put("message", message);
        data.add(JSONObject.toJavaObject(json, Map.class));

        MessageForm messageForm = new MessageForm();
        messageForm.setTopic(String.valueOf(channelId));
        messageForm.setDeviceId(deviceId);
        messageForm.setChannelId(channelId);
        messageForm.setDate(new Date());
        messageForm.setDirection(1);
        messageForm.setData(data);

        if (messageForm.getDeviceId() != deviceId) {
            throw new Exception("设备ID不一致");
        }

        OutMessageService outMessageService = SpringContextUtil.getBean(OutMessageService.class);

        try {
            outMessageService.sendMessageToDevice(deviceId, messageForm);
        } catch (Exception e) {
            throw new Exception("发送数据给设备失败！");
        }
    }
}
