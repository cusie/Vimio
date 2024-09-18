package top.vimio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.vimio.annotation.VisitLogger;
import top.vimio.enums.VisitBehavior;
import top.vimio.utils.common.ResponseResult;
import top.vimio.model.blogvo.vo.WebSaveVo;
import top.vimio.service.WebSaveService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 网站链
 * @Author: Vimio
 * @Date: 2024/9/7 8:39
 */
@RestController
public class WebSaveController {
    @Autowired
    WebSaveService webSaveService;

    @VisitLogger(VisitBehavior.WEBSITE)
    @GetMapping("/websites")
    public ResponseResult websites() {
        List<WebSaveVo> essential = webSaveService.getWebSaveVoList(1);
        List<WebSaveVo> scienceweb = webSaveService.getWebSaveVoList(2);
        List<WebSaveVo> frequentlyuse = webSaveService.getWebSaveVoList(3);
        List<WebSaveVo> other = webSaveService.getWebSaveVoList(4);
        Map<String, Object> map = new HashMap<>(4);
        map.put("essential", essential);
        map.put("scienceweb", scienceweb);
        map.put("frequentlyuse", frequentlyuse);
        map.put("other", other);
        return ResponseResult.ok("获取成功", map);

    }
    @VisitLogger(VisitBehavior.CHECK_WEBSITE)
    @PostMapping("/webview")
    public ResponseResult addViews(@RequestParam String webname) {
        webSaveService.updateViewsByWebname(webname);
        return ResponseResult.ok("更新成功");
    }
}
