package com.example.byc.blog.service;

import com.example.byc.blog.po.Blog;
import com.example.byc.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);

    Blog getAndConver(Long id);
    //分页
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);
     //前端分类 显示
    Page<Blog> listBlog(Pageable pageable);
    //前端最新推荐显示
    List<Blog> listRecommendBlogTop(Integer size);

    Page<Blog> listBlog(String query,Pageable pageable);

    //前端标签
    Page<Blog> listBlog(Long id,Pageable pageable);

//所有数据归档
    Map<String,List<Blog>> archiveBlog();

    Long countBlog();
}
