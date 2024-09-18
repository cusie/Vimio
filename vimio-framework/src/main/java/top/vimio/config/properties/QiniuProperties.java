package top.vimio.config.properties;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 七牛云上传方式
 * @Author: Vimio
 * @Date: 2024/9/15 12:09
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "upload.qiniu")
public class QiniuProperties {
    /**
     * 七牛云存储空间名称
     */
    private String bucketName;
    /**
     * 访问密钥
     */
    private String accessKey;
    /**
     * 秘密密钥
     */
    private String secretKey;
    /**
     * 存储路径
     */
    private String path;
    /**
     * CDN 访问域名
     */
    private String domain;
}