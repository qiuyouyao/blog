package com.tutu.blog.service;

import com.tutu.blog.bean.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author tutu 2020/5/1 15:12
 */
public interface TypeService {

    /**
     * 新增
     * @param type
     * @return
     */
    Type saveType(Type type);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Type getType(Long id);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<Type> listType(Pageable pageable);

    /**
     * listType
     * @return
     */
    List<Type> listType();

    /**
     * 更新
     * @param type
     * @return
     */
    Type updateType(Type type);

    /**
     * 删除
     * @param id
     */
    void removeType(Long id);

    /**
     * 查询
     * @param name
     * @return
     */
    Type getTypeByName(String name);

    List<Type> listTypeTop(Integer size);
}
