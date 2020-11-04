/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: store sqlsession to be used when CRUD on friendship
 */

package com.dao;

import com.model.Friendship;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Map;

public class FriendshipMapperImpl implements FriendshipMapper{
    private SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession=sqlSession;
    }

    @Override
    public int addFriendship(Map<String, Object> map) {
        FriendshipMapper mapper = sqlSession.getMapper(FriendshipMapper.class);
        return mapper.addFriendship(map);
    }

    @Override
    public int delFriendship(Map<String, Object> map) {
        FriendshipMapper mapper = sqlSession.getMapper(FriendshipMapper.class);
        return mapper.delFriendship(map);
    }

    @Override
    public int updateFriendship(Map<String, Object> map) {
        FriendshipMapper mapper = sqlSession.getMapper(FriendshipMapper.class);
        return mapper.updateFriendship(map);
    }

    @Override
    public List<Friendship> queryFriendship(Map<String, Object> map) {
        FriendshipMapper mapper = sqlSession.getMapper(FriendshipMapper.class);
        return mapper.queryFriendship(map);
    }
}
