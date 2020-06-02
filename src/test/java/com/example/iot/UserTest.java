package com.example.iot;

import com.example.iot.domain.User;
import com.example.iot.repository.UserRepository;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/20
 * @description:
 */

public class UserTest extends IotApplicationTests{

    @Autowired
    UserRepository userRepository;

    @Test(timeout = 1000)
    public void test1(){
        assertEquals("123456", userRepository.findByAccount("root").getPassword());
    }

    @Test
    public void test2(){
        User user = new User();
        System.out.println(user);
    }

}
