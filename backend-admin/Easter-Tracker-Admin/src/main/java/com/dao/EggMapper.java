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
