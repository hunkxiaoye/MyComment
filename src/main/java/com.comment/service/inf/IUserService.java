package com.comment.service.inf;

import com.comment.model.User;

public interface IUserService {
    User checklogin(String username, String pwd);
}
