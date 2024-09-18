package top.vimio.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.vimio.annotation.OperationLogger;
import top.vimio.entity.WebSave;
import top.vimio.utils.common.ResponseResult;
import top.vimio.model.blogdto.WebSaveDto;
import top.vimio.service.WebSaveService;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/13 10:09
 */
@RestController
public class WebSaveAdminnController {
    @Autowired
    WebSaveService webSaveService;
    /**
     * 分页获取网站链列表
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return
     */
    @GetMapping("/websites")
    public ResponseResult friends(@RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        String orderBy = "create_time asc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        PageInfo<WebSave> pageInfo = new PageInfo<>(webSaveService.getWebSaveList());
        return ResponseResult.ok("请求成功", pageInfo);
    }

    /**
     * 添加网站链
     *
     * @param webSave 网站链DTO
     * @return
     */
    @OperationLogger("添加网站链")
    @PostMapping("/website")
    public ResponseResult saveWebsite(@RequestBody WebSave webSave) {
        webSaveService.saveWebsite(webSave);
        return ResponseResult.ok("添加成功");
    }

    /**
     * 更新网站链
     *
     * @param webSave 网站链DTO
     * @return
     */
    @OperationLogger("添加网站链")
    @PutMapping("/website")
    public ResponseResult updateFriend(@RequestBody WebSaveDto webSave) {
        webSaveService.updateWebsite(webSave);
        return ResponseResult.ok("修改成功");
    }

    /**
     * 按id删除网站链
     *
     * @param id
     * @return
     */
    @OperationLogger("删除网站链")
    @DeleteMapping("/website")
    public ResponseResult deleteFriend(@RequestParam Long id) {
        webSaveService.deleteWebsite(id);
        return ResponseResult.ok("删除成功");
    }

    /**
     * 按type查询网站链
     *
     * @param type 网站链类型
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return
     */
    @GetMapping("/website/{type}")
    public ResponseResult getWebSavesByType(@PathVariable Long type,
                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WebSave> webSaves = webSaveService.getWebSavesByType(type);
        PageInfo<WebSave> pageInfo = new PageInfo<>(webSaves);
        return ResponseResult.ok("查询成功", pageInfo);
    }

}
