package com.tyfzw.booksmanage.infrastructure.aop;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.tyfzw.booksmanage.application.service.LogService;
import com.tyfzw.booksmanage.domain.bo.LogBO;
import com.tyfzw.booksmanage.domain.bo.ProceedBO;
import com.tyfzw.booksmanage.infrastructure.common.enums.ResultCode;
import com.tyfzw.booksmanage.infrastructure.common.execption.BizException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2022-05-22
 * Time: 14:34
 */
@Component
@Aspect
@Slf4j
public class ParamInfoAspect {


    @Autowired
    private LogService logService;

    @Pointcut("execution(* com.tyfzw.booksmanage.gateway.api.web.controller.*.*(..))")
    public void pointClass() {
    }

    @Around(value = "pointClass()")
    public Object printParam(ProceedingJoinPoint joinPoint) throws Throwable {
        // 开始时间
        long startTime = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();

        log.info("method [{}] ", method.getName());
        Object proceed = joinPoint.proceed();
        String proceedStr = JSON.toJSONString(proceed);
        log.info("method [{}] , result:{}, cost:{}ms", method.getName(), proceedStr, System.currentTimeMillis() - startTime);
        LogBO logBO = new LogBO();
        try {
            ProceedBO proceedBO = JSON.parseObject(proceedStr, ProceedBO.class);
            logBO.setErrorCode(proceedBO.getCode());
            logBO.setErrorMessage(proceedBO.getMsg());
            logBO.setInterfaceInput(JSON.toJSONString(args));
        } catch (JSONException e) {
            log.warn("JSON转化出错，非标准输出格式");
        } catch (Exception e) {
            log.warn("出现错误：", e);
        }
        //System.out.println(proceedBO);
        logBO.setSystemName("booksManage");
        logBO.setInterfaceName(method.getName());
        logBO.setInterfaceOutput(proceedStr);
        logBO.setSystemCallTime(System.currentTimeMillis() - startTime);
        int i = logService.insertLog(logBO);
        if (i != 1) {
            throw new BizException(ResultCode.LOG_CREATE_FAIL);
        }
        return proceed;
    }


    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        System.out.println(startTime);
    }
}
