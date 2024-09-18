package top.vimio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vimio.utils.common.ResponseResult;
import top.vimio.service.AboutService;

/**
 * @Description: 关于我的信息控制
 * @Author: Vimio
 * @Date: 2024/8/27 21:13
 */
@RestController
public class AboutController {
    @Autowired
    AboutService aboutService;

    /**
     * 获取关于我页面信息
     * @return
     */
    @GetMapping("/about")
    public ResponseResult about() {
        return ResponseResult.ok("获取成功", aboutService.getAboutInfo());
    }
}