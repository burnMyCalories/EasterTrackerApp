package com.dao;

import com.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int addUser(Map<String,Object> map);
    int delUser(@Param("id") int id);
    int updateUser(Map<String,Object> map);
    User queryUser(Map<String,Object> map);
}
