package top.vimio.utils.upload.channel;

import top.vimio.utils.upload.UploadUtils;

/**
 * @Description: 文件上传方式
 * @Author: Vimio
 * @Date: 2024/9/4 10:43
 */
public interface FileUploadChannel {
    /**
     * 通过指定方式上传文件
     *
     * @param image 需要保存的图片
     * @return 访问图片的URL
     * @throws Exception
     */
    String upload(UploadUtils.ImageResource image) throws Exception;
}
