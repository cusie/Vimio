package top.vimio.service;

import org.springframework.scheduling.annotation.Async;
import top.vimio.entity.Logentity.OperationLog;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/14 16:16
 */
public interface OperationLogService {
    List<OperationLog> getOperationLogListByDate(String startDate, String endDate);

    @Async
    void saveOperationLog(OperationLog log);

    void deleteOperationLogById(Long id);

    void deleteAllOperationLog();
}
