package com.example.iot.controller;

import com.example.iot.IotApplicationTests;
import com.example.iot.controller.VO.ChannelDataFieldForm;
import com.example.iot.controller.VO.DeviceChannelForm;
import com.example.iot.controller.VO.ResultVO;
import com.example.iot.controller.VO.TemplateChannelForm;
import com.example.iot.domain.ChannelDataField;
import com.example.iot.domain.DeviceChannel;
import com.example.iot.domain.TemplateChannel;
import com.example.iot.service.ChannelDataFieldService;
import com.example.iot.service.ChannelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ChannelControllerTest extends IotApplicationTests {
    @Autowired
    ChannelController channelController;
    @Autowired
    ChannelDataFieldService channelDataFieldService;
    @Autowired
    ChannelService channelService;

    @Test
    void addDataFieldToTemplateChannel() {
        ChannelDataFieldForm channelDataFieldForm = createChannelDataFieldForm("test", 2, 1, 0);

        ResultVO<Integer> resultVO = channelController.addDataField(channelDataFieldForm);
        ChannelDataField result = channelDataFieldService.getDataFieldById(resultVO.getData());
        assertNotNull(result);
        assertEquals(0,result.getChannelType());
        assertEquals("test", result.getFieldName());
        assertEquals(2,result.getFieldType());
        assertEquals(1, result.getChannelId());
    }

    @Test
    void addTemplateChannel() {
        TemplateChannelForm templateChannelForm = createTemplateChannelForm(0, 1, "test");
        ResultVO<Integer> resultVO = channelController.addTemplateChannel(templateChannelForm);
        TemplateChannel result = channelService.getTemplateChannelById(resultVO.getData());
        assertNotNull(result);
        assertEquals(1,result.getTemplateId());
        assertEquals(0, result.getChannelType());
        assertEquals("test", result.getChannelName());
    }

    @Test
    void addDeviceChannel() {
        DeviceChannelForm deviceChannelForm = createDeviceChannelForm(0,1,-1,"test");
        ResultVO<Integer> resultVO = channelController.addDeviceChannel(deviceChannelForm);
        DeviceChannel result = channelService.getDeviceChannelById(resultVO.getData());
        assertNotNull(result);
        assertEquals(0,result.getChannelType());
        assertEquals(1,result.getDeviceId());
        assertEquals(-1, result.getTemplateChannelId());
        assertEquals("test", result.getChannelName());
    }

    @Test
    void modifyDataFieldOfTemplateChannel(){
        ChannelDataFieldForm channelDataFieldForm = createChannelDataFieldForm("test", 2, 1, 0);
        int id = channelController.addDataField(channelDataFieldForm).getData();

        ChannelDataFieldForm changedChannelDataFieldForm = createChannelDataFieldForm("test1",2,1,0,id);
        channelController.modifyDataField(id,changedChannelDataFieldForm);

        ChannelDataField result = channelDataFieldService.getDataFieldById(id);
        assertNotNull(result);
        assertEquals(0,result.getChannelType());
        assertEquals("test1", result.getFieldName());
        assertEquals(2,result.getFieldType());
        assertEquals(1, result.getChannelId());
    }

    @Test
    void modifyNameOfTemplateChannel(){
        TemplateChannelForm templateChannelForm = createTemplateChannelForm(0, 1, "test");
        int id = channelController.addTemplateChannel(templateChannelForm).getData();

        DeviceChannelForm deviceChannelForm = createDeviceChannelForm(0,1,id, "test");
        int deviceChannelId = channelController.addDeviceChannel(deviceChannelForm).getData();

        TemplateChannelForm templateChannelForm1 = createTemplateChannelForm(0,1, "test1", id);
        channelController.modifyNameOfTemplateChannel(id, templateChannelForm1);

        TemplateChannel templateChannelResult = channelService.getTemplateChannelById(id);
        assertNotNull(templateChannelResult);
        assertEquals(1,templateChannelResult.getTemplateId());
        assertEquals(0, templateChannelResult.getChannelType());
        assertEquals("test1", templateChannelResult.getChannelName());

        DeviceChannel deviceChannel = channelService.getDeviceChannelById(deviceChannelId);
        assertNotNull(deviceChannel);
        assertEquals("test1", deviceChannel.getChannelName());
    }

    @Test
    void modifyNameOfDeviceChannel() {
        DeviceChannelForm deviceChannelForm = createDeviceChannelForm(0,1,-1,"test");
        int id = channelController.addDeviceChannel(deviceChannelForm).getData();

        DeviceChannelForm deviceChannelForm1 = createDeviceChannelForm(0,1, -1,"test1", id);
        channelController.modifyNameOfDeviceChannel(id, deviceChannelForm1);

        DeviceChannel result = channelService.getDeviceChannelById(id);
        assertNotNull(result);
        assertEquals(0,result.getChannelType());
        assertEquals(1,result.getDeviceId());
        assertEquals(-1, result.getTemplateChannelId());
        assertEquals("test1", result.getChannelName());
    }

    @Test
    void deleteDataField(){
        ChannelDataFieldForm channelDataFieldForm = createChannelDataFieldForm("test", 2, 1, 0);
        int id = channelController.addDataField(channelDataFieldForm).getData();
        assertNotNull(channelDataFieldService.getDataFieldById(id));

        channelController.deleteDataField(id);
        assertNull(channelDataFieldService.getDataFieldById(id));
    }

    @Test
    void deleteTemplateChannel(){
        TemplateChannelForm templateChannelForm = createTemplateChannelForm(0, 1, "test");
        int id = channelController.addTemplateChannel(templateChannelForm).getData();

        DeviceChannelForm deviceChannelForm = createDeviceChannelForm(0,1,id, "test");
        int deviceChannelId = channelController.addDeviceChannel(deviceChannelForm).getData();

        assertNotNull(channelService.getTemplateChannelById(id));
        assertNotNull(channelService.getDeviceChannelById(deviceChannelId));

        channelController.deleteTemplateChannel(id);
        assertNull(channelService.getTemplateChannelById(id));
        assertNull(channelService.getDeviceChannelById(deviceChannelId));
    }

    @Test
    void deleteDeviceChannel() {
        DeviceChannelForm deviceChannelForm = createDeviceChannelForm(0,1,-1,"test");
        int id = channelController.addDeviceChannel(deviceChannelForm).getData();
        assertNotNull(channelService.getDeviceChannelById(id));

        channelController.deleteDeviceChannel(id);
        assertNull(channelService.getDeviceChannelById(id));
    }

    private ChannelDataFieldForm createChannelDataFieldForm(String fieldName, int fieldType, int channelId, int channelType){
        ChannelDataFieldForm channelDataFieldForm = new ChannelDataFieldForm();
        channelDataFieldForm.setChannelType(channelType);
        channelDataFieldForm.setFieldName(fieldName);
        channelDataFieldForm.setFieldType(fieldType);
        channelDataFieldForm.setChannelId(channelId);
        return channelDataFieldForm;
    }

    private ChannelDataFieldForm createChannelDataFieldForm(String fieldName, int fieldType, int channelId, int channelType, int id){
        ChannelDataFieldForm channelDataFieldForm = new ChannelDataFieldForm();
        channelDataFieldForm.setChannelType(channelType);
        channelDataFieldForm.setFieldName(fieldName);
        channelDataFieldForm.setFieldType(fieldType);
        channelDataFieldForm.setChannelId(channelId);
        channelDataFieldForm.setId(id);
        return channelDataFieldForm;
    }

    private DeviceChannelForm createDeviceChannelForm(int channelType, int deviceId, int templateChannelId, String channelName){
        DeviceChannelForm deviceChannelForm = new DeviceChannelForm();
        deviceChannelForm.setChannelType(channelType);
        deviceChannelForm.setDeviceId(deviceId);
        deviceChannelForm.setTemplateChannelId(templateChannelId);
        deviceChannelForm.setChannelName(channelName);
        return deviceChannelForm;
    }

    private DeviceChannelForm createDeviceChannelForm(int channelType, int deviceId, int templateChannelId, String channelName, int id){
        DeviceChannelForm deviceChannelForm = new DeviceChannelForm();
        deviceChannelForm.setChannelType(channelType);
        deviceChannelForm.setDeviceId(deviceId);
        deviceChannelForm.setTemplateChannelId(templateChannelId);
        deviceChannelForm.setChannelName(channelName);
        deviceChannelForm.setId(id);
        return deviceChannelForm;
    }

    private TemplateChannelForm createTemplateChannelForm(int channelType, int templateId, String channelName){
        TemplateChannelForm templateChannelForm = new TemplateChannelForm();
        templateChannelForm.setChannelType(channelType);
        templateChannelForm.setTemplateId(templateId);
        templateChannelForm.setChannelName(channelName);
        return templateChannelForm;
    }

    private TemplateChannelForm createTemplateChannelForm(int channelType, int templateId, String channelName, int id){
        TemplateChannelForm templateChannelForm = new TemplateChannelForm();
        templateChannelForm.setChannelType(channelType);
        templateChannelForm.setTemplateId(templateId);
        templateChannelForm.setChannelName(channelName);
        templateChannelForm.setId(id);
        return templateChannelForm;
    }
}