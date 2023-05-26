package com.xxx.aop;

import com.xxx.SpringBootApp;
import com.xxx.anno.AopAnno;
import com.xxx.bean.Interface0;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.weaver.ast.Var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author R4441-zxc
 * @date 2022/1/26 14:13
 */
@SpringBootTest(classes = SpringBootApp.class)
@RunWith(SpringRunner.class)
@Slf4j
public class AopProxyTest {

    @Resource(name = "class01") private Interface0 class01;



    @Test
    public void test01() {
        class01.method1();
    }


}
