package com.falabella.test.products.handler;

import com.falabella.test.products.dto.ErrorMessageDto;
import com.falabella.test.products.exception.BadRequestException;
import com.falabella.test.products.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResponseExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    @ResponseBody
    public ErrorMessageDto notFoundRequest(HttpServletRequest request, Exception exception) {
        exception.printStackTrace();
        if (exception instanceof NotFoundException) {
            NotFoundException notFoundException = (NotFoundException) exception;
            ErrorMessageDto errorMessageDto = notFoundException.getErrorMessageDto();
            return new ErrorMessageDto(exception, request.getRequestURI(), errorMessageDto);
        }
        return new ErrorMessageDto(exception, request);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BadRequestException.class,
            org.springframework.dao.DuplicateKeyException.class,
            org.springframework.web.HttpRequestMethodNotSupportedException.class,
            org.springframework.web.bind.MissingRequestHeaderException.class,
            org.springframework.web.bind.MissingServletRequestParameterException.class,
            org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class,
            org.springframework.http.converter.HttpMessageNotReadableException.class,
    })
    @ResponseBody
    public ErrorMessageDto badRequest(HttpServletRequest request, Exception exception) {

        BadRequestException badRequestException = (BadRequestException) exception;
        ErrorMessageDto errorMessageDto = badRequestException.getErrorMessageDto();
        return new ErrorMessageDto(exception, request.getRequestURI(), errorMessageDto);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ErrorMessageDto fatalErrorUnexpectedException(HttpServletRequest request, Exception exception) {
        exception.printStackTrace();
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(exception, request);

        return errorMessageDto;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorMessageDto handleValidationExceptions(HttpServletRequest request, MethodArgumentNotValidException exception) {
        return new ErrorMessageDto(exception, request.getRequestURI());
    }
}
