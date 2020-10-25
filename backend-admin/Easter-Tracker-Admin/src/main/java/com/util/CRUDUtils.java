package com.util;

import com.alibaba.fastjson.JSONObject;
import com.dao.FriendshipMapper;
import com.dao.UserMapper;
import com.model.Friendship;
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
    public static int updateUser(String id,String username,String password,String gender,String nickname,String contact,String latitude,String longitude,String is_online,String is_deleted){
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
                    if(is_deleted==null){
                        is_deleted=json.getString("is_deleted");
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
            map.put("is_deleted",is_deleted);
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
                    if(is_deleted==null){
                        is_deleted=json.getString("is_deleted");
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
            map.put("is_deleted",is_deleted);
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
    public static int addFriendship(String userfrom_id,String userto_id){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        FriendshipMapper mapper = context.getBean("friendshipMapper", FriendshipMapper.class);
        Random random = new Random();
        Map<String, Object> map = new HashMap<>();
        int i = random.nextInt(Integer.MAX_VALUE);
        map.put("id",i);
        map.put("userfrom_id",userfrom_id);
        map.put("userto_id",userto_id);
        int res = mapper.addFriendship(map);
        return res;
    }
    public static int delFriendship(String id,String userfrom_id,String userto_id){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        FriendshipMapper mapper = context.getBean("friendshipMapper", FriendshipMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("userfrom_id",userfrom_id);
        map.put("userto_id",userto_id);
        int res = mapper.delFriendship(map);
        return res;
    }
    public static List<JSONObject> queryFriendship(String id,String userfrom_id,String userto_id){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        FriendshipMapper mapper = context.getBean("friendshipMapper", FriendshipMapper.class);
        Map<String, Object> map = new HashMap<>();;
        map.put("id",id);
        map.put("userfrom_id",userfrom_id);
        map.put("userto_id",userto_id);
        List<Friendship> friendships = mapper.queryFriendship(map);
        List<JSONObject> list = new ArrayList<>();
        for (Friendship friendship : friendships) {
            JSONObject json = friendship.toJSON();
            list.add(json);
        }
        return list;
    }
    public static int updateFriendship(String id,String userfrom_id,String userto_id,String is_deleted){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        FriendshipMapper mapper = context.getBean("friendshipMapper", FriendshipMapper.class);
        Map<String, Object> map = new HashMap<>();
        if(id!=null) {
            List<JSONObject> jsons = queryFriendship(id, null, null);
            for (JSONObject json : jsons) {
                if(json!=null){
                    if(is_deleted==null){
                        is_deleted=json.getString("is_deleted");
                    }
                }
            }
        }
        else if(userfrom_id!=null&&userto_id!=null){
            List<JSONObject> jsons = queryFriendship(null, userfrom_id, userto_id);
            for (JSONObject json : jsons) {
                if(json!=null){
                    id=json.getString("id");
                    if(is_deleted==null){
                        is_deleted=json.getString("is_deleted");
                    }
                }
            }
        }
        map.put("id",id);
        map.put("is_deleted",is_deleted);
        int res = mapper.updateFriendship(map);
        return res;
    }
}
