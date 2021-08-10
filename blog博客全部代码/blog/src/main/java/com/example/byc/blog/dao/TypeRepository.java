package com.example.byc.blog.dao;

import com.example.byc.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {
    /*业务校验，新增的名字不要重复*/

    Type findByName(String name);
    //前端分类显示 自定义查询
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
