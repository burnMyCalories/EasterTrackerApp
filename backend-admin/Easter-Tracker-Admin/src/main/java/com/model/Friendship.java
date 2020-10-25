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
    private int userfrom_id;
    private int userto_id;
    private int version;
    private int is_deleted;
    private String creation_time;
    private String update_time;
    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("id",this.id);
        json.put("userfrom_id",this.userfrom_id);
        json.put("userto_id",this.userto_id);
        json.put("is_deleted",this.is_deleted);
        return json;
    }
}
