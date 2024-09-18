package top.vimio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.vimio.entity.Logentity.VisitRecord;
import top.vimio.mapper.VisitRecordMapper;
import top.vimio.service.VisitRecordService;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/15 16:49
 */
@Service
public class VisitRecordServiceImpl implements VisitRecordService {
    @Autowired
    VisitRecordMapper visitRecordMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveVisitRecord(VisitRecord visitRecord) {
        visitRecordMapper.saveVisitRecord(visitRecord);
	}
}
