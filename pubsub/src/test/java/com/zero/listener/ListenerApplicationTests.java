package com.zero.listener;


import cn.hutool.core.thread.ThreadUtil;
import com.xxx.ListenerApplication;
import com.xxx.jdkob.CustomObservable;
import com.xxx.jdkob.CustomObserver;
import com.xxx.spring.CustomEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.IntStream;

@SpringBootTest(classes = ListenerApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ListenerApplicationTests {
    @Resource
    ApplicationContext applicationContext;

    @Test
    public void springPubSub() {
        applicationContext.publishEvent(new CustomEvent(this, "pub1"));
        applicationContext.publishEvent(new CustomEvent(this, "pub2"));
        applicationContext.publishEvent(new CustomEvent(this, "pub3"));
        applicationContext.publishEvent(new CustomEvent(this, "pub4"));
        applicationContext.publishEvent(new CustomEvent(this, "pub5"));
        applicationContext.publishEvent(new CustomEvent(this, "pub6"));

        IntStream.range(0, 20).forEach(i -> {
            ThreadUtil.sleep(1000);
            log.warn("waiting for {}...", i);
        });
    }

    @Test
    public void jdkObs() {
        //todo demo1
        CustomObservable customObservable = new CustomObservable();

        //将观察者1放入集合中 订阅1
        customObservable.addObserver((ob, arg) -> {
            log.info("Custom consume data ...");
            CustomObservable boa = (CustomObservable) ob;
            System.out.println("lambda 接收到了：" + arg);
        });

        //将观察者2放入集合中 订阅2
        customObservable.addObserver(new CustomObserver());

        //告诉观察者 可以执行一次
        customObservable.setChanged();
        //发送事件 集合中订阅的对象们 挨个处理订阅的信息
        customObservable.notifyObservers(Arrays.asList(1, 2, 3, 4, 5));

        //告诉观察者 可以执行一次 没有setChanged则无法处理消息
        customObservable.setChanged();
        customObservable.notifyObservers(Arrays.asList(2, 5));
        customObservable.notifyObservers(Arrays.asList(1, 3));
        customObservable.notifyObservers();

    }


    @Test
    public void contextLoads() {
        Flux.just("Hello", "World").subscribe(System.out::println);
        Flux.fromArray(new Integer[]{1, 2, 3}).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        Flux.range(1, 10).subscribe(System.out::println);
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
    }

}
