package com.example.iot.controller;

import com.example.iot.controller.VO.ResultVO;
import com.example.iot.controller.VO.TemplateForm;
import com.example.iot.controller.VO.TemplateVO;
import com.example.iot.service.TemplateService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/24
 * @description:
 */
@Api(value = "模板/产品服务")
@Controller
@ResponseBody
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    TemplateService templateService;

    @PostMapping("/add")
    public ResultVO<Integer> add(@RequestBody TemplateForm templateForm) {
        return templateService.addTemplate(templateForm);
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") int id) {
        templateService.deleteTemplate(id);
    }

    /*
    修改模板，但是只允许修改模板的名称和描述
     */
    @PostMapping("/update/{id}")
    public ResultVO update(@PathVariable(name = "id") int id, @RequestBody TemplateForm templateForm) {
        return templateService.updateTemplate(id, templateForm);
    }

    //返回模板信息
    @GetMapping("/get/{id}")
    public TemplateVO get(@PathVariable(name = "id") int id) {

        return templateService.getTemplate(id);
    }

    //备注：page从0开始
    @GetMapping("/list")
    public Page<TemplateVO> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return templateService.getTemplateList(page, pageSize);
    }

    @GetMapping("/search")
    public List<TemplateVO> searchByName(@RequestParam("name") String name) {
        return templateService.getTemplateListByName(name);
    }
}
