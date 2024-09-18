package top.vimio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.vimio.entity.Logentity.ExceptionLog;

import java.util.List;

/**
 * @Description: 异常日志持久层接口
 * @Author: Vimio
 * @Date: 2024/9/14 17:04
 */
@Mapper
@Repository
public interface ExceptionLogMapper {
    List<ExceptionLog> getExceptionLogListByDate(@Param("startDate")String startDate, @Param("endDate")String endDate);

    int saveExceptionLog(ExceptionLog log);

    int deleteExceptionLogById(Long id);
}
