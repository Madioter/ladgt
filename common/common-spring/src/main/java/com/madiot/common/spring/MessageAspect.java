package com.madiot.common.spring;

import com.madiot.common.utils.json.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 响应的消息封装
 * 注意：使用前提controller方法的返回值需要为String类型
 * Created by Yi.Wang2 on 2016/12/8.
 */
@Component
@Aspect
public class MessageAspect {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageAspect.class);

    /**
     * 返回消息切点
     */
    @Pointcut("@annotation(com.madiot.common.spring.MessageBody)")
    public void methodResponse() {
    }

    /**
     * 返回响应消息切面
     *
     * @param point 切点
     * @return 响应的http响应消息结构体
     */
    @Around("methodResponse()")
    public Object doAround(ProceedingJoinPoint point) {
        // 响应统一封装成MessageResponse的形式，原先的数据保存在data属性中，
        // 要求响应的返回值必须为String类型，否则会报错
        MessageResponse response = new MessageResponse();
        try {
            Object result = point.proceed();
            // 做数据封装
            response.setSuccess(true);
            response.setData(result);

        } catch (Throwable throwable) {
            LOGGER.error(throwable.getMessage(), throwable);
            response.setSuccess(false);
            response.setErrorMessage(throwable.getMessage());
        }
        /**
         * 返回json字符串
         */
        return JsonUtils.toJsonString(response);
    }

}