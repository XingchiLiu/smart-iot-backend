package com.example.iot.controller.VO.analysis;

import com.example.iot.domain.ChannelDataField;
import com.example.iot.util.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDataFieldVO {
    private Integer fieldId;
    private String fieldName;
    private FieldType fieldType;

    public ChannelDataFieldVO(ChannelDataField channelDataField) {
        this.fieldId = channelDataField.getId();
        this.fieldName = channelDataField.getFieldName();
        this.fieldType = FieldType.valueOf(channelDataField.getFieldType());
    }
}
