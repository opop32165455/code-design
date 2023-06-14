package com.xxx;

import lombok.val;

/**
 * decorator
 * @author R4441-zxc
 * @date 2023/6/14 17:06
 */
public class Test {
    public static void main(String[] args) {
        test01();
    }


    public static void test01(){
        val animalFactory = new BirdAnimalFactory();
        Animal bird1 = animalFactory.getBird("1");

        checkBirdAnimal(bird1);

        System.out.println(">>>>>>>>>>>>>>>>>>>");
        Animal bird2 = animalFactory.getBird("2");
        checkBirdAnimal(bird2);

    }

    private static void checkBirdAnimal(Animal bird) {
        bird.introduce();
        bird.breath();
        //模板方法
        bird.move();
        bird.eat();
    }
}
