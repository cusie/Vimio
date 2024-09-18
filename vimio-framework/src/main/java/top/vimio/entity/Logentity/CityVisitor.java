package top.vimio.entity.Logentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/12 8:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityVisitor {
    private String city;//城市名称
    private Integer uv;//独立访客数量
}
