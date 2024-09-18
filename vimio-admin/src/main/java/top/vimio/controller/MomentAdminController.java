package top.vimio.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.vimio.annotation.OperationLogger;
import top.vimio.entity.Moment;
import top.vimio.utils.common.ResponseResult;
import top.vimio.service.MomentServic;

import java.util.Date;

/**
 * @Description: 博客动态后台管理
 * @Author: Vimio
 * @Date: 2024/9/11 11:22
 */
@RestController
public class MomentAdminController {

    @Autowired
    MomentServic momentService;

    /**
     * 分页获取动态列表
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return
     */
    @GetMapping("/moments")
    public ResponseResult moments(@RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        PageInfo<Moment> pageInfo = new PageInfo<>(momentService.getMomentList());
        return ResponseResult.ok("请求成功", pageInfo);
    }
    /**
     * 根据id查询动态
     *
     * @param id 动态id
     * @return
     */
    @GetMapping("/moment")
    public ResponseResult moment(@RequestParam Long id) {
        return ResponseResult.ok("获取成功", momentService.getMomentById(id));
    }


    /**
     * 更新动态公开状态
     *
     * @param id        动态id
     * @param published 是否公开
     * @return
     */
    @OperationLogger("更新动态公开状态")
    @PutMapping("/moment/published")
    public ResponseResult updatePublished(@RequestParam Long id, @RequestParam Boolean published) {
        momentService.updateMomentPublishedById(id, published);
        return ResponseResult.ok("操作成功");
    }

    /**
     * 删除动态
     *
     * @param id 动态id
     * @return
     */
    @OperationLogger("删除动态")
    @DeleteMapping("/moment")
    public ResponseResult deleteMoment(@RequestParam Long id) {
        momentService.deleteMomentById(id);
        return ResponseResult.ok("删除成功");
    }

    /**
     * 发布动态
     *
     * @param moment 动态实体
     * @return
     */
    @OperationLogger("发布动态")
    @PostMapping("/moment")
    public ResponseResult saveMoment(@RequestBody Moment moment) {
        if (moment.getCreateTime() == null) {
            moment.setCreateTime(new Date());
        }
        momentService.saveMoment(moment);
        return ResponseResult.ok("添加成功");
    }

    /**
     * 更新动态
     *
     * @param moment 动态实体
     * @return
     */
    @OperationLogger("更新动态")
    @PutMapping("/moment")
    public ResponseResult updateMoment(@RequestBody Moment moment) {
        if (moment.getCreateTime() == null) {
            moment.setCreateTime(new Date());
        }
        momentService.updateMoment(moment);
        return ResponseResult.ok("修改成功");
    }
}
