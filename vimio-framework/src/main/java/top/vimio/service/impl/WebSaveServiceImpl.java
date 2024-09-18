package top.vimio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.vimio.entity.WebSave;
import top.vimio.exception.PersistenceException;
import top.vimio.mapper.WebSaveMapper;
import top.vimio.model.blogdto.WebSaveDto;
import top.vimio.model.blogvo.vo.WebSaveVo;
import top.vimio.service.WebSaveService;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/7 8:30
 */
@Service
public class WebSaveServiceImpl implements WebSaveService {
    @Autowired
    private WebSaveMapper webSaveMapper;
    @Override
    public List<WebSave> getWebSaveList() {
        return webSaveMapper.getWebSaveList();
    }


    @Override
    public List<WebSaveVo> getWebSaveVoList(Integer type) {
        return webSaveMapper.getWebSaveVoList(type);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateViewsByWebname(String webname) {
        if (webSaveMapper.updateViewsByWebname(webname) != 1) {
            throw new PersistenceException("操作失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveWebsite(WebSave webSave) {
        webSave.setViews(0);
        webSave.setCreateTime(new Date());
        if (webSaveMapper.saveWebsite(webSave) != 1) {
            throw new PersistenceException("添加失败");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateWebsite(WebSaveDto webSave) {
        if (webSaveMapper.updateWebsite(webSave) != 1) {
            throw new PersistenceException("修改失败");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteWebsite(Long id) {
        if (webSaveMapper.deleteWebsite(id) != 1) {
            throw new PersistenceException("删除失败");
        }
    }

    @Override
    public List<WebSave> getWebSavesByType(Long type) {
        return webSaveMapper.getWebSaveListByType(type);
    }
}
