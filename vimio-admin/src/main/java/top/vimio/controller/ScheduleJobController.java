package top.vimio.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.vimio.annotation.OperationLogger;
import top.vimio.entity.Logentity.ScheduleJobLog;
import top.vimio.entity.ScheduleJob;
import top.vimio.service.ScheduleJobService;
import top.vimio.utils.common.ResponseResult;
import top.vimio.utils.common.ValidatorUtils;

import java.util.Date;

/**
 * @Description: 定时任务动态管理
 * @Author: Vimio
 * @Date: 2024/9/15 16:33
 */
@RestController
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 分页查询定时任务列表
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return
     */
    @GetMapping("/jobs")
    public ResponseResult jobs(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<ScheduleJob> pageInfo = new PageInfo<>(scheduleJobService.getJobList());
        return ResponseResult.ok("请求成功", pageInfo);
    }

    /**
     * 新建定时任务
     *
     * @param scheduleJob
     * @return
     */
    @OperationLogger("新建定时任务")
    @PostMapping("/job")
    public ResponseResult saveJob(@RequestBody ScheduleJob scheduleJob) {
        scheduleJob.setStatus(false);
        scheduleJob.setCreateTime(new Date());
        ValidatorUtils.validateEntity(scheduleJob);
        scheduleJobService.saveJob(scheduleJob);
        return ResponseResult.ok("添加成功");
    }

    /**
     * 修改定时任务
     *
     * @param scheduleJob
     * @return
     */
    @OperationLogger("修改定时任务")
    @PutMapping("/job")
    public ResponseResult updateJob(@RequestBody ScheduleJob scheduleJob) {
        scheduleJob.setStatus(false);
        ValidatorUtils.validateEntity(scheduleJob);
        scheduleJobService.updateJob(scheduleJob);
        return ResponseResult.ok("修改成功");
    }

    /**
     * 删除定时任务
     *
     * @param jobId 任务id
     * @return
     */
    @OperationLogger("删除定时任务")
    @DeleteMapping("/job")
    public ResponseResult deleteJob(@RequestParam Long jobId) {
        scheduleJobService.deleteJobById(jobId);
        return ResponseResult.ok("删除成功");
    }

    /**
     * 立即执行任务
     *
     * @param jobId 任务id
     * @return
     */
    @OperationLogger("立即执行定时任务")
    @PostMapping("/job/run")
    public ResponseResult runJob(@RequestParam Long jobId) {
        scheduleJobService.runJobById(jobId);
        return ResponseResult.ok("提交执行");
    }

    /**
     * 更新任务状态：暂停或恢复
     *
     * @param jobId  任务id
     * @param status 状态
     * @return
     */
    @OperationLogger("更新任务状态")
    @PutMapping("/job/status")
    public ResponseResult updateJobStatus(@RequestParam Long jobId, @RequestParam Boolean status) {
        scheduleJobService.updateJobStatusById(jobId, status);
        return ResponseResult.ok("更新成功");
    }

    /**
     * 分页查询定时任务日志列表
     *
     * @param date     按执行时间查询
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return
     */
    @GetMapping("/job/logs")
    public ResponseResult logs(@RequestParam(defaultValue = "") String[] date,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        String startDate = null;
        String endDate = null;
        if (date.length == 2) {
            startDate = date[0];
            endDate = date[1];
        }
        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        PageInfo<ScheduleJobLog> pageInfo = new PageInfo<>(scheduleJobService.getJobLogListByDate(startDate, endDate));
        return ResponseResult.ok("请求成功", pageInfo);
    }

    /**
     * 按id删除任务日志
     *
     * @param logId 日志id
     * @return
     */
    @DeleteMapping("/job/log")
    public ResponseResult delete(@RequestParam Long logId) {
        scheduleJobService.deleteJobLogByLogId(logId);
        return ResponseResult.ok("删除成功");
	}
}
