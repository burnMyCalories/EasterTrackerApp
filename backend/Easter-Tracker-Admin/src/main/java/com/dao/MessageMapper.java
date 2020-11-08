/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: interface of operations on message
 */

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
