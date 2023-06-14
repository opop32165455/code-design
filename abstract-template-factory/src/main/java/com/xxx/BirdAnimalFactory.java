package com.xxx;

import javafx.scene.shape.Circle;

/**
 * @author R4441-zxc
 * @date 2023/6/14 17:04
 */
public class BirdAnimalFactory {
    /**
     * get bird
     *
     * @param number animal number
     * @return animal
     */
    public BirdAnimal getBird(String number) {
        if (number == null) {
            return null;
        }
        if ("1".equalsIgnoreCase(number)) {
            return new Duck();
        } else if ("2".equalsIgnoreCase(number)) {
            return new Ostrich();
        } else {
            return null;
        }
    }
}
