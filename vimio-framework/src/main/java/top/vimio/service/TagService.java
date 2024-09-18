package top.vimio.service;

import top.vimio.entity.Tag;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/2 20:00
 */
public interface TagService {
    /**
     *
     * @Description: 前台显示业务
     * */
    List<Tag> getTagListNotId();

    /**
     *
     * @Description: 公共业务业务
     * */
    List<Tag> getTagList();
    /**
     *
     * @Description: 后台管理业务
     * */
    void saveTag(Tag tag);

    Tag getTagByName(String name);

    void deleteTagById(Long id);

    void updateTag(Tag tag);

    Tag getTagById(Long id);


}
