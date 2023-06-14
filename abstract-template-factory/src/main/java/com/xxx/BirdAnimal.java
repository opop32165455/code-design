package com.xxx;

/**
 * 飞禽
 *
 * @author R4441-zxc
 * @date 2023/6/14 16:46
 */
public abstract class BirdAnimal implements Animal {
    /**
     * 飞翔
     */
    public abstract void fly();

    /**
     * 奔跑
     */
    public abstract void run();

    /**
     * 模板方法
     */
    @Override
    public void move(){
        System.out.println("start move");
        //具体功能交给子类实现
        fly();
        run();
    }


}
