package top.vimio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.vimio.constant.RedisKeyConstants;
import top.vimio.entity.Category;
import top.vimio.exception.NotFoundException;
import top.vimio.exception.PersistenceException;
import top.vimio.mapper.CategoryMapper;
import top.vimio.service.CategoryService;
import top.vimio.service.RedisService;
import top.vimio.service.TagService;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/2 14:54
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    RedisService redisService;
    @Override
    public List<Category> getCategoryList() {
        return categoryMapper.getCategoryList();
    }
    @Override
    public List<Category> getCategoryNameList() {
        List<Category> categoryList = categoryMapper.getCategoryNameList();
        return categoryList;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCategory(Category category) {
        if (categoryMapper.saveCategory(category) != 1) {
            throw new PersistenceException("分类添加失败");
        }
        redisService.deleteCacheByKey(RedisKeyConstants.CATEGORY_NAME_LIST);
    }

    @Override
    public Category getCategoryById(Long id) {
        Category category = categoryMapper.getCategoryById(id);
        if (category == null) {
            throw new NotFoundException("分类不存在");
        }
        return category;
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryMapper.getCategoryByName(name);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCategoryById(Long id) {
        if (categoryMapper.deleteCategoryById(id) != 1) {
            throw new PersistenceException("删除分类失败");
        }
        redisService.deleteCacheByKey(RedisKeyConstants.CATEGORY_NAME_LIST);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCategory(Category category) {
        if (categoryMapper.updateCategory(category) != 1) {
            throw new PersistenceException("分类更新失败");
        }
        redisService.deleteCacheByKey(RedisKeyConstants.CATEGORY_NAME_LIST);
        //修改了分类名，可能有首页文章关联了分类，也要更新首页缓存
        redisService.deleteCacheByKey(RedisKeyConstants.HOME_BLOG_INFO_LIST);
    }
}
