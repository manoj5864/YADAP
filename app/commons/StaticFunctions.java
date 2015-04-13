package commons;

import java.util.Random;

/**
 * Created by manoj on 4/12/15.
 */
public class StaticFunctions {

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

}
