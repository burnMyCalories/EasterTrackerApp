package com.dao;

import com.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int addUser(Map<String,Object> map);
    int delUser(Map<String,Object> map);
    int updateUser(Map<String,Object> map);
    List<User> queryUser(Map<String,Object> map);
}
