package com.endava.rpg.gp.util;

import java.util.concurrent.ThreadLocalRandom;

public final class ProcessorUtil {

    private ProcessorUtil(){
    }

    public static int getRandomInt(int lowerBorder, int upperBorder) {
        if (lowerBorder == upperBorder) {
            return lowerBorder;
        } else {
            return ThreadLocalRandom.current().nextInt(lowerBorder, upperBorder);
        }
    }
}
