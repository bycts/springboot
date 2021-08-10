package com.example.byc.blog.service;

import com.example.byc.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);
 /*   分页查询*/
    Page<Type> listType(Pageable pageable);

  /*  修改*/

    Type updateType(Long id,Type type);

    void deleteType(Long id);
    /*业务校验，新增的名字不要重复*/
    Type getTypeByName(String name);

    /* 显示搜索框中的分类值*/
    List<Type> listType();
    //前端分类显示
    List<Type> listTypeTop(Integer size);
}
