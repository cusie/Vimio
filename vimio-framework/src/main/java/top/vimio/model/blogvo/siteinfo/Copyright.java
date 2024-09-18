package top.vimio.model.blogvo.siteinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: copyright/站点开发作者
 * @Author: Vimio
 * @Date: 2024/9/3 9:33
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Copyright {
    private String title;
    private String siteName;
}
