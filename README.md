# springboot博客小项目
 
主要使用框架技术：
前端：semantic UI框架
后台管理: Jpa技术 springboot框架

项目名称：基于springboot的博客网站

项目描述：通过springboot技术搭建一个博客网站。
前台展示：前台通过 semantic ui框架的来搭建前端页面的，前端功能主要是首页博客数据的展示，例如博客标签分类，博客详情和博客页面的分页操作；还包括博客分类，博客标签和归档（也就是对博客发的时间和内容进行分类）
 后台维护： 后台主要是管理员登录后对博客进行增删改也就是博客管理，还有分类管理和标签管理
涉及的知识点：springboot, Maven , semantic ui

前端还使用了小插件集成，使其页面更加美观（MakeDown编辑器，内容排版typo.css,动画animate·.css,代码高亮prism,平滑滚动jqury.scrollTo,目录生成Tobot,二维码生成qrcode.js）

日志处理通过切面实现(Aspect文件夹）
页面处理通过thymeleaf布局实现 （公共片段通过fragment实现）

使用JPA技术 JPA中自己分装好了一些方法可直接定义使用  @Query也是JPA中的。

extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog>
 @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);
 
 
 handler自定义异常类
 
 intercptor登录拦截器，用户未登录不可访问其他界面
 
 评论功能简介：
 父子级循环嵌套实现：
 1.是在一个具体博客下进行评论
 2，html中进行form表单验证  定一个在评论处fragment来动态刷新（局部）
 3.发布功能

