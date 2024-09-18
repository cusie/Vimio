package top.vimio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.vimio.entity.About;

import java.util.List;
/**
 * @Description: 关于我的信息简介的持久层接口
 * @Author: Vimio
 * @Date: 2024/8/27 21:03
 */
@Mapper
@Repository
public interface AboutMapper {

    List<About> getList();

    String getAboutCommentEnabled();

    int updateAbout(@Param("nameEn") String nameEn,@Param("value") String value);


}
