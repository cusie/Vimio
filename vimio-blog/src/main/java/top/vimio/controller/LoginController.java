package top.vimio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.vimio.constant.JwtConstants;
import top.vimio.entity.User;
import top.vimio.utils.common.ResponseResult;
import top.vimio.model.blogdto.LoginInfoDto;
import top.vimio.service.LoginService;
import top.vimio.service.UserService;
import top.vimio.utils.JwtUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 前台登录
 * @Author: Vimio
 * @Date: 2024/9/5 15:54
 */
@RestController
public class LoginController implements LoginService {
    @Autowired
    UserService userService;

    /**
     * 登录成功后，签发博主身份Token
     *
     * @param loginInfo
     * @return
     */
    @Override
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginInfoDto loginInfo) {
        User user = userService.findUserByUsernameAndPassword(loginInfo.getUsername(), loginInfo.getPassword());
        if (!"ROLE_admin".equals(user.getRole())) {
            return ResponseResult.create(403, "无权限");
        }
        user.setPassword(null);
        String jwt = JwtUtils.generateToken(JwtConstants.ADMIN_PREFIX + user.getUsername());
        Map<String, Object> map = new HashMap<>(4);
        map.put("user", user);
        map.put("token", jwt);
        return ResponseResult.ok("登录成功", map);
    }
}
