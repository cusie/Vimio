package top.vimio.utils;

import org.springframework.web.client.RestTemplate;
import top.vimio.utils.common.QQResult;
import top.vimio.model.blogvo.vo.QQVO;

/**
 * @Description: 获取QQ昵称头像信息
 * @Author: Vimio
 * @Date: 2024/9/4 10:34
 */
public class QQInfoUtils {
    private static RestTemplate restTemplate = new RestTemplate();
    // 原接口半失效，需要提供cookie才可使用，暂时替换为备用接口，感谢 苏晓晴 大佬友情提供
    private static final String QQ_NICKNAME_URL = "https://api.qjqq.cn/api/qqinfo?qq={1}";
    //https://api.toubiec.cn/api/qqinfo_v4.php?qq={1}
    //https://api.qjqq.cn/api/qqinfo?qq={1}
    private static final String QQ_AVATAR_URL = "https://api.qjqq.cn/api/qqinfo?qq={1}";
    /**
     * 获取QQ昵称
     *
             * @param qq qq号，用于查询QQ昵称
     * @return 返回查询到的QQ昵称，如果查询失败，返回默认昵称"nickname"
            */

    public static String getQQNickname(String qq) {
        // 使用RestTemplate发送GET请求到QQ_NICKNAME_URL，并传入qq作为参数
        QQResult qqResultVO = restTemplate.getForObject(QQ_NICKNAME_URL, QQResult.class, qq);
        // 检查返回的结果是否为空
        if (qqResultVO!= null) {
            QQVO qqVO = new QQVO();
            qqVO.setName(qqResultVO.getName());
            qqVO.setEmail(qqResultVO.getMail());
            qqVO.setAvatar(qqResultVO.getImgurl());
            qqVO.setQq(Long.valueOf(qq));
            return qqVO.getName();
        }
        // 如果返回结果为空，返回默认昵称"nickname"
        return "nickname";
    }

    /**
     * 获取QQ头像URL
     *
     * @param qq
     * @return
     * @throws Exception
     */
    public static String getQQAvatarUrl(String qq) throws Exception {
        QQResult qqResult = restTemplate.getForObject(QQ_AVATAR_URL, QQResult.class, qq);
        return qqResult.getImgurl();
    }

    /**
     * 判断是否QQ号
     *
     * @param number
     * @return
     */
    public static boolean isQQNumber(String number) {
        return number.matches("^[1-9][0-9]{4,10}$");
    }
}
