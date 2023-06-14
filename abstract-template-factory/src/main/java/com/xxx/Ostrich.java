package com.xxx;

/**
 * 鸵鸟
 * @author R4441-zxc
 * @date 2023/6/14 16:54
 */
public class Ostrich extends BirdAnimal {
    @Override
    public void introduce() {
        System.out.println("i am an ostrich");
    }

    @Override
    public void eat() {
        System.out.println("我喜欢吃果实和虫子");
    }

    @Override
    public void fly() {
        System.out.println("我不会飞");
    }

    @Override
    public void run() {
        System.out.println("我跑得非常快");
    }
}
