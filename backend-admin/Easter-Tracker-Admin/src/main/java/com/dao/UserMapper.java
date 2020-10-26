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
