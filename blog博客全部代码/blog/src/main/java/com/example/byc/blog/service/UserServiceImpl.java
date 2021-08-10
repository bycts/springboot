package com.example.byc.blog.service;

import com.example.byc.blog.dao.UserRepository;
import com.example.byc.blog.po.User;
import com.example.byc.blog.util.MDSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*业务处理*/
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username, MDSUtils.code(password));

        return user;
    }
}
