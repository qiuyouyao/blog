package com.tutu.blog.dao;

import com.tutu.blog.bean.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author tutu 2020/5/3 18:31
 */
public interface CommentDao extends JpaRepository<Comment,Long> {

    /**
     * findByBlogId
     * @param id
     * @param sort
     * @return
     */
    List<Comment> findByBlogIdAndParentCommentNull(Long id, Sort sort);
}
