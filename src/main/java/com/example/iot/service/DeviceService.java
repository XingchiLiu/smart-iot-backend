package com.example.iot.service;

import com.example.iot.controller.VO.DeviceForm;
import com.example.iot.controller.VO.DeviceVO;
import com.example.iot.controller.VO.ResultVO;
import com.example.iot.domain.Device;
import com.example.iot.domain.DeviceTemplate;
import com.example.iot.repository.DeviceRepository;
import com.example.iot.repository.TemplateRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/24
 * @description:
 */
@Service
public class DeviceService {

    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    ChannelService channelService;

    /**
     * 增加一个设备，如果成功返回设备id
     *
     * @param deviceForm
     * @return
     */
    @Transactional
    public ResultVO<Integer> addDevice(DeviceForm deviceForm) {
        int templateId = deviceForm.getTemplateId();
        Optional<DeviceTemplate> deviceTemplateOptional = templateRepository.findById(templateId);
        //确保模板存在
        if (!deviceTemplateOptional.isPresent()) {
            return ResultVO.getFailed("模板不存在");
        }
        DeviceTemplate deviceTemplate = deviceTemplateOptional.get();
        Device device = new Device();
        BeanUtils.copyProperties(deviceTemplate, device, "id", "name", "description");
        //如果没填名字，设置名字
        if (StringUtils.isEmpty(deviceForm.getName())) {
            long time = System.currentTimeMillis();
            //通过随机来创造的伪唯一
            String name = deviceTemplate.getName() + "_device_" + (int) time % 100000;
            device.setName(name);
        } else {
            //有名字，需要判断名字在模板内唯一
            String name = deviceForm.getName();
            Optional<Device> optionalDevice = deviceRepository.findFirstByTemplateIdAndName(templateId, name);
            //同一模板内名字重复
            if (optionalDevice.isPresent()) {
                return ResultVO.getFailed("模板内有相同名称的设备，请重命名");
            }
            device.setName(deviceForm.getName());
        }
        device.setDescription(deviceForm.getDescription());
        device.setTemplateId(deviceTemplate.getId());
        device.setCreateTime(new Date());
        device.setUpdateTime(device.getCreateTime());
        //默认为管理员1
        device.setUserId(1);

        /*
        TODO 复制数据通道、物模型、影子设备
         */

        Device result = deviceRepository.save(device);

        channelService.addChannelsToNewlyAddedDevice(result.getId(), templateId);

        return ResultVO.getSuccess("创建成功", result.getId());
    }

    /**
     * 删除一个设备，必定成功
     *
     * @param deviceId
     */
    @Transactional
    public void deleteDevice(int deviceId) {
        //删除设备信息
        deviceRepository.deleteById(deviceId);
        //TODO 删除设备通道、物模型、影子设备
    }

    /**
     * 更新设备信息，只能修改设备名和描述，如果成功返回成功
     *
     * @param deviceId
     * @return
     */
    public ResultVO updateDevice(int deviceId, DeviceForm deviceForm) {
        Optional<Device> optionalDevice = deviceRepository.findFirstByTemplateIdAndName(
                deviceForm.getTemplateId(), deviceForm.getName());
        //同一模板内名字重复
        if (optionalDevice.isPresent()) {
            return ResultVO.getFailed("模板内有相同名称的设备，请重命名");
        }

        Device device = deviceRepository.findById(deviceId).get();
        device.setName(deviceForm.getName());
        device.setDescription(deviceForm.getDescription());
        device.setUpdateTime(new Date());
        deviceRepository.save(device);
        return ResultVO.getSuccess("创建成功");
    }

    /**
     * 获取设备基本信息
     *
     * @return
     */
    public DeviceVO getDeviceById(int id) {
        return deviceToVO(deviceRepository.findById(id).get());
    }

    /**
     * 按页返回设备列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    public Page<DeviceVO> getDeviceList(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        Page<Device> devicePage = deviceRepository.findAll(pageable);
        return devicePage.map(this::deviceToVO);
    }

    /**
     * 根据名字查询设备
     *
     * @param name
     * @return
     */
    public List<DeviceVO> getDeviceListByName(String name) {
        List<DeviceVO> result = new ArrayList<>();
        List<Device> found = deviceRepository.findAllByNameContaining(name);
        found.forEach(device -> {
            result.add(deviceToVO(device));
        });
        return result;
    }

    private DeviceVO deviceToVO(Device device) {

        DeviceVO deviceVO = new DeviceVO();
        BeanUtils.copyProperties(device, deviceVO);
        //TODO 查询最近的数据上报时间
        Date lastTime = null;
        deviceVO.setLastContactTime(lastTime);
        return deviceVO;
    }
}
