/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: The egg model
 */

package com.model;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Egg implements Serializable {
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
