package com.example.iot.util;

/**
 * @author zhm
 */
public enum FieldType {
    /**
     * 布尔类型
     */
    BOOLEAN(0),
    /**
     * 数字类型
     */
    DECIMAL(1),
    /**
     * 字符串类型
     */
    STRING(2);

    private final int value;

    private FieldType(int value) {
        this.value = value;
    }

    public static FieldType valueOf(int value) {
        switch (value) {
            case 0:
                return BOOLEAN;
            case 1:
                return DECIMAL;
            case 2:
                return STRING;
            default:
                return null;
        }
    }

    public int value() {
        return this.value;
    }
}
