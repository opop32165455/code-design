package com.xxx.mq.publish;

import com.xxx.mq.subscrib.Subscriber;
import lombok.NonNull;

import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangxuecheng4441
 * @date 2021/8/19/019 9:23
 */
public class Publisher<T> {
    /**
     * 存储容器
     */
    LinkedBlockingDeque<T> collection;

    /**
     * 总容量设置
     */
    public Publisher(int collectSize) {
        this.collection = new LinkedBlockingDeque<>(collectSize);
    }

    /**
     * 订阅者容器
     */
    private final Vector<Subscriber> subs = new Vector<>();
    private final ReentrantLock addLock = new ReentrantLock();

    /**
     * 增加订阅者
     *
     * @param sub Subscriber
     * @return Publisher
     */
    public Publisher<T> addSub(@NonNull Subscriber sub) {
        addLock.lock();
        try {
            if (!subs.contains(sub)) {
                subs.add(sub);
            }
        } finally {
            addLock.unlock();
        }
        return this;
    }

    /**
     * 批量新增消息
     *
     * @param message message
     */
    public void add(@NonNull Collection<? extends T> message) {
        for (T datum : message) {
            try {
                collection.putFirst(datum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 新增单个消息
     *
     * @param message message
     */
    public void add(@NonNull T message) {
        try {
            collection.putFirst(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 所有订阅者消费 全部消费
     * todo 其他消费方式可以自定义
     */
    public void notifyAllSub() {
        for (Subscriber subscriber : subs) {
            subscriber.consumer(collection);
        }
        collection.clear();
    }


    /**
     * 指定订阅者消费
     *
     * @param subscribers subscribers
     */
    public void notifySub(Subscriber... subscribers) {
        Subscriber[] subArray;

        if (subscribers == null || subscribers.length == 0) {
            subArray = subs.toArray(new Subscriber[0]);
        } else {
            subArray = subscribers;
        }

        for (Subscriber subscriber : subArray) {
            subscriber.consumer(collection);
        }
    }

    public int getCollectionSize() {
        return collection.size();
    }

    public LinkedBlockingDeque<T> getCollection() {
        return collection;
    }
}
