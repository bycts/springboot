package com.example.byc.blog.service;
/*业务处理接口*/

import com.example.byc.blog.po.User;

public interface UserService {

    User checkUser(String name,String password);
}
