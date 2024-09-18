package top.vimio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.vimio.annotation.OperationLogger;
import top.vimio.utils.common.ResponseResult;
import top.vimio.service.AboutService;

import java.util.Map;

/**
 * @Description: 关于我页面后台管理
 * @Author: Vimio
 * @Date: 2024/9/10 9:49
 */
@RestController
public class AboutAdminController {
    @Autowired
    AboutService aboutService;

    /**
     * 获取关于我页面配置
     *
     * @return
     */
    @GetMapping("/about")
    public ResponseResult about() {
        return ResponseResult.ok("请求成功", aboutService.getAboutSetting());
    }

    /**
     * 修改关于我页面
     *
     * @param map
     * @return
     */
    @OperationLogger("修改关于我页面")
    @PutMapping("/about")
    public ResponseResult updateAbout(@RequestBody Map<String, String> map) {
        aboutService.updateAbout(map);
        return ResponseResult.ok("修改成功");
    }
}
