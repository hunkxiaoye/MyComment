package com.comment.dao;

import com.comment.model.User;

public interface UserDao {

    User findByUsername(String username);
}
