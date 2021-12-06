package com.falabella.test.products.util;

public enum ExceptionMessageEnum {

    PRODUCT_NOT_FOUND("Product Not Found"),
    SKU_NOT_FOUND("SKU Not Found");

    ExceptionMessageEnum(String msg) {
        value = msg;
    }

    private final String value;

    public String getValue() {
        return value;
    }
}
