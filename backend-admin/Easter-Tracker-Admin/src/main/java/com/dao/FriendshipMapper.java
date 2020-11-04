/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: interface of operations on fiendship
 */

package com.dao;

import com.model.Friendship;
import com.model.User;

import java.util.List;
import java.util.Map;

public interface FriendshipMapper {
    int addFriendship(Map<String,Object> map);
    int delFriendship(Map<String,Object> map);
    int updateFriendship(Map<String,Object> map);
    List<Friendship> queryFriendship(Map<String,Object> map);
}
