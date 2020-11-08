/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: store sqlsession to be used when CRUD on action
 */

package com.dao;


import com.model.UserEggAction;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Map;

public class UserEggActionMapperImpl implements UserEggActionMapper {
    private SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession=sqlSession;
    }

    @Override
    public int addAction(Map<String, Object> map) {
        UserEggActionMapper mapper = sqlSession.getMapper(UserEggActionMapper.class);
        return mapper.addAction(map);
    }

    @Override
    public int delAction(Map<String, Object> map) {
        UserEggActionMapper mapper = sqlSession.getMapper(UserEggActionMapper.class);
        return mapper.delAction(map);
    }

    @Override
    public int updateAction(Map<String, Object> map) {
        UserEggActionMapper mapper = sqlSession.getMapper(UserEggActionMapper.class);
        return mapper.updateAction(map);
    }

    @Override
    public List<UserEggAction> queryAction(Map<String, Object> map) {
        UserEggActionMapper mapper = sqlSession.getMapper(UserEggActionMapper.class);
        return mapper.queryAction(map);
    }

    @Override
    public List<UserEggAction> querySpecialAction(Map<String, Object> map) {
        UserEggActionMapper mapper = sqlSession.getMapper(UserEggActionMapper.class);
        return mapper.querySpecialAction(map);
    }
}
