package com.xxx;

import lombok.Data;

/**
 * @author R4441-zxc
 * @date 2023/6/14 18:26
 */
@Data
public class Person {
    private Arms arms;

    /**
     * 与敌人对决
     */
    public void battle(){
        System.out.println("开始对决>>>>>");
        arms.fight();
    }
}
