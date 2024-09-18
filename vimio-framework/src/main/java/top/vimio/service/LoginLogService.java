package top.vimio.service;

import org.springframework.scheduling.annotation.Async;
import top.vimio.entity.Logentity.LoginLog;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/14 18:10
 */
public interface LoginLogService {
    List<LoginLog> getLoginLogListByDate(String startDate, String endDate);

    @Async
    void saveLoginLog(LoginLog log);

    void deleteLoginLogById(Long id);
}
