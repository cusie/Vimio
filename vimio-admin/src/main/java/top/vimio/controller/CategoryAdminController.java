package top.vimio.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.vimio.annotation.OperationLogger;
import top.vimio.entity.Category;
import top.vimio.utils.common.ResponseResult;
import top.vimio.service.BlogService;
import top.vimio.service.CategoryService;
import top.vimio.utils.StringUtils;

/**
 * @Description: 博客分类后台管理
 * @Author: Vimio
 * @Date: 2024/9/12 10:26
 */
@RestController
public class CategoryAdminController {
    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;

    /**
     * 获取博客分类列表
     *
     * @param pageNum  页码
     * @param pageSize 每页个数
     * @return
     */
    @GetMapping("/categories")
    public ResponseResult categories(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String orderBy = "id desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        PageInfo<Category> pageInfo = new PageInfo<>(categoryService.getCategoryList());
        return ResponseResult.ok("请求成功", pageInfo);
    }

    /**
     * 添加新分类
     *
     * @param category 分类实体
     * @return
     */
    @OperationLogger("添加分类")
    @PostMapping("/category")
    public ResponseResult saveCategory(@RequestBody Category category) {
        return getResult(category, "save");
    }

    /**
     * 修改分类名称
     *
     * @param category 分类实体
     * @return
     */
    @OperationLogger("修改分类")
    @PutMapping("/category")
    public ResponseResult updateCategory(@RequestBody Category category) {
        return getResult(category, "update");
    }

    /**
     * 执行分类添加或更新操作：校验参数是否合法，分类是否已存在
     *
     * @param category 分类实体
     * @param type     添加或更新
     * @return
     */
    private ResponseResult getResult(Category category, String type) {
        if (StringUtils.isEmpty(category.getName())) {
            return ResponseResult.error("分类名称不能为空");
        }
        //查询分类是否已存在
        Category category1 = categoryService.getCategoryByName(category.getName());
        //如果 category1.getId().equals(category.getId()) == true 就是更新分类
        if (category1 != null && !category1.getId().equals(category.getId())) {
            return ResponseResult.error("该分类已存在");
        }
        if ("save".equals(type)) {
            categoryService.saveCategory(category);
            return ResponseResult.ok("分类添加成功");
        } else {
            categoryService.updateCategory(category);
            return ResponseResult.ok("分类更新成功");
        }
    }

    /**
     * 按id删除分类
     *
     * @param id 分类id
     * @return
     */
    @OperationLogger("删除分类")
    @DeleteMapping("/category")
    public ResponseResult delete(@RequestParam Long id) {
        //删除存在博客关联的分类后，该博客的查询会出异常
        int num = blogService.countBlogByCategoryId(id);
        if (num != 0) {
            return ResponseResult.error("已有博客与此分类关联，不可删除");
        }
        categoryService.deleteCategoryById(id);
        return ResponseResult.ok("删除成功");
    }
}
