package top.vimio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import top.vimio.annotation.OperationLogger;
import top.vimio.entity.User;
import top.vimio.utils.common.ResponseResult;
import top.vimio.service.UserService;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/14 18:20
 */
@RestController
public class AccountAdminController {
    @Autowired
    UserService userService;

    /**
     * 账号密码修改
     */
    @OperationLogger("修改密码")
    @PostMapping("/account")
    public ResponseResult account(@RequestBody User user, @RequestHeader(value = "Authorization", defaultValue = "") String jwt) {
        boolean res = userService.changeAccount(user, jwt);
        return res ? ResponseResult.ok("修改成功") : ResponseResult.error("修改失败");
    }
}
