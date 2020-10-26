package com.util;

import com.alibaba.fastjson.JSONObject;
import com.dao.*;
import com.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class CRUDUtils {
    public static JSONObject addUser(String username,String password,String gender,String nickname,String contact,String latitude,String longitude){
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
        JSONObject json = new JSONObject();
        json.put("data",queryUser(String.valueOf(i),username,password,gender,nickname,contact,latitude,longitude).get("data"));
        json.put("rows",res);
        return json;
    }
    public static JSONObject delUser(String id,String username,String nickname){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserMapper mapper = context.getBean("userMapper", UserMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("username",username);
        map.put("nickname",nickname);
        JSONObject json = new JSONObject();
        json.put("data",queryUser(id,username,nickname,null,null,null,null,null).get("data"));
        int res = mapper.delUser(map);
        json.put("rows",res);
        return json;
    }
    public static JSONObject updateUser(String id,String username,String password,String gender,String nickname,String contact,String latitude,String longitude,String is_online,String is_deleted){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserMapper mapper = context.getBean("userMapper", UserMapper.class);
        Map<String, Object> map = new HashMap<>();
        if(id!=null){
            map.put("id",id);
            List<JSONObject> jsons = (List<JSONObject>) queryUser(id, null, null, null, null, null, null, null).get("data");
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
            List<JSONObject> jsons = (List<JSONObject>) queryUser(null, username, null, null, null, null, null, null).get("data");
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
        JSONObject json = new JSONObject();
        json.put("data",queryUser(id,username,password,gender,nickname,contact,latitude,longitude).get("data"));
        json.put("rows",res);
        return json;
    }
    public static JSONObject queryUser(String id,String username,String password,String gender,String nickname,String contact,String latitude,String longitude){
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
        JSONObject json = new JSONObject();
        json.put("data",list);
        json.put("rows",list.size());
        return json;
    }
    public static JSONObject addFriendship(String userfrom_id,String userto_id){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        FriendshipMapper mapper = context.getBean("friendshipMapper", FriendshipMapper.class);
        Random random = new Random();
        Map<String, Object> map = new HashMap<>();
        int i = random.nextInt(Integer.MAX_VALUE);
        map.put("id",i);
        map.put("userfrom_id",userfrom_id);
        map.put("userto_id",userto_id);
        int res = mapper.addFriendship(map);
        JSONObject json = new JSONObject();
        json.put("data",queryFriendship(String.valueOf(i),userfrom_id,userto_id,null,null,null,null).get("data"));
        json.put("rows",res);
        return json;
    }
    public static JSONObject delFriendship(String id,String userfrom_id,String userto_id){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        FriendshipMapper mapper = context.getBean("friendshipMapper", FriendshipMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("userfrom_id",userfrom_id);
        map.put("userto_id",userto_id);
        JSONObject json = new JSONObject();
        json.put("data",queryFriendship(id,userfrom_id,userto_id,null,null,null,null).get("data"));
        int res = mapper.delFriendship(map);
        json.put("rows",res);
        return json;
    }
    public static JSONObject queryFriendship(String id,String userfrom_id,String userto_id,String userfrom_username,String userto_username,String userfrom_nickname,String userto_nickname){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        FriendshipMapper mapper = context.getBean("friendshipMapper", FriendshipMapper.class);
        Map<String, Object> map = new HashMap<>();;
        map.put("id",id);
        map.put("userfrom_id",userfrom_id);
        map.put("userto_id",userto_id);
        map.put("userfrom_username",userfrom_username);
        map.put("userto_username",userto_username);
        map.put("userfrom_nickname",userfrom_nickname);
        map.put("userto_nickname",userto_nickname);
        List<Friendship> friendships = mapper.queryFriendship(map);
        List<JSONObject> list = new ArrayList<>();
        for (Friendship friendship : friendships) {
            JSONObject json = friendship.toJSON();
            list.add(json);
        }
        JSONObject json = new JSONObject();
        json.put("data",list);
        json.put("rows",list.size());
        return json;
    }
    public static JSONObject updateFriendship(String id,String userfrom_id,String userto_id,String is_deleted){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        FriendshipMapper mapper = context.getBean("friendshipMapper", FriendshipMapper.class);
        Map<String, Object> map = new HashMap<>();
        if(id!=null) {
            List<JSONObject> jsons = (List<JSONObject>) queryFriendship(id, null, null, null, null, null, null).get("data");
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
            List<JSONObject> jsons = (List<JSONObject>) queryFriendship(null, userfrom_id, userto_id, null, null, null,null).get("data");
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
        JSONObject json = new JSONObject();
        json.put("data",queryFriendship(id,userfrom_id,userto_id,null,null,null,null).get("data"));
        json.put("rows",res);
        return json;
    }
    public static JSONObject addMessage(String friend_id,String type,String content){
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
        JSONObject json = new JSONObject();
        json.put("data",queryMessage(String.valueOf(i),friend_id,null,null,null,null,null,null).get("data"));
        json.put("rows",res);
        return json;
    }
    public static JSONObject delMessage(String id,String friend_id){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        MessageMapper mapper = context.getBean("messageMapper", MessageMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("friend_id",friend_id);
        JSONObject json = new JSONObject();
        json.put("data",queryMessage(id,friend_id,null,null,null,null,null,null).get("data"));
        int res = mapper.delMessage(map);
        json.put("rows",res);
        return json;
    }
    public static JSONObject updateMessage(String id,String friend_id,String is_deleted){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        MessageMapper mapper = context.getBean("messageMapper", MessageMapper.class);
        Map<String, Object> map = new HashMap<>();
        if(id!=null){
            List<JSONObject> jsons = (List<JSONObject>) queryMessage(id, null,null,null,null,null,null,null).get("data");
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
            List<JSONObject> jsons = (List<JSONObject>) queryMessage(null, friend_id,null,null,null,null,null,null).get("data");
            int count=0;
            for (JSONObject json : jsons) {
                if(json!=null){
                    id = json.getString("id");
                    updateMessage(id, null, is_deleted);
                    count++;
                }
            }
            JSONObject json = new JSONObject();
            json.put("data",queryMessage(null,friend_id,null,null,null,null,null,null).get("data"));
            json.put("rows",count);
        }
        map.put("id",id);
        map.put("is_deleted",is_deleted);
        int res = mapper.updateMessage(map);
        JSONObject json = new JSONObject();
        json.put("data",queryMessage(id,friend_id,null,null,null,null,null,null).get("data"));
        json.put("rows",res);
        return json;
    }
    public static JSONObject queryMessage(String id,String friend_id,String userfrom_id,String userto_id,String userfrom_username,String userto_username,String userfrom_nickname,String userto_nickname){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        MessageMapper mapper = context.getBean("messageMapper", MessageMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("friend_id",friend_id);
        map.put("userfrom_id",userfrom_id);
        map.put("userto_id",userto_id);
        map.put("userfrom_username",userfrom_username);
        map.put("userto_username",userto_username);
        map.put("userfrom_nickname",userfrom_nickname);
        map.put("userto_nickname",userto_nickname);
        List<Message> messages = mapper.queryMessage(map);
        List<JSONObject> list = new ArrayList<>();
        for (Message message : messages) {
            JSONObject json = message.toJSON();
            list.add(json);
        }
        JSONObject json = new JSONObject();
        json.put("data",list);
        json.put("rows",list.size());
        return json;
    }
    public static JSONObject addEgg(String name,String color,String type,String latitude,String longitude,String content,String expire_time){
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
        map.put("expire_time",expire_time);
        int res = mapper.addEgg(map);
        JSONObject json = new JSONObject();
        json.put("data",queryEgg(String.valueOf(i),name,color,type,latitude,longitude).get("data"));
        json.put("rows",res);
        return json;
    }
    public static JSONObject delEgg(String id,String name){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        EggMapper mapper = context.getBean("eggMapper", EggMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        JSONObject json = new JSONObject();
        json.put("data",queryEgg(id,name,null,null,null,null).get("data"));
        int res = mapper.delEgg(map);
        json.put("rows",res);
        return json;
    }
    public static JSONObject updateEgg(String id,String name,String color,String type,String latitude,String longitude,String content,String expire_time,String is_deleted){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        EggMapper mapper = context.getBean("eggMapper", EggMapper.class);
        Map<String, Object> map = new HashMap<>();
        if(id!=null){
            List<JSONObject> jsons = (List<JSONObject>) queryEgg(id, null, null, null, null, null).get("data");
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
                    if(expire_time==null){
                        expire_time=json.getString("expire_time");
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
            map.put("expire_time",expire_time);
            map.put("is_deleted",is_deleted);
        }
        else if(name!=null){
            List<JSONObject> jsons = (List<JSONObject>) queryEgg(null, name, null, null, null, null).get("data");
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
                    if(expire_time==null){
                        expire_time=json.getString("expire_time");
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
            map.put("expire_time",expire_time);
            map.put("is_deleted",is_deleted);
        }
        int res = mapper.updateEgg(map);
        JSONObject json = new JSONObject();
        json.put("data",queryEgg(id,name,color,type,latitude,longitude).get("data"));
        json.put("rows",res);
        return json;
    }
    public static JSONObject queryEgg(String id,String name,String color,String type,String latitude,String longitude){
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
        JSONObject json = new JSONObject();
        json.put("data",list);
        json.put("rows",list.size());
        return json;
    }
    public static JSONObject addAction(String user_id,String egg_id,String action){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserEggActionMapper mapper = context.getBean("userEggActionMapper", UserEggActionMapper.class);
        Map<String, Object> map = new HashMap<>();
        Random random = new Random();
        int i = random.nextInt(Integer.MAX_VALUE);
        map.put("id",i);
        map.put("user_id",user_id);
        map.put("egg_id",egg_id);
        map.put("action",action);
        int res = mapper.addAction(map);
        JSONObject json = new JSONObject();
        json.put("data",queryAction(String.valueOf(i),user_id,egg_id,action,null,null,null).get("data"));
        json.put("rows",res);
        return json;
    }
    public static JSONObject delAction(String id,String user_id,String egg_id,String action){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserEggActionMapper mapper = context.getBean("userEggActionMapper", UserEggActionMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("user_id",user_id);
        map.put("egg_id",egg_id);
        map.put("action",action);
        JSONObject json = new JSONObject();
        json.put("data",queryAction(id,user_id,egg_id,action,null,null,null).get("data"));
        int res = mapper.delAction(map);
        json.put("rows",res);
        return json;
    }
    public static JSONObject updateAction(String id,String user_id,String egg_id,String action,String is_deleted){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserEggActionMapper mapper = context.getBean("userEggActionMapper", UserEggActionMapper.class);
        Map<String, Object> map = new HashMap<>();
        if(id!=null){
            List<JSONObject> jsons = (List<JSONObject>) queryAction(id, null, null, null, null, null, null).get("data");
            for (JSONObject json : jsons) {
                if(json!=null){
                    if(user_id==null){
                        user_id=json.getString("user_id");
                    }
                    if(egg_id==null){
                        egg_id=json.getString("egg_id");
                    }
                    if(action==null){
                        action=json.getString("action");
                    }
                    if(is_deleted==null){
                        is_deleted=json.getString("is_deleted");
                    }
                    break;
                }
            }
            map.put("id",id);
            map.put("user_id",user_id);
            map.put("egg_id",egg_id);
            map.put("action",action);
            map.put("is_deleted",is_deleted);
        }
        else if(user_id!=null&&egg_id!=null&&action!=null){
            List<JSONObject> jsons = (List<JSONObject>) queryAction(null, user_id, egg_id, action, null, null, null).get("data");
            for (JSONObject json : jsons) {
                if(json!=null){
                    if(id==null){
                        id=json.getString("id");
                    }
                    if(is_deleted==null){
                        is_deleted=json.getString("is_deleted");
                    }
                    break;
                }
            }
            map.put("id",id);
            map.put("user_id",user_id);
            map.put("egg_id",egg_id);
            map.put("action",action);
            map.put("is_deleted",is_deleted);
        }
        int res = mapper.updateAction(map);
        JSONObject json = new JSONObject();
        json.put("data",queryAction(id,user_id,egg_id,action,null,null,null).get("data"));
        json.put("rows",res);
        return json;
    }
    public static JSONObject queryAction(String id,String user_id,String egg_id,String action,String user_username,String user_nickname,String egg_name){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserEggActionMapper mapper = context.getBean("userEggActionMapper", UserEggActionMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("user_id",user_id);
        map.put("egg_id",egg_id);
        map.put("action",action);
        map.put("user_username",user_username);
        map.put("user_nickname",user_nickname);
        map.put("egg_name",egg_name);
        List<UserEggAction> userEggActions = mapper.queryAction(map);
        List<JSONObject> list = new ArrayList<>();
        for (UserEggAction userEggAction : userEggActions) {
            JSONObject json = userEggAction.toJSON();
            list.add(json);
        }
        JSONObject json = new JSONObject();
        json.put("data",list);
        json.put("rows",list.size());
        return json;
    }
}
