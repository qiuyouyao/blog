package com.tutu.blog.controller.admin;

import com.tutu.blog.bean.Blog;
import com.tutu.blog.bean.Type;
import com.tutu.blog.bean.User;
import com.tutu.blog.service.BlogService;
import com.tutu.blog.service.TagService;
import com.tutu.blog.service.TypeService;
import com.tutu.blog.vo.BlogQueryVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author tutu 2020/4/30 23:29
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    public static final String INPUT = "/admin/blogs-input";
    public static final String LIST = "/admin/blogs";
    public static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Resource
    BlogService blogService;
    @Resource
    TypeService typeService;
    @Resource
    TagService tagService;

    @GetMapping("/blogs")
    public String blog(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQueryVO vo, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, vo));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQueryVO vo, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, vo));
        return "/admin/blogs::blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        return INPUT;
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return INPUT;
    }

    @PostMapping("/blogs")
    public String saveBlog(Blog blog,RedirectAttributes attributes,HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog saveBlog = blogService.saveBlog(blog);
        if(saveBlog == null){
            attributes.addFlashAttribute("message", "新增失败");
        }else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return REDIRECT_LIST;
    }

    @PutMapping("/blogs")
    public String updateBlog(Blog blog,RedirectAttributes attributes){
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog saveBlog = blogService.updateBlog(blog);
        if(saveBlog == null){
            attributes.addFlashAttribute("message", "更新失败");
        }else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return REDIRECT_LIST;
    }

    @DeleteMapping("/blogs/{id}")
    public String deleteBlog(@PathVariable Long id){
        blogService.removeBlog(id);
        return REDIRECT_LIST;
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        Blog blog = new Blog();
        blog.setType(new Type());
        model.addAttribute("blog", blog);
    }
}
