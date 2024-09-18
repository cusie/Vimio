package top.vimio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.vimio.entity.Logentity.CityVisitor;
import top.vimio.mapper.CityVisitorMapper;
import top.vimio.service.CityVisitorService;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/15 16:49
 */
@Service
public class CityVisitorServiceImpl implements CityVisitorService {
    @Autowired
    CityVisitorMapper cityVisitorMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCityVisitor(CityVisitor cityVisitor) {
        cityVisitorMapper.saveCityVisitor(cityVisitor);
	}
}
