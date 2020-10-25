package com.model;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private int id;
    private int friend_id;
    private int type;
    private String content;
    private int version;
    private int is_deleted;
    private Date creation_time;
    private Date update_time;
    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("id",this.id);
        json.put("friend_id",this.friend_id);
        json.put("type",this.type);
        json.put("content",this.content);
        json.put("is_deleted",this.is_deleted);
        return json;
    }
}
