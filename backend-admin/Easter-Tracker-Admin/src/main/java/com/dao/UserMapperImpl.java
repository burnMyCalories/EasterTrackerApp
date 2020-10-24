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
    public int delUser(int id) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.delUser(id);
    }

    @Override
    public int updateUser(Map<String, Object> map) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.updateUser(map);
    }

    @Override
    public User queryUser(Map<String, Object> map) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.queryUser(map);
    }


}
