package top.vimio.service;

import top.vimio.entity.WebSave;
import top.vimio.model.blogdto.WebSaveDto;
import top.vimio.model.blogvo.vo.WebSaveVo;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/7 8:30
 */
public interface WebSaveService {

    /**
     *
     * @Description: 前台显示业务
     * */
    List<WebSaveVo> getWebSaveVoList(Integer type);

    void updateViewsByWebname(String webname);

    /**
     *
     * @Description: 公共业务
     * */

    /**
     *
     * @Description: 后台管理业务
     * */

    List<WebSave> getWebSaveList();

    void saveWebsite(WebSave webSave);

    void updateWebsite(WebSaveDto webSave);

    void deleteWebsite(Long id);
    List<WebSave> getWebSavesByType(Long type);
}
