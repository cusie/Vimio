package top.vimio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.vimio.entity.Logentity.Visitor;
import top.vimio.model.blogdto.VisitLogUuidTime;

import java.util.List;

/**
 * @Description: 访客统计持久层接口
 * @Author: Vimio
 * @Date: 2024/9/14 16:59
 */
@Mapper
@Repository
public interface VisitorMapper {
    List<Visitor> getVisitorListByDate(@Param("startDate")String startDate, @Param("endDate")String endDate);

    List<String> getNewVisitorIpSourceByYesterday();

    int hasUUID(String uuid);

    int saveVisitor(Visitor visitor);

    int updatePVAndLastTimeByUUID(VisitLogUuidTime dto);

    int deleteVisitorById(Long id);
    void deleteAllVisitorLog();
}
