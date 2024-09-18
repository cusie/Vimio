package top.vimio.service;

import top.vimio.entity.Logentity.ScheduleJobLog;
import top.vimio.entity.ScheduleJob;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/15 16:34
 */
public interface ScheduleJobService {
    List<ScheduleJob> getJobList();

    void saveJob(ScheduleJob scheduleJob);

    void updateJob(ScheduleJob scheduleJob);

    void deleteJobById(Long jobId);

    void runJobById(Long jobId);

    void updateJobStatusById(Long jobId, Boolean status);

    List<ScheduleJobLog> getJobLogListByDate(String startDate, String endDate);

    void saveJobLog(ScheduleJobLog log);

    void deleteJobLogByLogId(Long logId);
}
