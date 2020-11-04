/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: The action model
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
public class UserEggAction implements Serializable {
    private int id;
    private User user;
    private Egg egg;
    private int action;
    private int version;
    private int is_deleted;
    private String creation_time;
    private String update_time;
    public JSONObject toJSON(){
        JSONObject json = new JSONObject(true);
        json.put("id",this.id);
        json.put("user",user.toJSON());
        json.put("egg",egg.toJSON());
        json.put("action",this.action);
        json.put("is_deleted",this.is_deleted);
        json.put("update_time",this.update_time);
        return json;
    }
}
