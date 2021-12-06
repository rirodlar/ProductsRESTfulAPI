package com.falabella.test.products.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ViolationConstrainsProductException extends Exception{
    public ViolationConstrainsProductException(String message) {
        super(message);
    }
}
