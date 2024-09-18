package top.vimio.service;

import org.springframework.scheduling.annotation.Async;
import top.vimio.entity.Logentity.Visitor;
import top.vimio.model.blogdto.VisitLogUuidTime;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/14 15:55
 */
public interface VisitorService {
    List<Visitor> getVisitorListByDate(String startDate, String endDate);

    List<String> getNewVisitorIpSourceByYesterday();

    boolean hasUUID(String uuid);

    @Async
    void saveVisitor(Visitor visitor);

    void updatePVAndLastTimeByUUID(VisitLogUuidTime dto);

    void deleteVisitor(Long id, String uuid);

    void deleteAllVisitorLog();
}
