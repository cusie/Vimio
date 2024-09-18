package top.vimio;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/8/27 20:52
 */

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"top.vimio","top.vimio.mapper","top.vimio.*","top.vimio.service"})
public class BlogApplication {
    public static void main( String[] args )
    {
        SpringApplication.run(BlogApplication.class, args);
        //TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

}
