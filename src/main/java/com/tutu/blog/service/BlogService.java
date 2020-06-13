package com.tutu.blog.service;

import com.tutu.blog.bean.Blog;
import com.tutu.blog.vo.BlogQueryVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author tutu 2020/5/1 18:52
 */
public interface BlogService {

    Blog getBlog(Long id);

    Blog getBlogDetail(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQueryVO vo);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Blog blog);

    void removeBlog(Long id);

    Page<Blog> listBlog(Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    Page<Blog> listBlog(String query, Pageable pageable);

    Page<Blog> listBlog(Long tagId, Pageable pageable);

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();
}
