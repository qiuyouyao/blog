package com.tutu.blog.controller;

import com.tutu.blog.bean.Comment;
import com.tutu.blog.bean.User;
import com.tutu.blog.service.BlogService;
import com.tutu.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author tutu 2020/5/3 18:24
 */
@Controller
public class CommentController {

    @Resource
    CommentService commentService;
    @Resource
    private BlogService blogService;
    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long id = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(id));
        comment.setAvatar(avatar);
        //管理员评论
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + id;
    }
}
