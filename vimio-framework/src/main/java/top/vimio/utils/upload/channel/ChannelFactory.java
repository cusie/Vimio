package top.vimio.utils.upload.channel;

import top.vimio.constant.UploadConstants;
import top.vimio.utils.common.SpringContextUtils;
/**
 * @Description: 文件上传方式
 * @Author: Vimio
 * @Date: 2024/9/4 10:43
 */
public class ChannelFactory {
    /**
     * 创建文件上传方式
     *
     * @param channelName 方式名称
     * @return 文件上传Channel
     */
    public static FileUploadChannel getChannel(String channelName) {
        switch (channelName.toLowerCase()) {
            case UploadConstants.LOCAL:
                return SpringContextUtils.getBean(LocalChannel.class);
            case UploadConstants.GITHUB:
                return SpringContextUtils.getBean(GithubChannel.class);
            case UploadConstants.UPYUN:
                return SpringContextUtils.getBean(UpyunChannel.class);
        }
        throw new RuntimeException("Unsupported value in [application.properties]: [upload.channel]");
    }
}
