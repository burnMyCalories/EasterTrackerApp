package com.dao;

import com.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    User queryUserById(@Param("id") int id);
    List<User> queryUser();
}
