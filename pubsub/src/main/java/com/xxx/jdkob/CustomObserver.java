package com.xxx.jdkob;

import lombok.extern.slf4j.Slf4j;

import java.util.Observable;
import java.util.Observer;

/**
 * @author zhangxuecheng4441
 * @date 2021/8/14/014 17:27
 */
@Slf4j
public class CustomObserver implements Observer {

    /**
     * 消费处理函数
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     *            method.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CustomObservable) {
            log.info("CustomObserver consume data ...");
            CustomObservable boa = (CustomObservable) o;
            System.out.println(boa.name + "接收到了：" + arg);
        }
    }

}
