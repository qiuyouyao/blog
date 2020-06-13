package com.tutu.blog.service;

import com.tutu.blog.bean.Comment;

import java.util.List;

/**
 * @author tutu 2020/5/3 18:29
 */
public interface CommentService {

    /**
     * listCommentByBlogId
     * @param id
     * @return
     */
    List<Comment> listCommentByBlogId(Long id);

    /**
     * saveComment
     * @param comment
     * @return
     */
    Comment saveComment(Comment comment);
}
