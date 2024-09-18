package top.vimio.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.vimio.mapper.ScheduleJobLogMapper;
import top.vimio.service.*;


/**
 * @Description: 每个月清除部分日志
 * @Author: Naccl
 * @Date: 2020-11-02
 */
@Component
public class MonthlyLogClearingTask {
	@Autowired
	VisitorService visitorService;

	@Autowired
	VisitLogService	visitLogService;

	@Autowired
	ScheduleJobLogMapper scheduleJobLogMapper;

	@Autowired
	OperationLogService operationLogService;


	public void syncMonthlyLogClearingDatabase() {
		visitorService.deleteAllVisitorLog();
		visitLogService.deleteAllVisitLog();
		scheduleJobLogMapper.deleteAllJobLogByLog();
		operationLogService.deleteAllOperationLog();
	}
}
