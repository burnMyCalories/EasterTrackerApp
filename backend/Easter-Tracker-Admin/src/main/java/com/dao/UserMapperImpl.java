/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: store sqlsession to be used when CRUD on user
 */

package com.dao;

import com.model.User;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Map;

public class UserMapperImpl implements UserMapper{

    private SqlSessionTemplate sqlSession;



    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession=sqlSession;
    }

    @Override
    public int addUser(Map<String, Object> map) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.addUser(map);
    }

    @Override
    public int delUser(Map<String,Object> map) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.delUser(map);
    }

    @Override
    public int updateUser(Map<String, Object> map) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.updateUser(map);
    }

    @Override
    public List<User> queryUser(Map<String, Object> map) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.queryUser(map);
    }

    @Override
    public Integer selectGet(int id) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.selectGet(id);
    }

    @Override
    public Integer selectSet(int id) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.selectSet(id);
    }


}
