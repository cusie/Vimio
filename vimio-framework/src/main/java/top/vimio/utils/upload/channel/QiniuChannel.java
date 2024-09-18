package top.vimio.utils.upload.channel;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import top.vimio.config.properties.QiniuProperties;
import top.vimio.utils.upload.UploadUtils;

import java.util.UUID;

/**
 * @Description:七牛云存储上传方式
 * @Author: Vimio
 * @Date: 2024/9/15 12:13
 */

@Lazy
@Component
public class QiniuChannel implements FileUploadChannel {
    private UploadManager uploadManager;
    private QiniuProperties qiniuProperties;

    public QiniuChannel(QiniuProperties qiniuProperties) {
        this.qiniuProperties = qiniuProperties;
        Configuration cfg = new Configuration(Region.region0());
        this.uploadManager = new UploadManager(cfg);
    }

    @Override
    public String upload(UploadUtils.ImageResource image) throws Exception {
        Auth auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        String fileAbsolutePath = qiniuProperties.getPath() + "/" + UUID.randomUUID() + "." + image.getType();
        try {
            Response response = uploadManager.put(image.getData(), fileAbsolutePath, auth.uploadToken(qiniuProperties.getBucketName()));
            if (!response.isOK()) {
                throw new RuntimeException("七牛云上传失败");
            }
            return qiniuProperties.getDomain() + fileAbsolutePath;
        } catch (QiniuException e) {
            Response r = e.response;
            throw new RuntimeException("七牛云上传失败：" + r.toString());
        }
    }
}