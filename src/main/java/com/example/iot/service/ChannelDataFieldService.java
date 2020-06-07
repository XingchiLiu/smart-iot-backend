package com.example.iot.service;

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
}
