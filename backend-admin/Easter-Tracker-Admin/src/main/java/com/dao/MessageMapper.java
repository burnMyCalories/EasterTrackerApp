package com.dao;

import com.model.Friendship;
import com.model.Message;

import java.util.List;
import java.util.Map;

public interface MessageMapper {
    int addMessage(Map<String,Object> map);
    int delMessage(Map<String,Object> map);
    int updateMessage(Map<String,Object> map);
    List<Message> queryMessage(Map<String,Object> map);
}
