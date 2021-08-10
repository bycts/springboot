package com.example.byc.blog.service;

import com.example.byc.blog.NotfoundException;
import com.example.byc.blog.dao.BlogRepository;
import com.example.byc.blog.po.Blog;
import com.example.byc.blog.po.Type;
import com.example.byc.blog.util.MyBeanUtils;
import com.example.byc.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService{
    @Autowired
    private BlogRepository blogRepository;
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }


    /*  MarkdownUtils的使用*/
    @Transactional
    @Override
    public Blog getAndConver(Long id) {
        Blog blog = blogRepository.findById(id).get();
        if (blog == null) {
            throw new NotfoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        //String content = b.getContent();
        /* b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));*/
        /*浏览次数累加*/
        blogRepository.updateViews(id);
        return b;

    }

    /*分页 动态组合查询分类 dao中实现接口JpaSpecificationExecutor*/
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
              /*  动态组合条件处理*/
                List<Predicate> predicates=new ArrayList<>();
               /* 标题查询*/
                if (!"".equals(blog.getTitle())&&blog.getTitle()!=null){
                    predicates.add(cb.like(root.<String>get("title"),blog.getTitle()+"%"));/*sql语句title like=%*/
                }
               /* 分类查询 id blog.getType().getId()*/
                if (blog.getTypeId()!=null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
               /* 是否推荐 Boolean类型*/
                if (blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));//where条件
                return null;
            }
        },pageable);
    }
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        /*博客新增*/
        if (blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b=blogRepository.findById(id).get();
        if (b==null){
            throw  new NotfoundException("该博客不存在");
        }
      /*  更新后保存*/
       /* BeanUtils.copyProperties(blog,b);*/
        //更新保存
        /*MyBeanUtils.getNullPropertyNames(blog)过滤掉属性值为空的值*/
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }
    @Transactional
    @Override
    public void deleteBlog(Long id) {
    blogRepository.deleteById(id);
    }


    @Override
    public Page<Blog> listBlog(Pageable pageable) {

        return  blogRepository.findAll(pageable);

    }
    //前端最新推荐显示
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable =PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

//前端标签
    @Override
    public Page<Blog> listBlog(Long id, Pageable pageable) {

        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //关联表查询
                Join join = root.join("tags");
                return cb.equal(join.get("id"),id);
            }
        },pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        /*根据年份归档*/
        List<String> years=blogRepository.findGroupYear();
        Map<String,List<Blog>> map=new LinkedHashMap<>();
        for (String year:years){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }
}
