package com.comment.service.imp;

import com.comment.dao.UserDao;
import com.comment.model.User;
import com.comment.service.inf.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImp implements IUserService {
    @Autowired
    private UserDao userdao;

    @Transactional
    public User checklogin(String username, String pwd) {
        User user = userdao.findByUsername(username);
        if (user != null && user.getPwd().equals(pwd)) {
            return user;
        }
        return null;
    }
}
