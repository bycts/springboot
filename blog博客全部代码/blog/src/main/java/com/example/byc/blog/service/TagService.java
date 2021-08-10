package com.example.byc.blog.service;


import com.example.byc.blog.po.Tag;
import com.example.byc.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by limi on 2017/10/16.
 */
public interface TagService {

    Tag saveTag(Tag type);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    Tag updateTag(Long id, Tag type);

    void deleteTag(Long id);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    //前端标签显示
    List<Tag> listTagTop(Integer size);
}
