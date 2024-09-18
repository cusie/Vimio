package top.vimio.service;

import org.springframework.scheduling.annotation.Async;
import top.vimio.entity.Logentity.VisitLog;
import top.vimio.model.blogdto.VisitLogUuidTime;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/14 16:03
 */
public interface VisitLogService {
    List<VisitLog> getVisitLogListByUUIDAndDate(String uuid, String startDate, String endDate);

    List<VisitLogUuidTime> getUUIDAndCreateTimeByYesterday();

    @Async
    void saveVisitLog(VisitLog log);

    void deleteVisitLogById(Long id);

    void deleteAllVisitLog();
}
