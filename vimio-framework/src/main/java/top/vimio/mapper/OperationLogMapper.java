package top.vimio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.vimio.entity.Logentity.OperationLog;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/14 17:01
 */
@Mapper
@Repository
public interface OperationLogMapper {
    List<OperationLog> getOperationLogListByDate(@Param("startDate")String startDate, @Param("endDate")String endDate);
    int saveOperationLog(OperationLog log);

    int deleteOperationLogById(Long id);

    void deleteAllOperationLog();
}
