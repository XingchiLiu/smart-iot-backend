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
    public ResultVO<Integer> add(@RequestBody TemplateForm templateForm){
        int id = templateService.addTemplate(templateForm);
        return ResultVO.getSuccess("创建成功", id);
    }

    @GetMapping("/delete")
    public void delete(@RequestParam("id") int id){

    }

    /*
    修改模板，但是只允许修改模板的名称和描述
     */
    @PostMapping("/update")
    public void update(@RequestBody TemplateForm templateForm){

    }

    //返回模板信息
    @GetMapping("/get")
    public TemplateVO get(@RequestParam("id") int id){
        return null;
    }

    @GetMapping("/list")
    public Page<TemplateVO> list(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize){
        return null;
    }
}
