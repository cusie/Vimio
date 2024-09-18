package top.vimio.service;

import top.vimio.entity.User;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/5 15:35
 */
public interface UserService {
    User findUserByUsernameAndPassword(String username, String password);

    User findUserById(Long id);

    boolean changeAccount(User user, String jwt);
}
