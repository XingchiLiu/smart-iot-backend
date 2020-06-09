package com.example.iot.util;

/**
 * @author Karson
 * @CreateDate 2020/6/10 0:14
 */
public class CompareUtil {

    public static boolean GT(double val, double threshold){
        return val > threshold;
    }

    public static boolean LT(double val, double threshold){
        return val < threshold;
    }

    public static boolean EQ(double val, double threshold){
        return val == threshold;
    }

    public static boolean GE(double val, double threshold){
        return val >= threshold;
    }

    public static boolean LE(double val, double threshold){
        return val <= threshold;
    }

    public static boolean NE(double val, double threshold){
        return val != threshold;
    }
}
