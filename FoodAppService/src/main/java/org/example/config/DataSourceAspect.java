package org.example.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 
 * @Date 2024/1/30 16:19
 */
//@Aspect
//@Component
public class DataSourceAspect implements Ordered {
    /**
     * 指定切入点
     */
    @Pointcut("@annotation(org.example.config.DataSource)")
    public void dataSourcePointCut(){

    }

    @After("dataSourcePointCut()")
    public void after(){
        DatabaseContextHolder.chearDataSource();
    }
    /**
     * 环绕通知，包含了五种通知类型
     * @return
     */
    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point){
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method=signature.getMethod();
        //默认数据源
        String defaultDataSource= DatabaseContextHolder.FOOD_DS;
        //获取该注解
        DataSource dataSource=method.getAnnotation(DataSource.class);
        //存在注解，直接切换该注解对应的数据源
        if(dataSource!=null){
            defaultDataSource=dataSource.value();
        }
        //切换数据源
        DatabaseContextHolder.setDataBase(defaultDataSource);
        //继续执行方法
        Object result=null;
        try {
            result=point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            //最后清除数据源
            DatabaseContextHolder.chearDataSource();
        }
        return result;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}

