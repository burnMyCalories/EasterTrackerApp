package com.model;

import com.alibaba.fastjson.JSONObject;
import com.dao.UserMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String gender;
    private String nickname;
    private String contact;
    private int is_active;
    private int is_online;
    private double latitude;
    private double longitude;
    private int version;
    private int is_deleted;
    private String creation_time;
    private String update_time;
    public JSONObject toJSON(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserMapper mapper = context.getBean("userMapper", UserMapper.class);
        Integer set_count = mapper.selectSet(this.id);
        Integer get_count = mapper.selectGet(this.id);
        if(set_count==null){
            set_count=0;
        }
        if(get_count==null){
            get_count=0;
        }
        JSONObject json = new JSONObject(true);
        json.put("id",this.id);
        json.put("username",this.username);
//        json.put("password",this.password);
        json.put("gender",this.gender);
        json.put("nickname",this.nickname);
        json.put("contact",this.contact);
        json.put("is_active",this.is_active);
        json.put("is_online",this.is_online);
        json.put("latitude",this.latitude);
        json.put("longitude",this.longitude);
        json.put("set_count",set_count);
        json.put("get_count",get_count);
        json.put("is_deleted",this.is_deleted);
        return json;
    }
}
