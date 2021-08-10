package com.example.byc.blog.dao;

import com.example.byc.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//动态组合查询  JpaSpecificationExecutor
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    //前端最新推荐显示
    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);
    @Query("select b from Blog b where b.title like ?1 or b.content like ?2")
    Page<Blog> findByQuery(String query,Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Blog b set b.views=b.views+1  where b.id=?1")
    int updateViews(Long id);
//查年份
    @Query("select function('date_format',b.updateTime,'%Y') as year " +
            "from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();
//根据年份查询
    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);
}
