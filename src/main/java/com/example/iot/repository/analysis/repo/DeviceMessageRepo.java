package com.example.iot.repository.analysis.repo;

import com.example.iot.domain.DeviceMessage;

import java.time.LocalDateTime;
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

    /**
     * 查找某话题在时间区间[startTime, endTime]间的所有历史消息
     *
     * @param topic     话题
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 某话题在时间区间[startTime, endTime]间的所有历史消息
     */
    List<DeviceMessage> getMsgByTopicAndTimeInterval(String topic, LocalDateTime startTime, LocalDateTime endTime);
}
