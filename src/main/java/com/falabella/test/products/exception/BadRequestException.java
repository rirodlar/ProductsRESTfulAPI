package com.falabella.test.products.exception;

import com.falabella.test.products.dto.ErrorMessageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = -602494716859410290L;
    private static final String DEFAULT_MESSAGE = "Bad Request Exception (400)";
    private ErrorMessageDto errorMessageDto;

    public BadRequestException(String message) {
        super(String.join(". ", message));
    }

    public BadRequestException(ErrorMessageDto errorMessage) {
        super(errorMessage.getMessage() != null ? errorMessage.getMessage() : DEFAULT_MESSAGE);
        this.errorMessageDto = errorMessage;
    }
}
