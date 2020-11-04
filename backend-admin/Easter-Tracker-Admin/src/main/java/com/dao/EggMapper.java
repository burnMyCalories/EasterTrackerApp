/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: interface of operations on egg
 */

package com.dao;

import com.model.Egg;
import com.model.Friendship;

import java.util.List;
import java.util.Map;

public interface EggMapper {
    int addEgg(Map<String,Object> map);
    int delEgg(Map<String,Object> map);
    int updateEgg(Map<String,Object> map);
    List<Egg> queryEgg(Map<String,Object> map);
}
