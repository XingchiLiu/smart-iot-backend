package com.example.iot.util;

/**
 * @author zhm
 */
public enum FieldType {
    BOOLEAN(0),
    INTEGER(1),
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
                return INTEGER;
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
