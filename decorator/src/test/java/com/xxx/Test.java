package com.xxx;

import lombok.val;

/**
 * @author R4441-zxc
 * @date 2023/6/14 18:26
 */
public class Test {
    public static void main(String[] args) {

        //person1装饰武器
        val person1 = new Person();
        person1.setArms(new Gun());

        //对person2进行装饰武器
        val person2 = new Person();
        person2.setArms(new Sword());

        //执行相关逻辑
        //person执行的逻辑最好只与Arms接口耦合 即能够合理执行模版方法不与Arms的子类实现耦合
        person1.battle();
        person2.battle();
    }
}
