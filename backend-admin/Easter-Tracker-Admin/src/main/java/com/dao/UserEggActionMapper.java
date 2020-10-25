package com.dao;

import com.model.Egg;
import com.model.UserEggAction;

import java.util.List;
import java.util.Map;

public interface UserEggActionMapper {
    int addAction(Map<String,Object> map);
    int delAction(Map<String,Object> map);
    int updateAction(Map<String,Object> map);
    List<UserEggAction> queryAction(Map<String,Object> map);
}
