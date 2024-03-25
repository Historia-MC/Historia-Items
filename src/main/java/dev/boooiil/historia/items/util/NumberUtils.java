package dev.boooiil.historia.items.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * A utility class for number operations.
 */
public class NumberUtils {

    /** number utils default constructor */
    private NumberUtils() {
    }

    /**
     * Generates a random float between the min and max values.
     * 
     * @param min The minimum value of the random float.
     * @param max The maximum value of the random float.
     * @return A random float between the min and max values.
     */
    public static float random(int min, int max) {
        return (float) (Math.random() * (max - min) + min);
    }

    /**
     * Generates a random float between the min and max values.
     * 
     * @param min The minimum value of the random float.
     * @param max The maximum value of the random float.
     * @return A random float between the min and max values.
     */
    public static float random(float min, float max) {
        return (float) (Math.random() * (max - min) + min);
    }

    /**
     * Generates a random double between the min and max values.
     * 
     * @param min The minimum value of the random double.
     * @param max The maximum value of the random double.
     * @return A random double between the min and max values.
     */
    public static double random(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }

    /**
     * Generates a random float between the min and max values, rounded to the
     * nearest tenth.
     * 
     * @param min The minimum value of the random float.
     * @param max The maximum value of the random float.
     * @return A random float between the min and max values, rounded to the nearest
     *         tenth.
     */
    public static float randomToTenth(int min, int max) {
        return (float) (Math.round(Math.random() * (max - min) + min) * 10) / 10;
    }

    /**
     * Generates a random float between the min and max values, rounded to the
     * nearest hundredth.
     * 
     * @param min The minimum value of the random float.
     * @param max The maximum value of the random float.
     * @return A random float between the min and max values, rounded to the nearest
     *         hundredth.
     */
    public static float randomToHundredth(int min, int max) {
        return (float) (Math.round(Math.random() * (max - min) + min) * 100) / 100;
    }

    /**
     * Generates a random integer between the min and max values.
     * 
     * @param min The minimum value of the random integer.
     * @param max The maximum value of the random integer.
     * @return A random integer between the min and max values.
     */
    public static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    /**
     * Round a float value to the specified number of places.
     * 
     * @param value  The float value to round.
     * @param places The number of decimal places to round to.
     * @return The float value rounded to the specified number of places.
     */
    public static float roundFloat(float value, int places) {
        return (float) (Math.round(value * Math.pow(10, places)) / Math.pow(10, places));
    }

    /**
     * Round a double value to the specified number of places.
     * 
     * @param value  The double value to round.
     * @param places The number of decimal places to round to.
     * @return The double value rounded to the specified number of places.
     */
    public static double roundDouble(double value, int places) {
        return Double.parseDouble(
                new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
    }

}
