package com.example.byc.blog.dao;

import com.example.byc.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*处理业务需要操作数据库 dao
* 数据库是使用JPA所以操作dao得继承JPA(JpaRepository已包括增删改查的方法,只要遵循命名规则)
*
* */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);
}
