package top.vimio.config.properties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 静态文件上传访问路径配置(目前用于评论中QQ头像的本地存储)
 * @Author: Vimio
 * @Date: 2024/9/4 10:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "upload.file")
public class UploadProperties {
	/**
	 * 本地文件路径
	 */
	private String path;
	/**
	 * 请求地址映射
	 */
	private String accessPath;
	/**
	 * 本地文件路径映射
	 */
	private String resourcesLocations;
}
