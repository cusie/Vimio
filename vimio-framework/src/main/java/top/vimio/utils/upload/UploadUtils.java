package top.vimio.utils.upload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import top.vimio.constant.UploadConstants;
import top.vimio.exception.BadRequestException;
import top.vimio.utils.upload.channel.ChannelFactory;
import top.vimio.utils.upload.channel.FileUploadChannel;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/4 10:40
 */
@Component
@DependsOn("springContextUtils")
public class UploadUtils {
    private static RestTemplate restTemplate;

    private static FileUploadChannel uploadChannel;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        UploadUtils.restTemplate = restTemplate;
    }

    @Value("${upload.channel}")
    public void setNotifyChannel(String channelName) {
        UploadUtils.uploadChannel = ChannelFactory.getChannel(channelName);
    }

    @AllArgsConstructor
    @Getter
    public static class ImageResource {
        byte[] data;
        //图片拓展名 jpg png
        String type;
    }

    /**
     * 通过指定方式存储图片
     *
     * @param image 需要保存的图片
     * @throws Exception
     */
    public static String upload(ImageResource image) throws Exception {
        return uploadChannel.upload(image);
    }

    /**
     * 从网络获取图片数据
     *
     * @param url 图片URL
     * @return
     */
    public static ImageResource getImageByRequest(String url) {
        ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(url, byte[].class);
        if (UploadConstants.IMAGE.equals(responseEntity.getHeaders().getContentType().getType())) {
            return new ImageResource(responseEntity.getBody(), responseEntity.getHeaders().getContentType().getSubtype());
        }
        throw new BadRequestException("response contentType unlike image");
    }
}
