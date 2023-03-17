package com.zero.listener;


import cn.hutool.core.thread.ThreadUtil;
import com.xxx.ListenerApplication;
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
    public void contextLoads() {
        Flux.just("Hello", "World").subscribe(System.out::println);
        Flux.fromArray(new Integer[]{1, 2, 3}).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        Flux.range(1, 10).subscribe(System.out::println);
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
    }

}
