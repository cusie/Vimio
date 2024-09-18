package top.vimio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.vimio.entity.Category;

import java.util.List;

/**
 * @Description: 博客分类持久层接口
 * @Author: Vimio
 * @Date: 2024/9/2 14:46
 */
@Mapper
@Repository
public interface CategoryMapper {
    List<Category> getCategoryList();

    List<Category> getCategoryNameList();


    int saveCategory(Category category);

    Category getCategoryById(Long id);

    Category getCategoryByName(String name);

    int deleteCategoryById(Long id);

    int updateCategory(Category category);
}
