package com.model;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEggAction {
    private int id;
    private int user_id;
    private int egg_id;
    private int action;
    private int version;
    private int is_deleted;
    private String creation_time;
    private String update_time;
    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("id",this.id);
        json.put("user_id",this.user_id);
        json.put("egg_id",this.egg_id);
        json.put("action",this.action);
        json.put("is_deleted",this.is_deleted);
        json.put("update_time",this.update_time);
        return json;
    }
}
