package com.model;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friendship {
    private int id;
    private User userfrom;
    private User userto;
    private int version;
    private int is_deleted;
    private String creation_time;
    private String update_time;
    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("id",this.id);
        json.put("userfrom",userfrom!=null?userfrom.toJSON():null);
        json.put("userto",userto!=null?userto.toJSON():null);
        json.put("is_deleted",this.is_deleted);
        return json;
    }
}
