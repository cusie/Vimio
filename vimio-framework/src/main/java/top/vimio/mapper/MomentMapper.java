package top.vimio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.vimio.entity.Moment;

import java.util.List;

/**
 * @Description: 博客动态持久层接口
 * @Author: Vimio
 * @Date: 2024/8/30 15:54
 */
@Mapper
@Repository
public interface MomentMapper {
    List<Moment> getMomentList();

    int addLikeByMomentId(Long momentId);

    int updateMomentPublishedById(@Param("momentId")Long momentId, @Param("published")Boolean published);

    Moment getMomentById(Long id);

    int deleteMomentById(Long id);

    int saveMoment(Moment moment);

    int updateMoment(Moment moment);

}
