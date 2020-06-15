package com.example.iot.repository.analysis.repo;

import com.example.iot.domain.DeviceMessage;

import java.util.List;

/**
 * @author zhm
 */
public interface DeviceMessageRepo {
    /**
     * 查找某话题中距离当前时间最近的消息
     *
     * @param topic 话题
     * @return 某话题中距离当前时间最近的消息
     */
    DeviceMessage getLastMsgByTopic(String topic);

    /**
     * 查找某话题的所有历史消息
     *
     * @param topic 话题
     * @return 某话题的所有历史消息
     */
    List<DeviceMessage> getAllMsgByTopic(String topic);
}
