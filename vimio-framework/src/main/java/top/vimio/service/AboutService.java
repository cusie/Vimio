package top.vimio.service;

import java.util.Map;

public interface AboutService {

    /**
    *
    * @Description: 前台显示业务
    * */
    Map<String, String> getAboutInfo();     //获取关于我的信息

    /**
     *
     * @Description: 公共业务
     * */
    boolean getAboutCommentEnabled();       //评论是否开启，评论工具类

    /**
     *
     * @Description: 后台管理业务
     * */
    Map<String, String> getAboutSetting();

    void updateAbout(Map<String, String> map);

}
