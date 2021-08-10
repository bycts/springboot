package com.example.byc.blog.web;

import com.example.byc.blog.NotfoundException;
import com.example.byc.blog.service.BlogService;
import com.example.byc.blog.service.TagService;
import com.example.byc.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;


//    @RequestMapping("/{id}/{name}")
//    public String index(@PathVariable Integer id,@PathVariable String name){
//      // int i=9/0;/*异常处理*/
//        String blog=null;/*异常处理带主界面 自定义异常*/
//        if (blog==null){
//            throw new NotfoundException("博客不存在");
//        }
//        System.out.println("------------------index");
//
//        return  "index";
//    }

    @RequestMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        //前端分页
        model.addAttribute("page",blogService.listBlog(pageable));
        //前端分类
        model.addAttribute("types", typeService.listTypeTop(6));
        //前端标签
        model.addAttribute("tags", tagService.listTagTop(10));
        //前端最新推荐显示
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }


    //前端搜索
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query,Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @RequestMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConver(id));
        return  "blog";
    }



}
