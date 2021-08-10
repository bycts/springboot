package com.example.byc.blog.dao;


import com.example.byc.blog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by limi on 2017/10/16.
 */
public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);
    //前端标签显示 自定义查询
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
