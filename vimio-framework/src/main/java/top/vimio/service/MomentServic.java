package top.vimio.service;

import top.vimio.entity.Moment;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/8/30 16:02
 */
public interface MomentServic {

    /**
     *
     * @Description: 前台显示业务
     * */
    List<Moment> getMomentVOList(Integer pageNum , /**/boolean adminIdentity);

    void addLikeByMomentId(Long momentId);


    /**
     *
     * @Description: 公共业务业务
     * */


    /**
     *
     * @Description: 后台管理业务
     * */
    List<Moment> getMomentList();

    Moment getMomentById(Long id);

    void updateMomentPublishedById(Long momentId, Boolean published);

    void deleteMomentById(Long id);

    void saveMoment(Moment moment);

    void updateMoment(Moment moment);
}
