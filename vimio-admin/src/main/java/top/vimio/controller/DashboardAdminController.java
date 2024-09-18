package top.vimio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vimio.constant.RedisKeyConstants;
import top.vimio.entity.Logentity.CityVisitor;
import top.vimio.utils.common.ResponseResult;
import top.vimio.service.DashboardService;
import top.vimio.service.RedisService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 后台管理仪表盘
 * @Author: Vimio
 * @Date: 2024/9/12 8:29
 */
@RestController
public class DashboardAdminController {
    @Autowired
    DashboardService dashboardService;
    @Autowired
    RedisService redisService;

    @GetMapping("/dashboard")
    public ResponseResult dashboard() {
        int todayPV = dashboardService.countVisitLogByToday();
        int todayUV = redisService.countBySet(RedisKeyConstants.IDENTIFICATION_SET);
        int blogCount = dashboardService.getBlogCount();
        int commentCount = dashboardService.getCommentCount();
        Map<String, List> categoryBlogCountMap = dashboardService.getCategoryBlogCountMap();
        Map<String, List> tagBlogCountMap = dashboardService.getTagBlogCountMap();
        Map<String, List> visitRecordMap = dashboardService.getVisitRecordMap();
        List<CityVisitor> cityVisitorList = dashboardService.getCityVisitorList();

        Map<String, Object> map = new HashMap<>(16);
        map.put("pv", todayPV);
        map.put("uv", todayUV);
        map.put("blogCount", blogCount);
        map.put("commentCount", commentCount);
        map.put("category", categoryBlogCountMap);
        map.put("tag", tagBlogCountMap);
        map.put("visitRecord", visitRecordMap);
        map.put("cityVisitor", cityVisitorList);
        return ResponseResult.ok("获取成功", map);
    }
}
