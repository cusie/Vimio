package top.vimio.entity.Logentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 访问记录
 * @Author: Vimio
 * @Date: 2024/9/12 8:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitRecord {
    private Long id;
    private Integer pv;//访问量
    private Integer uv;//独立用户
    private String date;//日期"02-23"

    public VisitRecord(Integer pv, Integer uv, String date) {
        this.pv = pv;
        this.uv = uv;
        this.date = date;
    }
}
