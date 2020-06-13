package com.tutu.blog.controller;

import com.tutu.blog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

/**
 * @author tutu 2020/5/3 23:43
 */
@Controller
public class ArchiveShowController {

    @Resource
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.countBlog());
        return "/archives";
    }
}
