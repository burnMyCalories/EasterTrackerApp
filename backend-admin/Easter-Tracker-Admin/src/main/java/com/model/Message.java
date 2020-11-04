/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: The message model
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
public class Message implements Serializable {
    private int id;
    private Friendship friend;
    private int type;
    private String content;
    private int version;
    private int is_deleted;
    private String creation_time;
    private String update_time;
    public JSONObject toJSON(){
        JSONObject json = new JSONObject(true);
        json.put("id",this.id);
        json.put("friend",friend!=null?friend.toJSON():null);
        json.put("type",this.type);
        json.put("content",this.content);
        json.put("is_deleted",this.is_deleted);
        return json;
    }
}
