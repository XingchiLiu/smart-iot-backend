package com.example.iot.service;

import com.example.iot.IotApplicationTests;
import com.example.iot.domain.ChannelDataField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ChannelDataFieldServiceTest extends IotApplicationTests {
    @Autowired
    ChannelDataFieldService channelDataFieldService;

    @Test
    void addDataFields() {
        ChannelDataField channelDataField = new ChannelDataField();
        channelDataField.setChannelType(0);
        channelDataField.setFieldType(1);
        channelDataField.setFieldName("test");
        channelDataField.setChannelId(2);
        int id = channelDataFieldService.addDataField(channelDataField);

        ChannelDataField result = channelDataFieldService.getDataFieldById(id);
        assertNotNull(result);
        assertEquals(0, result.getChannelType());
        assertEquals(1, result.getFieldType());
    }

    @Test
    void addDataField() {

    }

    @Test
    void getDataFieldById() {
    }

    @Test
    void getDataFieldsByChannelId() {
    }
}