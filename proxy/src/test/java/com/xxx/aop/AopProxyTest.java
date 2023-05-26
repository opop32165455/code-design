package com.xxx.aop;

import com.xxx.SpringBootApp;
import com.xxx.bean.Interface0;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author R4441-zxc
 * @date 2022/1/26 14:13
 */
@SpringBootTest(classes = SpringBootApp.class)
@RunWith(SpringRunner.class)
@Slf4j
public class AopProxyTest {

    @Resource(name = "class01")
    private Interface0 class01;

    @Test
    public void test01() {
        class01.method1();
    }


}
