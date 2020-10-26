package com.model;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        JSONObject json = new JSONObject();
        json.put("id",this.id);
        json.put("username",this.username);
        json.put("password",this.password);
        json.put("gender",this.gender);
        json.put("nickname",this.nickname);
        json.put("contact",this.contact);
        json.put("is_active",this.is_active);
        json.put("is_online",this.is_online);
        json.put("latitude",this.latitude);
        json.put("longitude",this.longitude);
        json.put("is_deleted",this.is_deleted);
        return json;
    }
}
