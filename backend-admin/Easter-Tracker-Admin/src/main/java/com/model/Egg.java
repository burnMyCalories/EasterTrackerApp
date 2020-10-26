package com.model;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Egg {
    private int id;
    private String name;
    private String color;
    private int type;
    private double latitude;
    private double longitude;
    private String content;
    private String expire_time;
    private int version;
    private int is_deleted;
    private String creation_time;
    private String update_time;
    public JSONObject toJSON(){
        JSONObject json = new JSONObject(true);
        json.put("id",this.id);
        json.put("name",this.name);
        json.put("color",this.color);
        json.put("type",this.type);
        json.put("latitude",this.latitude);
        json.put("longitude",this.longitude);
        json.put("content",this.content);
        json.put("expire_time",this.expire_time);
        json.put("is_deleted",this.is_deleted);
        return json;
    }
}
