package com.dao;

import com.model.Friendship;
import com.model.User;

import java.util.List;
import java.util.Map;

public interface FriendshipMapper {
    int addFriendship(Map<String,Object> map);
    int delFriendship(Map<String,Object> map);
    int updateFriendship(Map<String,Object> map);
    List<Friendship> queryFriendship(Map<String,Object> map);
}
