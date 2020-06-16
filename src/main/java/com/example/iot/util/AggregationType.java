package com.example.iot.util;

/**
 * @author zhm
 */
public enum AggregationType {
    /**
     * 最大值
     */
    MAX(0),
    /**
     * 最小值
     */
    MIN(1),
    /**
     * 平均值
     */
    AVG(2),
    /**
     * 求和
     */
    SUM(3),
    /**
     * 次数
     */
    COUNT(4);

    private final int value;

    private AggregationType(int value) {
        this.value = value;
    }

    public static AggregationType valueOf(int value) {
        switch (value) {
            case 0:
                return MAX;
            case 1:
                return MIN;
            case 2:
                return AVG;
            case 3:
                return SUM;
            case 4:
                return COUNT;
            default:
                return null;
        }
    }

    public int value() {
        return this.value;
    }
}
