package com.tutu.blog.service.impl;

import com.tutu.blog.bean.Blog;
import com.tutu.blog.bean.Type;
import com.tutu.blog.dao.BlogDao;
import com.tutu.blog.exception.NotFoundException;
import com.tutu.blog.service.BlogService;
import com.tutu.blog.util.MarkdownUtils;
import com.tutu.blog.util.MyBeanUtils;
import com.tutu.blog.vo.BlogQueryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author tutu 2020/5/1 18:54
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BlogServiceImpl implements BlogService {

    @Resource
    BlogDao blogDao;

    @Override
    public Blog getBlog(Long id) {
        return blogDao.getOne(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQueryVO vo) {

        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(vo.getTitle())) {
                    predicates.add(builder.like(root.get("title"), "%" + vo.getTitle() + "%"));
                }
                if (vo.getTypeId() != null) {
                    predicates.add(builder.equal(root.<Type>get("type").get("id"), vo.getTypeId()));
                }
                if (vo.isRecommend()) {
                    predicates.add(builder.equal(root.<Boolean>get("recommend"), vo.isRecommend()));
                }
                query.where(predicates.toArray(new Predicate[0]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        return blogDao.save(blog);
    }

    @Override
    public Blog updateBlog(Blog blog) {
        Blog origin = blogDao.getOne(blog.getId());
        if (origin == null) {
            throw new NotFoundException("该资源不存在");
        }
        blog.setUpdateTime(new Date());
        BeanUtils.copyProperties(blog, origin, MyBeanUtils.filterNullPropertyNames(blog));
        return blogDao.save(origin);
    }

    @Override
    public void removeBlog(Long id) {
        blogDao.deleteById(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogDao.findAll(pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC,"updateTime"));
        return blogDao.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        query = "%" + query + "%";
        return blogDao.findByQuery(query,pageable);
    }

    @Override
    public Blog getBlogDetail(Long id) {
        Blog blog = blogDao.getOne(id);
        if(blog == null){
            throw new NotFoundException("博客不存在");
        }
        blog.setViews(blog.getViews()+1);
        blogDao.save(blog);
        Blog retBlog = new Blog();
        BeanUtils.copyProperties(blog,retBlog);
        retBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(retBlog.getContent()));
        return retBlog;
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogDao.findAll(new Specification<Blog>(){
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Join<Object, Object> join = root.join("tags");
                return builder.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogDao.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year,blogDao.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogDao.count();
    }
}