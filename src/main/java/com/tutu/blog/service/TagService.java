package com.tutu.blog.service;

import com.tutu.blog.bean.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author tutu 2020/5/1 17:59
 */
public interface TagService {
    /**
     * saveTag
     * @param tag
     * @return
     */
    Tag saveTag(Tag tag);

    /**
     * getTag
     * @param id
     * @return
     */
    Tag getTag(Long id);

    /**
     * listType
     * @param pageable
     * @return
     */
    Page<Tag> listType(Pageable pageable);

    /**
     * 更新
     * @param tag
     * @return
     */
    Tag updateTag(Tag tag);

    /**
     * 删除
     * @param id
     */
    void removeTag(Long id);

    /**
     * getTagByName
     * @param name
     * @return
     */
    Tag getTagByName(String name);

    /**
     * listTag
     * @return
     */
    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);
}
