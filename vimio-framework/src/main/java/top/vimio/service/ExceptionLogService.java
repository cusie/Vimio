package top.vimio.service;

import org.springframework.scheduling.annotation.Async;
import top.vimio.entity.Logentity.ExceptionLog;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/14 16:17
 */
public interface ExceptionLogService {
    List<ExceptionLog> getExceptionLogListByDate(String startDate, String endDate);

    @Async
    void saveExceptionLog(ExceptionLog log);

    void deleteExceptionLogById(Long id);
}
