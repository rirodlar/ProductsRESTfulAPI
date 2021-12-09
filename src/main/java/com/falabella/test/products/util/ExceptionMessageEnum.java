package com.falabella.test.products.util;

public enum ExceptionMessageEnum {

    PRODUCT_NOT_FOUND("Product Not Found"),
    SKU_NOT_FOUND("SKU Not Found");

    private final String value;

    ExceptionMessageEnum(String msg) {
        value = msg;
    }

    public String getValue() {
        return value;
    }
}
