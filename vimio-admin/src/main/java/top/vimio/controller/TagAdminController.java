package top.vimio.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.vimio.annotation.OperationLogger;
import top.vimio.entity.Tag;
import top.vimio.utils.common.ResponseResult;
import top.vimio.service.BlogService;
import top.vimio.service.TagService;
import top.vimio.utils.StringUtils;

/**
 * @Description: 博客标签后台管理
 * @Author: Vimio
 * @Date: 2024/9/12 11:11
 */
@RestController
public class TagAdminController {
    @Autowired
    BlogService blogService;
    @Autowired
    TagService tagService;

    /**
     * 获取博客标签列表
     *
     * @param pageNum  页码
     * @param pageSize 每页个数
     * @return
     */
    @GetMapping("/tags")
    public ResponseResult tags(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String orderBy = "id desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        PageInfo<Tag> pageInfo = new PageInfo<>(tagService.getTagList());
        return ResponseResult.ok("请求成功", pageInfo);
    }

    /**
     * 添加新标签
     *
     * @param tag 标签实体
     * @return
     */
    @OperationLogger("添加标签")
    @PostMapping("/tag")
    public ResponseResult saveTag(@RequestBody Tag tag) {
        return getResult(tag, "save");
    }

    /**
     * 修改标签
     *
     * @param tag 标签实体
     * @return
     */
    @OperationLogger("修改标签")
    @PutMapping("/tag")
    public ResponseResult updateTag(@RequestBody Tag tag) {
        return getResult(tag, "update");
    }

    /**
     * 执行标签添加或更新操作：校验参数是否合法，标签是否已存在
     *
     * @param tag  标签实体
     * @param type 添加或更新
     * @return
     */
    private ResponseResult getResult(Tag tag, String type) {
        if (StringUtils.isEmpty(tag.getName())) {
            return ResponseResult.error("参数不能为空");
        }
        //查询标签是否已存在
        Tag tag1 = tagService.getTagByName(tag.getName());
        //如果 tag1.getId().equals(tag.getId()) == true 就是更新标签
        if (tag1 != null && !tag1.getId().equals(tag.getId())) {
            return ResponseResult.error("该标签已存在");
        }
        if ("save".equals(type)) {
            tagService.saveTag(tag);
            return ResponseResult.ok("添加成功");
        } else {
            tagService.updateTag(tag);
            return ResponseResult.ok("更新成功");
        }
    }

    /**
     * 按id删除标签
     *
     * @param id 标签id
     * @return
     */
    @OperationLogger("删除标签")
    @DeleteMapping("/tag")
    public ResponseResult delete(@RequestParam Long id) {
        //删除存在博客关联的标签后，该博客的查询会出异常
        int num = blogService.countBlogByTagId(id);
        if (num != 0) {
            return ResponseResult.error("已有博客与此标签关联，不可删除");
        }
        tagService.deleteTagById(id);
        return ResponseResult.ok("删除成功");
    }
}
