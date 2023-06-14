package com.xxx;

/**
 * 动物接口
 * @author R4441-zxc
 * @date 2023/6/14 16:44
 */
public interface Animal {

    /**
     * 自我介绍
     */
    void introduce();

    /**
     * 呼吸
     */
    default void breath(){
        System.out.println("I'm getting some fresh air");
    }

    /**
     * 移动
     */
    void move();

    /**
     * 进食
     */
    void eat();


}
