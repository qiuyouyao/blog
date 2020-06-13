package com.tutu.blog.service.impl;

import com.tutu.blog.bean.Type;
import com.tutu.blog.dao.TypeDao;
import com.tutu.blog.exception.NotAllowDeleteException;
import com.tutu.blog.exception.NotFoundException;
import com.tutu.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tutu 2020/5/1 15:16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TypeServiceImpl implements TypeService {
    @Resource
    TypeDao typeDao;

    @Override
    public Type saveType(Type type) {
        return typeDao.save(type);
    }

    @Override
    public Type getType(Long id) {
        return typeDao.getOne(id);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeDao.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeDao.findAll();
    }

    @Override
    public Type updateType(Type type) {
        Type t = typeDao.getOne(type.getId());
        if (t == null) {
            throw new NotFoundException("不存在该分类");
        }
        BeanUtils.copyProperties(type,t);
        return typeDao.save(t);
    }

    @Override
    public void removeType(Long id) {
        Type type = typeDao.getOne(id);
        if(type.getBlogs().size() > 0){
            throw new NotAllowDeleteException("该分类已经被博客关联,无法删除");
        }
        typeDao.deleteById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.findByName(name);
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "blogs.size"));
        return typeDao.findTop(pageable);
    }
}
