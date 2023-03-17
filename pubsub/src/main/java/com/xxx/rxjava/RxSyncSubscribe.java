package com.xxx.rxjava;

import cn.hutool.core.thread.ThreadUtil;
import rx.Observer;
import rx.observables.SyncOnSubscribe;

/**
 * @author zhangxuecheng4441
 * @date 2023/3/17/017 14:03
 */
public class RxSyncSubscribe extends SyncOnSubscribe<String, String> {
    final int limit = 10;
    int count = 0;

    @Override
    protected String generateState() {
        return "state";
    }

    @Override
    protected String next(String state, Observer<? super String> observer) {
        if (count++ <= limit) {
            ThreadUtil.sleep(1000);
            observer.onNext("on next:" + count);
        } else {
            observer.onCompleted();
        }
        //throw new NullPointerException("Throw a Exception...");
        return null;
    }

    public RxSyncSubscribe() {
        super();
    }
}
