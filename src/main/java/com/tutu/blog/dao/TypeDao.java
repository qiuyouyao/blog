package com.tutu.blog.dao;

import com.tutu.blog.bean.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author tutu 2020/5/1 15:16
 */
public interface TypeDao extends JpaRepository<Type,Long> {
    /**
     * 查询
     * @param name
     * @return
     */
    Type findByName(String name);

    /**
     * findTop
     * @param pageable
     * @return
     */
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
