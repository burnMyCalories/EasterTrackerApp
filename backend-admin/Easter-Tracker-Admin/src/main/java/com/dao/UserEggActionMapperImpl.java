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
}
