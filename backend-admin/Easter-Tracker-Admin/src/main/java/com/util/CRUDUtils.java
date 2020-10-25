package com.util;

import com.alibaba.fastjson.JSONObject;
import com.dao.EggMapper;
import com.dao.FriendshipMapper;
import com.dao.MessageMapper;
import com.dao.UserMapper;
import com.model.Egg;
import com.model.Friendship;
import com.model.Message;
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
                    break;
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
                    break;
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
                        break;
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
                        break;
                    }
                }
            }
        }
        map.put("id",id);
        map.put("is_deleted",is_deleted);
        int res = mapper.updateFriendship(map);
        return res;
    }
    public static int addMessage(String friend_id,String type,String content){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        MessageMapper mapper = context.getBean("messageMapper", MessageMapper.class);
        Random random = new Random();
        Map<String, Object> map = new HashMap<>();
        int i = random.nextInt(Integer.MAX_VALUE);
        map.put("id",i);
        map.put("friend_id",friend_id);
        map.put("type",type);
        map.put("content",content);
        int res = mapper.addMessage(map);
        return res;
    }
    public static int delMessage(String id,String friend_id){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        MessageMapper mapper = context.getBean("messageMapper", MessageMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("friend_id",friend_id);
        int res = mapper.delMessage(map);
        return res;
    }
    public static int updateMessage(String id,String friend_id,String is_deleted){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        MessageMapper mapper = context.getBean("messageMapper", MessageMapper.class);
        Map<String, Object> map = new HashMap<>();
        if(id!=null){
            List<JSONObject> jsons = queryMessage(id, null);
            for (JSONObject json : jsons) {
                if(json!=null){
                    if(is_deleted==null){
                        is_deleted=json.getString("is_deleted");
                        break;
                    }
                }
            }
        }
        else if(friend_id!=null){
            List<JSONObject> jsons = queryMessage(null, friend_id);
            int count=0;
            for (JSONObject json : jsons) {
                if(json!=null){
                    id = json.getString("id");
                    updateMessage(id, null, is_deleted);
                    count++;
                }
            }
            return count;
        }
        map.put("id",id);
        map.put("is_deleted",is_deleted);
        int res = mapper.updateMessage(map);
        return res;
    }
    public static List<JSONObject> queryMessage(String id,String friend_id){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        MessageMapper mapper = context.getBean("messageMapper", MessageMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("friend_id",friend_id);
        List<Message> messages = mapper.queryMessage(map);
        List<JSONObject> list = new ArrayList<>();
        for (Message message : messages) {
            JSONObject json = message.toJSON();
            list.add(json);
        }
        return list;
    }
    public static int addEgg(String name,String color,String type,String latitude,String longitude,String content){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        EggMapper mapper = context.getBean("eggMapper", EggMapper.class);
        Map<String, Object> map = new HashMap<>();
        Random random = new Random();
        int i = random.nextInt(Integer.MAX_VALUE);
        map.put("id",i);
        map.put("name",name);
        map.put("color",color);
        map.put("type",type);
        map.put("latitude",latitude);
        map.put("longitude",longitude);
        map.put("content",content);
        int res = mapper.addEgg(map);
        return res;
    }
    public static int delEgg(String id,String name){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        EggMapper mapper = context.getBean("eggMapper", EggMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        int res = mapper.delEgg(map);
        return res;
    }
    public static int updateEgg(String id,String name,String color,String type,String latitude,String longitude,String content,String is_deleted){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        EggMapper mapper = context.getBean("eggMapper", EggMapper.class);
        Map<String, Object> map = new HashMap<>();
        if(id!=null){
            List<JSONObject> jsons = queryEgg(id, null, null, null, null, null);
            for (JSONObject json : jsons) {
                if(json!=null){
                    if(name==null){
                        name=json.getString("name");
                    }
                    if(color==null){
                        color=json.getString("color");
                    }
                    if(type==null){
                        type=json.getString("type");
                    }
                    if(latitude==null){
                        latitude=json.getString("latitude");
                    }
                    if(longitude==null){
                        longitude=json.getString("longitude");
                    }
                    if(content==null){
                        content=json.getString("content");
                    }
                    if(is_deleted==null){
                        is_deleted=json.getString("is_deleted");
                    }
                    break;
                }
            }
            map.put("id",id);
            map.put("name",name);
            map.put("color",color);
            map.put("type",type);
            map.put("latitude",latitude);
            map.put("longitude",longitude);
            map.put("content",content);
            map.put("is_deleted",is_deleted);
        }
        else if(name!=null){
            List<JSONObject> jsons = queryEgg(null, name, null, null, null, null);
            for (JSONObject json : jsons) {
                if(json!=null){
                    if(id==null){
                        id=json.getString("id");
                    }
                    if(color==null){
                        color=json.getString("color");
                    }
                    if(type==null){
                        type=json.getString("type");
                    }
                    if(latitude==null){
                        latitude=json.getString("latitude");
                    }
                    if(longitude==null){
                        longitude=json.getString("longitude");
                    }
                    if(content==null){
                        content=json.getString("content");
                    }
                    if(is_deleted==null){
                        is_deleted=json.getString("is_deleted");
                    }
                    break;
                }
            }
            map.put("id",id);
            map.put("name",name);
            map.put("color",color);
            map.put("type",type);
            map.put("latitude",latitude);
            map.put("longitude",longitude);
            map.put("content",content);
            map.put("is_deleted",is_deleted);
        }
        int res = mapper.updateEgg(map);
        return res;
    }
    public static List<JSONObject> queryEgg(String id,String name,String color,String type,String latitude,String longitude){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        EggMapper mapper = context.getBean("eggMapper", EggMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        map.put("color",color);
        map.put("type",type);
        map.put("latitude",latitude);
        map.put("longitude",longitude);
        List<Egg> eggs = mapper.queryEgg(map);
        List<JSONObject> list = new ArrayList<>();
        for (Egg egg : eggs) {
            JSONObject json = egg.toJSON();
            list.add(json);
        }
        return list;
    }
}
