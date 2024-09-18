package top.vimio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.vimio.entity.Logentity.LoginLog;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/14 18:12
 */
@Mapper
@Repository
public interface LoginLogMapper {
    List<LoginLog> getLoginLogListByDate(@Param("startDate")String startDate, @Param("endDate")String endDate);

    int saveLoginLog(LoginLog log);

    int deleteLoginLogById(Long id);
}
