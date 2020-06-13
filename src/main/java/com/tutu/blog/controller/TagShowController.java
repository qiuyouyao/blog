package com.tutu.blog.controller;

import com.tutu.blog.bean.Tag;
import com.tutu.blog.service.BlogService;
import com.tutu.blog.service.TagService;
import com.tutu.blog.vo.BlogQueryVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tutu 2020/5/3 23:09
 */
@Controller
public class TagShowController {
    @Resource
    private TagService tagService;
    @Resource
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PathVariable Long id, @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
        List<Tag> tags = tagService.listTagTop(100);
        if(id == -1){
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id,pageable));
        model.addAttribute("activeTypeId", id);
        return "/tags";
    }
}
