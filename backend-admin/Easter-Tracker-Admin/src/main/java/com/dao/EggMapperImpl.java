/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: store sqlsession to be used when CRUD on egg
 */

package com.dao;

import com.model.Egg;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Map;

public class EggMapperImpl implements EggMapper{
    private SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession=sqlSession;
    }

    @Override
    public int addEgg(Map<String, Object> map) {
        EggMapper mapper = sqlSession.getMapper(EggMapper.class);
        return mapper.addEgg(map);
    }

    @Override
    public int delEgg(Map<String, Object> map) {
        EggMapper mapper = sqlSession.getMapper(EggMapper.class);
        return mapper.delEgg(map);
    }

    @Override
    public int updateEgg(Map<String, Object> map) {
        EggMapper mapper = sqlSession.getMapper(EggMapper.class);
        return mapper.updateEgg(map);
    }

    @Override
    public List<Egg> queryEgg(Map<String, Object> map) {
        EggMapper mapper = sqlSession.getMapper(EggMapper.class);
        return mapper.queryEgg(map);
    }
}
