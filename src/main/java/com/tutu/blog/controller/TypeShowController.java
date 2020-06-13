package com.tutu.blog.controller;

import com.tutu.blog.bean.Type;
import com.tutu.blog.service.BlogService;
import com.tutu.blog.service.TypeService;
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
 * @author tutu 2020/5/3 22:49
 */
@Controller
public class TypeShowController {
    @Resource
    private TypeService typeService;
    @Resource
    BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
        List<Type> types = typeService.listTypeTop(100);
        if(id == -1){
            id = types.get(0).getId();
        }
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(pageable,new BlogQueryVO(id)));
        model.addAttribute("activeTypeId", id);
        return "/types";
    }
}
