package com.falabella.test.products.dto;

import com.falabella.test.products.util.Constant;
import com.falabella.test.products.util.MessageType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class ErrorMessageDto {

    private boolean valid;
    private String exception;
    private String message;
    private String path;
    private String description; //no se envia al cliente
    private String traceId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.FORMAT_DATE)
    private LocalDateTime localDateTime;
    private List<MessageDto> messageList = new ArrayList<>();

    public ErrorMessageDto(Exception exception, String path, ErrorMessageDto errorMessageDto) {
        this.exception = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.localDateTime = LocalDateTime.now();
        this.path = path;
        if (errorMessageDto != null) {
            this.setMessageList(errorMessageDto.getMessageList());
        }
        this.valid = false;
        log.info("Error :" + this);
    }


    public ErrorMessageDto(MethodArgumentNotValidException exception, String path) {
        this.exception = exception.getClass().getSimpleName();
        this.message = exception.getClass().getSimpleName();
        this.path = path;
        this.localDateTime = LocalDateTime.now();
        this.valid = false;
        List<MessageDto> messageList = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();

            String errorValidation = error.getDefaultMessage();
            String messageError = "";
            if (((FieldError) error).getRejectedValue() != null) {
                messageError = ((FieldError) error).getRejectedValue().toString();
            }
            log.info("ArgumentNotValid {} , {}, {}", fieldName, errorValidation, messageError);
            messageList.add(new MessageDto(fieldName, errorValidation, MessageType.ERROR.getValue()));
        });
        this.setMessageList(messageList);
        log.info("Error :" + this);
    }


    public ErrorMessageDto(Exception exception, HttpServletRequest request) {
        this.valid = false;
        this.exception = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.path = request.getRequestURI();
        this.localDateTime = LocalDateTime.now();
        log.info("Error :" + this);

    }


    @Override
    public String toString() {
        return "ErrorMessage{" +
                "valid=" + valid +
                ", exception='" + exception + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                ", description='" + description + '\'' +
                ", traceId='" + traceId + '\'' +
                ", messageList=" + messageList +
                '}';
    }
}
