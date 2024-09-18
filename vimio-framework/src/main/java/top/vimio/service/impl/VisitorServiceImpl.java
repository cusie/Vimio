package top.vimio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.vimio.constant.RedisKeyConstants;
import top.vimio.entity.Logentity.Visitor;
import top.vimio.exception.PersistenceException;
import top.vimio.mapper.VisitorMapper;
import top.vimio.model.blogdto.UserAgentDto;
import top.vimio.model.blogdto.VisitLogUuidTime;
import top.vimio.service.RedisService;
import top.vimio.service.VisitorService;
import top.vimio.utils.IpAddressUtils;
import top.vimio.utils.UserAgentUtils;

import java.util.List;

/**
*
*@Description:
*@Author: Vimio
*@Date: 2024/9/14 16:24
*
*/
@Service
public class VisitorServiceImpl implements VisitorService {
    @Autowired
    VisitorMapper visitorMapper;
    @Autowired
    UserAgentUtils userAgentUtils;
    @Autowired
    RedisService redisService;

    @Override
    public List<Visitor> getVisitorListByDate(String startDate, String endDate) {
        return visitorMapper.getVisitorListByDate(startDate, endDate);
    }

    @Override
    public List<String> getNewVisitorIpSourceByYesterday() {
        return visitorMapper.getNewVisitorIpSourceByYesterday();
    }

    @Override
    public boolean hasUUID(String uuid) {
        return visitorMapper.hasUUID(uuid) != 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveVisitor(Visitor visitor) {
        String ipSource = IpAddressUtils.getCityInfo(visitor.getIp());
        UserAgentDto userAgentDTO = userAgentUtils.parseOsAndBrowser(visitor.getUserAgent());
        visitor.setIpSource(ipSource);
        visitor.setOs(userAgentDTO.getOs());
        visitor.setBrowser(userAgentDTO.getBrowser());
        if (visitorMapper.saveVisitor(visitor) != 1) {
            throw new PersistenceException("访客添加失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePVAndLastTimeByUUID(VisitLogUuidTime dto) {
        visitorMapper.updatePVAndLastTimeByUUID(dto);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteVisitor(Long id, String uuid) {
        //删除Redis中该访客的uuid
        redisService.deleteValueBySet(RedisKeyConstants.IDENTIFICATION_SET, uuid);
        if (visitorMapper.deleteVisitorById(id) != 1) {
            throw new PersistenceException("删除访客失败");
        }
    }

    @Override
    public void deleteAllVisitorLog() {
        visitorMapper.deleteAllVisitorLog();
    }
}
