package top.vimio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vimio.annotation.VisitLogger;
import top.vimio.enums.VisitBehavior;
import top.vimio.utils.common.ResponseResult;
import top.vimio.service.BlogService;

import java.util.Map;

/**
 * @Description: 归档控制层
 * @Author: Vimio
 * @Date: 2024/8/31 10:59
 */
@RestController
public class ArchivesController {
    @Autowired
    BlogService blogService;

    /**
     * 按年月分组归档公开博客 统计公开博客总数
     *
     * @return
     */
    @VisitLogger(VisitBehavior.ARCHIVE)
    @GetMapping("/archives")
    public ResponseResult archives() {
        Map<String, Object> archiveBlogMap = blogService.getArchiveBlogAndCountByIsPublished();
        return ResponseResult.ok("请求成功", archiveBlogMap);
    }

}
