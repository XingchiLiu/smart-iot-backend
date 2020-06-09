package com.example.iot.service;

import com.example.iot.controller.VO.ChannelDataFieldForm;
import com.example.iot.controller.VO.ResultVO;
import com.example.iot.domain.ChannelDataField;
import com.example.iot.repository.ChannelDataFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChannelDataFieldService {
    @Autowired
    ChannelDataFieldRepository channelDataFieldRepository;

    public ResultVO<Integer> addDataFields(HashMap<String, Integer> dataFields, int channelId, int channelType){
        int latestSuccessfulId = 0;
        for(Map.Entry<String, Integer> dataField: dataFields.entrySet()){
            ChannelDataField channelDataField = new ChannelDataField();
            channelDataField.setChannelId(channelId);
            channelDataField.setChannelType(channelType);
            channelDataField.setFieldName(dataField.getKey());
            channelDataField.setFieldType(dataField.getValue());
            latestSuccessfulId = addDataField(channelDataField);
        }
        return ResultVO.getSuccess("创建成功", latestSuccessfulId);
    }

    public int addDataField(ChannelDataField channelDataField){
        ChannelDataField result = channelDataFieldRepository.save(channelDataField);
        return result.getId();
    }

    public ChannelDataField getDataFieldById(int id) {
        ChannelDataField result = channelDataFieldRepository.getChannelDataFieldById(id);
        return result;
    }

    public List<ChannelDataField> getDataFieldsByChannelId(int channelId){
        List<ChannelDataField> result = channelDataFieldRepository.getAllByChannelId(channelId);
        return result;
    }

    public int addDataField(String fieldName, int fieldType, int channelId, int channelType){
        ChannelDataField channelDataField = new ChannelDataField();
        channelDataField.setFieldName(fieldName);
        channelDataField.setFieldType(fieldType);
        channelDataField.setChannelId(channelId);
        channelDataField.setChannelType(channelType);
        return addDataField(channelDataField);
    }

    public int addDataField(ChannelDataFieldForm channelDataFieldForm){
        ChannelDataField channelDataField = createChannelDataField(channelDataFieldForm);
        return addDataField(channelDataField);
    }

    public int updateDataField(ChannelDataFieldForm channelDataFieldForm){
        ChannelDataField channelDataField = createChannelDataField(channelDataFieldForm);
        channelDataField.setId(channelDataFieldForm.getId());
        return addDataField(channelDataField);
    }

    public void deleteDataField(int id){
        channelDataFieldRepository.deleteById(id);
    }

    public ChannelDataField createChannelDataField(ChannelDataFieldForm channelDataFieldForm){
        ChannelDataField channelDataField = new ChannelDataField();
        channelDataField.setChannelType(channelDataFieldForm.getChannelType());
        channelDataField.setChannelId(channelDataFieldForm.getChannelId());
        channelDataField.setFieldType(channelDataFieldForm.getFieldType());
        channelDataField.setFieldName(channelDataFieldForm.getFieldName());
        return channelDataField;
    }
}
