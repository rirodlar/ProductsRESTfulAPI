package com.falabella.test.products.exception;

import com.falabella.test.products.dto.ErrorMessageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MESSAGE = "Not Found Exception (404)";
    private ErrorMessageDto errorMessageDto;

    public NotFoundException(String message) {
        super(String.join(". ", message));
    }

    public NotFoundException(ErrorMessageDto errorMessage) {
        super(errorMessage.getMessage() != null ? errorMessage.getMessage() : DEFAULT_MESSAGE);
        this.errorMessageDto = errorMessage;
    }
}
