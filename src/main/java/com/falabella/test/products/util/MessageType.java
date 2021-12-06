package com.falabella.test.products.util;

public enum MessageType {
    SUCCESS(0),
    ERROR(1),
    WARNING(2),
    INFO(3);

    private int value;

    MessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
