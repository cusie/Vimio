package top.vimio.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/14 16:33
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {
        "top.vimio.controller", // 扫描控制器
        "top.vimio.aspect"      // 扫描 AOP 组件
})
public class ExceptionConfig {
}
