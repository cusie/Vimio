package top.vimio.service;

import top.vimio.entity.Category;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/2 14:54
 */
public interface CategoryService {

    /**
     *
     * @Description: 前台显示业务
     * */
    List<Category> getCategoryNameList();

    /**
     *
     * @Description: 公共业务业务
     * */
    List<Category> getCategoryList();

    /**
     *
     * @Description: 后台管理业务
     * */
    void saveCategory(Category category);

    Category getCategoryByName(String name);

    void deleteCategoryById(Long id);

    void updateCategory(Category category);



    Category getCategoryById(Long id);
}
