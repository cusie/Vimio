package top.vimio.utils.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: 分页结果
 * @Author: Vimio
 * @Date: 2024/8/30 16:25
 */
@Data
@NoArgsConstructor
public class PageResult<T> {
    private Integer totalPage;//总页数
    private List<T> list;//数据

    public PageResult(Integer totalPage, List<T> list) {
        this.totalPage = totalPage;
        this.list = list;
    }
}