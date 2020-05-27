package com.example.iot.controller;

import com.example.iot.controller.VO.ResultVO;
import com.example.iot.domain.User;
import com.example.iot.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/20
 * @description:
 */
@Api(value = "用户服务")
@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {
    @Autowired
    UserRepository userRepository;

    @ApiOperation(value = "登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账户字符串", required = true, paramType = "query", dataType = "String", example = "root"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, paramType = "query", dataType = "String", example = "123456")
    })
    @GetMapping("/login")
    public ResultVO getUserInfo(@RequestParam("account") String account, @RequestParam("password") String password) {

        User user = userRepository.findByAccount(account);
        if (user == null) {
            //用户不存在
            return ResultVO.builder().result(1).message("用户不存在").build();
        }
        if (user.getPassword().equals(password)) {
            //成功
            return ResultVO.builder().result(0).data(user).build();
        } else
            //密码不正确
            return ResultVO.builder().result(1).message("用户名密码不匹配").build();
    }
}
