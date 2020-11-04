/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: interface of operations on user
 */

package com.dao;

import com.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int addUser(Map<String,Object> map);
    int delUser(Map<String,Object> map);
    int updateUser(Map<String,Object> map);
    List<User> queryUser(Map<String,Object> map);
    @Select("select count(user_id) as getnum from UserEggAction where action=2 and user_id=#{id} group by user_id")
    Integer selectGet(@Param("id") int id);
    @Select("select count(user_id) as putnum from UserEggAction where action=1 and user_id=#{id} group by user_id")
    Integer selectSet(@Param("id") int id);
}
