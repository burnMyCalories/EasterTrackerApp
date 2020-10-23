package com.dao;

import com.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class UserOps {
    public static List<User> queryUser(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserMapper mapper = context.getBean("userMapper", UserMapper.class);
        List<User> users = mapper.queryUser();
        return users;
    }
}
