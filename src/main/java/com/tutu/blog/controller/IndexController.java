package com.tutu.blog.controller;

import com.tutu.blog.bean.Blog;
import com.tutu.blog.service.BlogService;
import com.tutu.blog.service.CommentService;
import com.tutu.blog.service.TagService;
import com.tutu.blog.service.TypeService;
import com.tutu.blog.vo.BlogQueryVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author tutu 2020/5/2 7:37
 */
@Controller
public class IndexController {

    @Resource
    BlogService blogService;
    @Resource
    TypeService typeService;
    @Resource
    TagService tagService;
    @Resource
    CommentService commentService;

    @GetMapping({"/", "/index", "index.html"})
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        Blog blog = blogService.getBlogDetail(id);
        model.addAttribute("blog", blog);
        model.addAttribute("comments",commentService.listCommentByBlogId(id));
        return "blog";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlog(query,pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "_fragments :: newbloglist";
    }
}
