package com.tutu.blog.service.impl;

import com.tutu.blog.bean.Tag;
import com.tutu.blog.dao.TagDao;
import com.tutu.blog.exception.NotAllowDeleteException;
import com.tutu.blog.exception.NotFoundException;
import com.tutu.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tutu 2020/5/1 18:02
 */
@Service
public class TagServiceImpl implements TagService {

    @Resource
    TagDao tagDao;

    @Override
    public Tag saveTag(Tag tag) {
        return tagDao.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagDao.getOne(id);
    }

    @Override
    public Page<Tag> listType(Pageable pageable) {
        return tagDao.findAll(pageable);
    }

    @Override
    public Tag updateTag(Tag tag) {
        Tag t = tagDao.getOne(tag.getId());
        if(t == null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag,t);
        return tagDao.save(t);
    }

    @Override
    public void removeTag(Long id) {
        Tag tag = tagDao.getOne(id);
        if(tag.getBlogs().size() > 0){
            throw new NotAllowDeleteException("该标签：" + tag.getName() + " 已经被博客引用,无法删除");
        }
        tagDao.deleteById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.findByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagDao.findAllById(convertToList(ids));
    }

    private List<Long> convertToList(String string){
        List<Long> values = new ArrayList<>();
        if(StringUtils.isEmpty(string)){
            return values;
        }
        String[] strings = string.split(",");
        for (String str : strings) {
            values.add(Long.valueOf(str));
        }
        return values;
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "blogs.size"));
        return tagDao.findTop(pageable);
    }
}
