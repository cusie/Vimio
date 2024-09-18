package top.vimio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.vimio.entity.Logentity.OperationLog;
import top.vimio.exception.PersistenceException;
import top.vimio.mapper.OperationLogMapper;
import top.vimio.model.blogdto.UserAgentDto;
import top.vimio.service.OperationLogService;
import top.vimio.utils.IpAddressUtils;
import top.vimio.utils.UserAgentUtils;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/14 16:25
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {
    @Autowired
    OperationLogMapper operationLogMapper;
    @Autowired
    UserAgentUtils userAgentUtils;

    @Override
    public List<OperationLog> getOperationLogListByDate(String startDate, String endDate) {
        return operationLogMapper.getOperationLogListByDate(startDate, endDate);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOperationLog(OperationLog log) {
        String ipSource = IpAddressUtils.getCityInfo(log.getIp());
        UserAgentDto userAgentDTO = userAgentUtils.parseOsAndBrowser(log.getUserAgent());
        log.setIpSource(ipSource);
        log.setOs(userAgentDTO.getOs());
        log.setBrowser(userAgentDTO.getBrowser());
        if (operationLogMapper.saveOperationLog(log) != 1) {
            throw new PersistenceException("日志添加失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteOperationLogById(Long id) {
        if (operationLogMapper.deleteOperationLogById(id) != 1) {
            throw new PersistenceException("删除日志失败");
        }
    }

    @Override
    public void deleteAllOperationLog() {
        operationLogMapper.deleteAllOperationLog();
    }
}
