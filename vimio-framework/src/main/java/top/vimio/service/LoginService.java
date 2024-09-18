package top.vimio.service;

import top.vimio.utils.common.ResponseResult;
import top.vimio.model.blogdto.LoginInfoDto;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/10 10:52
 */
public interface LoginService {
    ResponseResult login(LoginInfoDto loginInfo);
}
