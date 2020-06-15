package com.example.iot.util;

import com.example.iot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/20
 * @description: 实现CommandLineRunner接口，会随项目启动的时候运行run函数
 */
@Component
public class DbLoader implements CommandLineRunner {

    UserRepository userRepository;

    @Autowired
    public DbLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        //加载作者信息
//        new Thread(this::loadUser).start();
    }

    public void loadUser() {

    }


}
