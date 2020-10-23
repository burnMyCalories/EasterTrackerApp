package com.dao;

import com.model.User;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class UserMapperImpl implements UserMapper{

    private SqlSessionTemplate sqlSession;

    @Override
    public User queryUserById(int id) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.queryUserById(id);
    }

    @Override
    public List<User> queryUser() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.queryUser();
    }

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession=sqlSession;
    }
}
