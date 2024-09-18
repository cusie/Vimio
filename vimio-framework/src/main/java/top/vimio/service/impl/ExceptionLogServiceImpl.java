package top.vimio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.vimio.entity.Logentity.ExceptionLog;
import top.vimio.exception.PersistenceException;
import top.vimio.mapper.ExceptionLogMapper;
import top.vimio.model.blogdto.UserAgentDto;
import top.vimio.service.ExceptionLogService;
import top.vimio.utils.IpAddressUtils;
import top.vimio.utils.UserAgentUtils;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/14 16:26
 */
@Service
public class ExceptionLogServiceImpl implements ExceptionLogService {
    @Autowired
    ExceptionLogMapper exceptionLogMapper;
    @Autowired
    UserAgentUtils userAgentUtils;

    @Override
    public List<ExceptionLog> getExceptionLogListByDate(String startDate, String endDate) {
        return exceptionLogMapper.getExceptionLogListByDate(startDate, endDate);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveExceptionLog(ExceptionLog log) {
        String ipSource = IpAddressUtils.getCityInfo(log.getIp());
        UserAgentDto userAgentDTO = userAgentUtils.parseOsAndBrowser(log.getUserAgent());
        log.setIpSource(ipSource);
        log.setOs(userAgentDTO.getOs());
        log.setBrowser(userAgentDTO.getBrowser());
        if (exceptionLogMapper.saveExceptionLog(log) != 1) {
            throw new PersistenceException("日志添加失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteExceptionLogById(Long id) {
        if (exceptionLogMapper.deleteExceptionLogById(id) != 1) {
            throw new PersistenceException("删除日志失败");
        }
    }
}
