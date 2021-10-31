package com.nashtech.minhtran.gearshop.exception;

import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    MessageResponse messageResponse;
    @ExceptionHandler({InvalidEmailException.class})
    public ResponseEntity<MessageResponse> handleInvalidEmail(InvalidEmailException e){
        messageResponse = new MessageResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({InvalidPasswordException.class})
    public ResponseEntity<MessageResponse> handleInvalidPassword(InvalidPasswordException e){
        messageResponse = new MessageResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({EmailExistException.class})
    public ResponseEntity<MessageResponse> handleEmailExist(EmailExistException e){
        messageResponse = new MessageResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({EmptyBodyException.class})
    public ResponseEntity<MessageResponse> handleEmptyBody(EmptyBodyException e){
        messageResponse = new MessageResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({ManufacturerNotExistException.class})
    public ResponseEntity<MessageResponse> handleNotExistManufacturer(ManufacturerNotExistException e){
        messageResponse = new MessageResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({EmptyNameManufacturerException.class})
    public ResponseEntity<MessageResponse> handleEmptyNameManufacturer(EmptyNameManufacturerException e){
        messageResponse = new MessageResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({EmptyNameCategoryException.class})
    public ResponseEntity<MessageResponse> handleEmptyNameCategory(EmptyNameCategoryException e){
        messageResponse = new MessageResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({CategoryNotExistException.class})
    public ResponseEntity<MessageResponse> handleNotExistCategory(CategoryNotExistException e){
        messageResponse = new MessageResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<MessageResponse> handleException(Exception e){
        logger.error(e.getMessage());
        messageResponse = new MessageResponse("Unknown Error", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(messageResponse);
    }
}
