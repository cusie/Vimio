package top.vimio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.vimio.entity.WebSave;
import top.vimio.model.blogdto.WebSaveDto;
import top.vimio.model.blogvo.vo.WebSaveVo;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/6 20:46
 */

@Mapper
@Repository
public interface WebSaveMapper {

    List<WebSave> getWebSaveList();

    List<WebSave> getWebSaveListByType(@Param("type") Long type);
    List<WebSaveVo> getWebSaveVoList(Integer type);

    int updateViewsByWebname(String webname);

    int saveWebsite(WebSave webSave);

    int updateWebsite(WebSaveDto webSave);

    int deleteWebsite(Long id);



}
