package com.tutu.blog.dao;

import com.tutu.blog.bean.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author tutu 2020/5/1 17:58
 */
public interface TagDao extends JpaRepository<Tag,Long> {
    /**
     * findByName
     * @param name
     * @return
     */
    Tag findByName(String name);

    @Query("select  t from Tag  t")
    List<Tag> findTop(Pageable pageable);
}
