package com.example.byc.blog.web;

import com.example.byc.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//归档
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model) {
        //年份查询
        model.addAttribute("archiveMap", blogService.archiveBlog());
        //博客数量
        model.addAttribute("blogCount", blogService.countBlog());
        return "archives";
    }
}
