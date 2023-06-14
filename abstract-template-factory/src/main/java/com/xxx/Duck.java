package com.xxx;

/**
 * 鸵鸟
 * @author R4441-zxc
 * @date 2023/6/14 16:54
 */
public class Duck extends BirdAnimal {
    @Override
    public void introduce() {
        System.out.println("i am a duck");
    }

    @Override
    public void eat() {
        System.out.println("我喜欢吃鱼");
    }

    @Override
    public void fly() {
        System.out.println("我不能飞太远");
    }

    @Override
    public void run() {
        System.out.println("我跑得不快");
    }
    @Override
    public void move(){
        super.move();
        swim();
    }

    public void swim(){
        System.out.println("我擅长游泳");
    }
}
