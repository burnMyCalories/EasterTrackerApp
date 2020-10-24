package com.util;

import com.alibaba.fastjson.JSONObject;
import com.dao.UserMapper;
import com.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class CRUDUtils {

    public static int addUser(String username,String password,String gender,String nickname,String contact,String latitude,String longitude){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserMapper mapper = context.getBean("userMapper", UserMapper.class);
        Map<String, Object> map = new HashMap<>();
        Random random = new Random();
        int i = random.nextInt(Integer.MAX_VALUE);
        map.put("id",i);
        map.put("username",username);
        map.put("password",password);
        map.put("gender",gender);
        map.put("nickname",nickname);
        map.put("contact",contact);
        map.put("latitude",latitude);
        map.put("longitude",longitude);
        int res = mapper.addUser(map);
        return res;
    }
    public static int delUser(String id,String username,String nickname){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserMapper mapper = context.getBean("userMapper", UserMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("username",username);
        map.put("nickname",nickname);
        int res = mapper.delUser(map);
        return res;
    }
    public static int updateUser(String id,String username,String password,String gender,String nickname,String contact,String latitude,String longitude,String is_online){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserMapper mapper = context.getBean("userMapper", UserMapper.class);
        Map<String, Object> map = new HashMap<>();
        if(id!=null){
            map.put("id",id);
            List<JSONObject> jsons = queryUser(id, null, null, null, null, null, null, null);
            for (JSONObject json : jsons) {
                if(json!=null){
                    if(username==null){
                        username=json.getString("username");
                    }
                    if(password==null){
                        password=json.getString("password");
                    }
                    if(gender==null){
                        gender=json.getString("gender");
                    }
                    if(nickname==null){
                        nickname=json.getString("nickname");
                    }
                    if(contact==null){
                        contact=json.getString("contact");
                    }
                    if(latitude==null){
                        latitude=json.getString("latitude");
                    }
                    if(longitude==null){
                        longitude=json.getString("longitude");
                    }
                    if(is_online==null){
                        is_online=json.getString("is_online");
                    }
                }
            }
            map.put("username",username);
            map.put("password",password);
            map.put("gender",gender);
            map.put("nickname",nickname);
            map.put("contact",contact);
            map.put("latitude",latitude);
            map.put("longitude",longitude);
            map.put("is_online",is_online);
        }
        else if(username!=null){
            map.put("username",username);
            List<JSONObject> jsons = queryUser(null, username, null, null, null, null, null, null);
            for (JSONObject json : jsons) {
                if(json!=null){
                    if(id==null){
                        id=json.getString("id");
                    }
                    if(password==null){
                        password=json.getString("password");
                    }
                    if(gender==null){
                        gender=json.getString("gender");
                    }
                    if(nickname==null){
                        nickname=json.getString("nickname");
                    }
                    if(contact==null){
                        contact=json.getString("contact");
                    }
                    if(latitude==null){
                        latitude=json.getString("latitude");
                    }
                    if(longitude==null){
                        longitude=json.getString("longitude");
                    }
                    if(is_online==null){
                        is_online=json.getString("is_online");
                    }
                }
            }
            map.put("id",id);
            map.put("password",password);
            map.put("gender",gender);
            map.put("nickname",nickname);
            map.put("contact",contact);
            map.put("latitude",latitude);
            map.put("longitude",longitude);
            map.put("is_online",is_online);
        }
        int res = mapper.updateUser(map);
        return res;
    }
    public static List<JSONObject> queryUser(String id,String username,String password,String gender,String nickname,String contact,String latitude,String longitude){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserMapper mapper = context.getBean("userMapper", UserMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("username",username);
        map.put("password",password);
        map.put("gender",gender);
        map.put("nickname",nickname);
        map.put("contact",contact);
        map.put("latitude",latitude);
        map.put("longitude",longitude);
        List<User> users = mapper.queryUser(map);
        List<JSONObject> list = new ArrayList<>();
        for (User user : users) {
            JSONObject json = user.toJSON();
            list.add(json);
        }
        return list;
    }
}
