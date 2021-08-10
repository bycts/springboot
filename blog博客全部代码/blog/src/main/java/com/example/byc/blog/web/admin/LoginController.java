package com.example.byc.blog.web.admin;

import com.example.byc.blog.po.User;
import com.example.byc.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/*调用业务处理层方法
* @PathVariable主要用于接收http://host:port/path/{参数值}数据。@RequestParam主要用于接收http://host:port/path?参数名=参数值数据，这里后面也可以不跟参数值。

@RequestParam和@PathVariable这两者之间区别不大，主要是请求的URL不一样

用@RequestParam请求接口时,URL是:http://www.test.com/user/getUserById?userId=1

用@PathVariable请求接口时,URL是:http://www.test.com/user/getUserById/2
*
* @Autowired注释,一般用在Service前面,Dao前面,Config前面,
* @Autowired注释对在哪里和如何完成自动连接提供了更多的精细化控制。
*
* @GetMapping：直接从URL获得数据，以www.xxx.com/?id=1类似这种形式传输。
* @PostMapping请求会从body部分拿到数据，好处是一些不希望用户看到的数据会放在body里面传输。
* */
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

   @GetMapping
    public String loginPage(){
        return "admin/login";
    }
    /*登录*/
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user=userService.checkUser(username,password);
        if (user==null){
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            /*使用attributes来提示错误信息*/
            /*不可以使用model 使用了重定向*/
            attributes.addFlashAttribute("msg","用户名和密码错误");
            //使用重定向避免错误
            return "redirect:/admin";
        }

    }

    /*注销*/
    @GetMapping("/logout")
    public String logout(HttpSession session){
       session.removeAttribute("user");
        return "redirect:/admin";
    }
}
