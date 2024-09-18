package top.vimio.service;

import top.vimio.entity.Logentity.CityVisitor;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/12 8:33
 */
public interface DashboardService {
    int countVisitLogByToday();

    int getBlogCount();

    int getCommentCount();

    Map<String, List> getCategoryBlogCountMap();

    Map<String, List> getTagBlogCountMap();

    Map<String, List> getVisitRecordMap();

    List<CityVisitor> getCityVisitorList();

}

