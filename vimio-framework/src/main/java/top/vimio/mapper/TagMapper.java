package top.vimio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.vimio.entity.Tag;
import top.vimio.model.blogvo.vo.TagBlogCount;

import java.util.List;

/**
 * @Description: 博客标签持久层接口
 * @Author: Vimio
 * @Date: 2024/9/2 19:54
 */
@Mapper
@Repository
public interface TagMapper {
    List<Tag> getTagList();

    List<Tag> getTagListNotId();

    List<Tag> getTagListByBlogId(Long blogId);

    List<TagBlogCount> getTagBlogCount();

    int saveTag(Tag tag);

    Tag getTagByName(String name);

    int deleteTagById(Long id);

    int updateTag(Tag tag);

    Tag getTagById(Long id);
}
