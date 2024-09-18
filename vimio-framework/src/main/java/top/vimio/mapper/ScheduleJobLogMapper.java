package top.vimio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.vimio.entity.Logentity.ScheduleJobLog;

import java.util.List;

/**
 * @Description: 定时任务日志持久层接口
 * @Author: Vimio
 * @Date: 2024/9/15 16:43
 */
@Mapper
@Repository
public interface ScheduleJobLogMapper {
    List<ScheduleJobLog> getJobLogListByDate(@Param("startDate")String startDate, @Param("endDate")String endDate);

    int saveJobLog(ScheduleJobLog jobLog);

    int deleteJobLogByLogId(Long logId);

    void deleteAllJobLogByLog();
}
