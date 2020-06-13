package com.tutu.blog.service.impl;

import com.tutu.blog.bean.Comment;
import com.tutu.blog.dao.CommentDao;
import com.tutu.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tutu 2020/5/3 18:30
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentDao commentDao;

    private List<Comment> tempReplays = new ArrayList<>();

    @Override
    public List<Comment> listCommentByBlogId(Long id) {
        List<Comment> comments = commentDao.findByBlogIdAndParentCommentNull(id, Sort.by(Sort.Direction.ASC, "createTime"));
        return eachComment(comments);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentDao.getOne(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentDao.save(comment);
    }

    /**
     * 循环每个顶级的评论节点
     *
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentList = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentList.add(c);
        }
        combineChildren(commentList);
        return commentList;
    }

    /**
     * root根节点,blog不为空的对象集合
     *
     * @param comments
     */
    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            for (Comment replyComment : comment.getReplyComments()) {
                recursively(replyComment);
            }
            comment.setReplyComments(tempReplays);
            tempReplays = new ArrayList<>();
        }
    }

    /**
     * 递归迭代
     *
     * @param comment
     */
    private void recursively(Comment comment) {
        tempReplays.add(comment);
        if (comment.getReplyComments().size() > 0) {
            for (Comment replyComment : comment.getReplyComments()) {
                tempReplays.add(replyComment);
                if (replyComment.getReplyComments().size() > 0) {
                    recursively(replyComment);
                }
            }
        }
    }
}
