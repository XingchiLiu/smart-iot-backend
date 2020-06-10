package com.example.iot.service;

import com.example.iot.controller.VO.ResultVO;
import com.example.iot.controller.VO.TemplateForm;
import com.example.iot.controller.VO.TemplateVO;
import com.example.iot.domain.DeviceTemplate;
import com.example.iot.repository.TemplateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/24
 * @description:设备模板
 *
 */
@Service
public class TemplateService {

    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    GroupService groupService;
    @Autowired
    ChannelService channelService;
    @Autowired
    ChannelDataFieldService channelDataFieldService;


    public ResultVO addTemplate(TemplateForm templateForm){
        //判断模板名字是否重复
        Optional<DeviceTemplate> optionalDeviceTemplate = templateRepository.findByName(templateForm.getName());
        if(optionalDeviceTemplate.isPresent()){
            return ResultVO.getFailed("创建失败，有相同名字的模板");
        }
        DeviceTemplate deviceTemplate = new DeviceTemplate();
        //复制信息
        deviceTemplate.setName(templateForm.getName());
        deviceTemplate.setConnectionType(templateForm.getConnectionType());
        deviceTemplate.setDescription(templateForm.getDescription());
        //填充默认信息
        deviceTemplate.setClassification("common");
        deviceTemplate.setDataFormatType("ICA_Format");
        deviceTemplate.setCreateTime(new Date());
        deviceTemplate.setUpdateTime(deviceTemplate.getCreateTime());

        DeviceTemplate result = templateRepository.save(deviceTemplate);
        //创建产品组
        groupService.createProductGroup(result);

        //如果设置了数据通道
        if(templateForm.getChannelType() != -1) {
            //获得设备模板id
            int templateId = result.getId();

            // 创建数据通道
            int channelId = channelService.addTemplateChannel(templateForm.getChannelType(), templateId);

            // 创建数据通道里的字段
            if(templateForm.getChannelData() != null && !templateForm.getChannelData().isEmpty()) {
                channelDataFieldService.addDataFields(templateForm.getChannelData(), channelId, 0);
            }
        }

        return ResultVO.getSuccess("创建成功", result.getId());
    }

    /*
    删除模板，但不会删除其对应的设备
     */
    public void deleteTemplate(int id){
        templateRepository.deleteById(id);
    }

    /**
     *
     * @param templateForm
     * @return 只返回成功还是失败
     */
    public ResultVO updateTemplate(int templateId, TemplateForm templateForm){
        DeviceTemplate deviceTemplate = templateRepository.findById(templateId).get();
        //设置名字
        if(deviceTemplate.getName().equals(templateForm.getName())){
            //do nothing
        }
        else{
            //名字查重
            Optional<DeviceTemplate> optionalDeviceTemplate = templateRepository.findByName(templateForm.getName());
            if(optionalDeviceTemplate.isPresent()){
                return ResultVO.getFailed("更新失败，有相同名字的模板");
            }
            deviceTemplate.setName(templateForm.getName());
        }
        //设置描述
        deviceTemplate.setDescription(templateForm.getDescription());
        templateRepository.save(deviceTemplate);
        return ResultVO.getSuccess("修改成功");
    }

    /**
     *
     * @param templateId
     * @return 模板的展示形式
     */
    public TemplateVO getTemplate(int templateId){
        DeviceTemplate template = templateRepository.getOne(templateId);
        TemplateVO templateVO = new TemplateVO();
        BeanUtils.copyProperties(template, templateVO);
        return templateVO;
    }

    public Page<TemplateVO> getTemplateList(int page, int pageSize){
        //id降序排列，即新创建的排在前面
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        Page<DeviceTemplate> templatePage = templateRepository.findAll(pageable);
        return templatePage.map(this::deviceTemplateToVO);
    }

    public List<TemplateVO> getTemplateListByName(String name){
        List<DeviceTemplate> templateList = templateRepository.findByNameLike(name);
        List<TemplateVO> result = new ArrayList<>();
        templateList.forEach(t->{
            result.add(deviceTemplateToVO(t));
        });
        return result;
    }

    private TemplateVO deviceTemplateToVO(DeviceTemplate deviceTemplate){
        TemplateVO result = new TemplateVO();
        BeanUtils.copyProperties(deviceTemplate, result);
        return result;
    }
}
