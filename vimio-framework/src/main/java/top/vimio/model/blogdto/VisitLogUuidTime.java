package top.vimio.model.blogdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description: 访客更新DTO
 * @Author: Vimio
 * @Date: 2024/9/12 8:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitLogUuidTime {
    private String uuid;
    private Date time;
    private Integer pv;
}
