package com.gsp.springcloud.annotation;

import com.gsp.springcloud.model.Log;
import com.gsp.springcloud.model.User;
import com.gsp.springcloud.service.IProjectService;
import com.gsp.springcloud.utils.AddressUtils;
import com.gsp.springcloud.utils.DateUtils;
import com.gsp.springcloud.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import static com.gsp.springcloud.staticproperties.TimeFormatProperties.*;
import static com.gsp.springcloud.staticproperties.TimeFormatProperties.TIME_FORMAT;

/**
 * @Author Don
 * @Date: 2020/7/16 8:56
 * @Discription:
 * @Version 1.0
 **/
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private IProjectService iProjectService;

    /**
     * @Author Don
     * @Description 定义切点信息
     * 这个时候就不能再按照常规的切点(service/controller)
     * 直接去切自定义的注解
     * 也就是说当检测自定义注解存在的时候，切面触发，也就是说AOP才会被触发
     * @Date 2020/7/16 9:12
     **/
    @Pointcut("@annotation(com.gsp.springcloud.annotation.LoginAnnotation)")
    public void pointcut() {
        //TODO nothing to do
    }

    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws ClassNotFoundException {
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        //获取Request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //1.获取IP地址
        String ipAddr = IPUtils.getIpAddr(request);
        //2.获取地理位置
        Map<String, Object> addressMap = AddressUtils.getAddresses(ipAddr, "UTF-8");

        //创建日志对象并添加相关数值
        Log log = new Log();
        log.setIp(ipAddr);
        log.setLocation(addressMap.get("province") + "|" + addressMap.get("city"));
        log.setLoginTime(DateUtils.formatDate(new Date(), TIME_FORMAT));

        //3.获取UserName 要想获取username 必须获取目标方法的参数值
        Object[] args = proceedingJoinPoint.getArgs();
        User user = (User) args[0];
        log.setUsername(user.getUsername());

        //4.获取操作的习惯以及具体操作的内容（反射）
        //4.1获取目标的类名（全限定名）
        String tarClassName = proceedingJoinPoint.getTarget().getClass().getName();

        String tarMehtodName = proceedingJoinPoint.getSignature().getName();

        //4.2获取类对象
        Class tarClass = Class.forName(tarClassName);
        //4.3获取目标类中的所有方法
        Method[] methods = tarClass.getMethods();

        String operationType = "";
        String operationName = "";

        for(Method method : methods) {
            String methodName = method.getName();
            if(tarMehtodName.equals(methodName)) {
                // 这个时候虽然已经确定了目标方法没有问题，但是有可能会出现方法的重载
                // 还需要进一步判断
                // 4.4.获取目标方法的参数
                Class[] parameterTypes = method.getParameterTypes();
                if(parameterTypes.length == args.length) {
                    // 获取目标方法 完美，优秀 英俊！
                    operationType = method.getAnnotation(LoginAnnotation.class).opeationType();
                    operationName = method.getAnnotation(LoginAnnotation.class).opeationName();
                }
            }
        }

        log.setOperationName(operationName);
        log.setOperationType(operationType);

        iProjectService.addLoginLog(log);

        return result;
    }
}
