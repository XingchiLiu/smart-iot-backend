package com.example.iot.service;

import com.example.iot.controller.VO.ChannelDataFieldForm;
import com.example.iot.controller.VO.MessageForm;
import com.example.iot.controller.VO.ResultVO;
import com.example.iot.domain.ChannelDataField;
import com.example.iot.repository.ChannelDataFieldRepository;
import com.example.iot.service.MessageFilterService.MessageFilterExceptions.DataFieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChannelDataFieldService {
    @Autowired
    ChannelDataFieldRepository channelDataFieldRepository;

    public ResultVO<Integer> addDataFields(HashMap<String, Integer> dataFields, int channelId, int channelType) {
        int latestSuccessfulId = 0;
        for (Map.Entry<String, Integer> dataField : dataFields.entrySet()) {
            ChannelDataField channelDataField = new ChannelDataField();
            channelDataField.setChannelId(channelId);
            channelDataField.setChannelType(channelType);
            channelDataField.setFieldName(dataField.getKey());
            channelDataField.setFieldType(dataField.getValue());
            latestSuccessfulId = addDataField(channelDataField);
        }
        return ResultVO.getSuccess("创建成功", latestSuccessfulId);
    }

    public int addDataField(ChannelDataField channelDataField) {
        return channelDataFieldRepository.save(channelDataField).getId();
    }

    public ChannelDataField getDataFieldById(int id) {
        ChannelDataField result = channelDataFieldRepository.getChannelDataFieldById(id);
        return result;
    }

    public List<ChannelDataField> getDataFieldsByChannelId(int channelId) {
        List<ChannelDataField> result = channelDataFieldRepository.getAllByChannelId(channelId);
        return result;
    }

    public List<ChannelDataField> getDataFieldsByChannelIdAndChannelType(int channelId, int channelType){
        return channelDataFieldRepository.getAllByChannelIdAndChannelType(channelId, channelType);
    }

    public void verifyDataFieldsInMessageForm(int channelId, int channelType, List<Map<String, Object>> data) throws DataFieldException {
        List<ChannelDataField> dataFields = getDataFieldsByChannelIdAndChannelType(channelId, channelType);

        for(int i = 0; i< data.size(); i++){
            for(Map.Entry<String, Object> entry: data.get(i).entrySet()){
                if(!testOneField(entry.getKey(), entry.getValue(), dataFields)){
                    throw new DataFieldException();
                }
            }
        }
    }

    private boolean testOneField(String name, Object value, List<ChannelDataField> dataFields){
        for(int i = 0; i < dataFields.size(); i++){
            if(dataFields.get(i).getFieldName().equals(name)){
                int type = dataFields.get(i).getFieldType();
                try {
                    switch (type) {
                        case 0:
                            boolean cast_b = (boolean) value;
                            break;
                        case 1:
                            double cast_d = (double) value;
                            break;
                        case 2:
                            String cast_s = (String) value;
                            break;
                        case 3:
                            int cast_i = (int) value;
                    }
                }catch(Exception e){
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    public int addDataField(String fieldName, int fieldType, int channelId, int channelType) {
        ChannelDataField channelDataField = new ChannelDataField();
        channelDataField.setFieldName(fieldName);
        channelDataField.setFieldType(fieldType);
        channelDataField.setChannelId(channelId);
        channelDataField.setChannelType(channelType);
        return channelDataFieldRepository.save(channelDataField).getId();
    }

    public int addDataField(ChannelDataFieldForm channelDataFieldForm) {
        ChannelDataField channelDataField = createChannelDataField(channelDataFieldForm);
        return channelDataFieldRepository.save(channelDataField).getId();
    }

    public int updateDataField(ChannelDataFieldForm channelDataFieldForm) {
        ChannelDataField channelDataField = createChannelDataField(channelDataFieldForm);
        channelDataField.setId(channelDataFieldForm.getId());
        return channelDataFieldRepository.save(channelDataField).getId();
    }

    public void deleteDataField(int id) {
        channelDataFieldRepository.deleteById(id);
    }

    public void deleteChannelRelatedFields(int channelId, int channelType) {
        ArrayList<ChannelDataField> channelDataFields = (ArrayList<ChannelDataField>)
                channelDataFieldRepository.getAllByChannelIdAndChannelType(channelId, channelType);
        if (channelDataFields != null && channelDataFields.size() > 0) {
            for (int i = 0; i < channelDataFields.size(); i++) {
                channelDataFieldRepository.deleteById(channelDataFields.get(i).getId());
            }
        }
    }

    public ChannelDataField createChannelDataField(ChannelDataFieldForm channelDataFieldForm) {
        ChannelDataField channelDataField = new ChannelDataField();
        channelDataField.setChannelType(channelDataFieldForm.getChannelType());
        channelDataField.setChannelId(channelDataFieldForm.getChannelId());
        channelDataField.setFieldType(channelDataFieldForm.getFieldType());
        channelDataField.setFieldName(channelDataFieldForm.getFieldName());
        return channelDataField;
    }
}
