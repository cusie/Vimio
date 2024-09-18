package top.vimio.config.properties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: GitHub配置(目前用于评论中QQ头像的图床)
 * @Author: Vimio
 * @Date: 2024/9/4 9:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "upload.github")
public class GithubProperties {
	/**
	 * GitHub token
	 */
	private String token;
	/**
	 * GitHub username
	 */
	private String username;
	/**
	 * GitHub 仓库名
	 */
	private String repos;
	/**
	 * GitHub 仓库路径
	 */
	private String reposPath;
}
