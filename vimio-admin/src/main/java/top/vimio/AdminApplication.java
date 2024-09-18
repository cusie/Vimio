package top.vimio;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/10 9:41
 */

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"top.vimio","top.vimio.mapper","top.vimio.*","top.vimio.utils","top.vimio.task"})
public class AdminApplication {
    public static void main( String[] args )
    {
        SpringApplication.run(AdminApplication.class, args);
        //TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }
}
