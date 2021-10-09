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

使用JPA框架 JPA中自己分装好了一些方法可直接定义使用  @Query也是JPA中的。

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
 4.实现嵌套循环
 
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;


    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        /* 建立子父的关系*/
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.findById(parentCommentId).get());
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    @Override
   /* 评论列表展示功能*/
    public List<Comment> listCommentByBlogId(Long blogId) {

        Sort sort =Sort.by("createTime");
       /* 数据处理*/
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        /*评论形成父子级
        * 顶层root parent-id为空
        * */
        return eachComment(comments);
    }

    /**
     * 循环每个顶级的评论节点
     * 对父级内容进行copy 防止父级内
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments
     *root根节点下的子集，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            //getReplyComments 子集
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     *                一层一层的内容都获取到
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
