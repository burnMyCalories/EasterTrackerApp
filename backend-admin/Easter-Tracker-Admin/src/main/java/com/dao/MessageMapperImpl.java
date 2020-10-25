package com.dao;

import com.model.Message;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Map;

public class MessageMapperImpl implements MessageMapper{
    private SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession=sqlSession;
    }

    @Override
    public int addMessage(Map<String, Object> map) {
        MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
        return mapper.addMessage(map);
    }

    @Override
    public int delMessage(Map<String, Object> map) {
        MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
        return mapper.delMessage(map);
    }

    @Override
    public int updateMessage(Map<String, Object> map) {
        MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
        return mapper.updateMessage(map);
    }

    @Override
    public List<Message> queryMessage(Map<String, Object> map) {
        MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
        return mapper.queryMessage(map);
    }
}
