package top.vimio.model.blogdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 访问日志备注
 * @Author: Vimio
 * @Date: 2024/9/14 15:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitLogRemark {
    /**
     * 访问内容
     */
    private String content;

    /**
     * 备注
     */
    private String remark;
}
