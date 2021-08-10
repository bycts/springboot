package com.example.byc.blog.web.admin;

import com.example.byc.blog.po.Type;
import com.example.byc.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*1.Pageable概述#
        Page<User> findByAge(int age, Pageable pageable);
        Pageable 是Spring Data库中定义的一个接口，
        用于构造翻页查询，是所有分页相关信息的一个抽象，通过该接口，我们可以得到和分页相关所有信息
        （例如pageNumber、pageSize等），这样，Jpa就能够通过pageable参数来得到一个带分页信息的Sql语句。*/
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
       /* 每页10条，根据ID倒叙排序*/
        //typeService.listType(pageable);
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
       /* 后端校验   model.addAttribute("type", new Type()); Model model*/
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String post(Type type, BindingResult result,
                       RedirectAttributes attributes
                       ){
       /*业务校验，新增的名字不要重复*/
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            //自定义错误信息
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
       /* 后端检验
       @Valid Type type, BindingResult result,

       if (result.hasErrors()){
            return "redirect:/admin/input";
        }*/
        //使用了redirect不能用model
       Type t= typeService.saveType(type);
       if (t==null){
           attributes.addFlashAttribute("message", "新增失败");
       }else{
           attributes.addFlashAttribute("message", "新增成功");
       }
        return "redirect:/admin/types";
    }

    /*根据id修改，先查到再改*/
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }
   /* 修改方法*/
    @PostMapping("/types/{id}")
    public String editPost(Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        Type t = typeService.updateType(id,type);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
