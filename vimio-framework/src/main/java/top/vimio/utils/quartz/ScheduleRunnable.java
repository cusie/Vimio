package top.vimio.utils.quartz;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import top.vimio.utils.common.SpringContextUtils;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/15 16:41
 */
public class ScheduleRunnable implements Runnable{
    private Object target;
    private Method method;
    private String params;

    public ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
        this.target = SpringContextUtils.getBean(beanName);
        this.params = params;
        if (StringUtils.hasText(params)) {
            this.method = target.getClass().getDeclaredMethod(methodName, String.class);
        } else {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }
    }

    @Override
    public void run() {
        try {
            ReflectionUtils.makeAccessible(method);
            if (StringUtils.hasText(params)) {
                method.invoke(target, params);
            } else {
                method.invoke(target);
            }
        } catch (Exception e) {
            throw new RuntimeException("执行定时任务失败", e);
        }
	}
}
