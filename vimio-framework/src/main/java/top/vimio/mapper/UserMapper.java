package top.vimio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.vimio.entity.User;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/5 15:07
 */
@Mapper
@Repository
public interface UserMapper {

    User findByUsername(String username);

    User findById(Long id);

    int updateUserByUsername(@Param("username")String username, @Param("user")User user);
}
