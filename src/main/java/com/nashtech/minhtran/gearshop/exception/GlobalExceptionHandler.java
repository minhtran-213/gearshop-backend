package com.nashtech.minhtran.gearshop.exception;

import com.nashtech.minhtran.gearshop.constants.ErrorCode;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.model.Manufacturer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    ResponseDTO messageResponse;

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ResponseDTO> handleAccessDenied(AccessDeniedException e) {
        messageResponse = new ResponseDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(messageResponse);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ResponseDTO> handleUsernameOrPasswordException(BadCredentialsException e) {
        messageResponse = new ResponseDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(messageResponse);
    }

    @ExceptionHandler({InvalidEmailException.class})
    public ResponseEntity<ResponseDTO> handleInvalidEmail(InvalidEmailException e) {
        messageResponse = new ResponseDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({InvalidPasswordException.class})
    public ResponseEntity<ResponseDTO> handleInvalidPassword(InvalidPasswordException e) {
        messageResponse = new ResponseDTO(e.getMessage());
        return ResponseEntity.badRequest().body(messageResponse);
    }

    @ExceptionHandler({EmailExistException.class})
    public ResponseEntity<ResponseDTO> handleEmailExist(EmailExistException e) {
        messageResponse = new ResponseDTO(e.getMessage());
        return ResponseEntity.badRequest().body(messageResponse);
    }

    @ExceptionHandler({InvalidOldPasswordException.class})
    public ResponseEntity<ResponseDTO> handleInvalidOldPassword(InvalidOldPasswordException e) {
        messageResponse = new ResponseDTO(e.getMessage());
        return ResponseEntity.badRequest().body(messageResponse);
    }

    @ExceptionHandler({ProductNotExistException.class})
    public ResponseEntity<ResponseDTO> handleProductNotExist(ProductNotExistException e) {
        messageResponse = new ResponseDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(messageResponse);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ResponseDTO> handleUserNotFound(UserNotFoundException e) {
        messageResponse = new ResponseDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(messageResponse);
    }

    @ExceptionHandler({AddressNotFoundException.class})
    public ResponseEntity<ResponseDTO> handleAddressNotFound(AddressNotFoundException e) {
        messageResponse = new ResponseDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(messageResponse);
    }

    @ExceptionHandler({RetrieveProductDetailException.class})
    public ResponseEntity<ResponseDTO> handleRetrieveProductDetail(RetrieveProductDetailException e) {
        messageResponse = new ResponseDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
    }

    @ExceptionHandler({RetrieveProductException.class})
    public ResponseEntity<ResponseDTO> handleRetrieveProduct(RetrieveProductException e) {
        messageResponse = new ResponseDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
    }

    @ExceptionHandler({CategoryNotExistException.class})
    public ResponseEntity<ResponseDTO> handleCategoryNotFound(CategoryNotExistException e) {
        messageResponse = new ResponseDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(messageResponse);
    }

    @ExceptionHandler({ManufacturerNotExistException.class})
    public ResponseEntity<ResponseDTO> handleManufacturerNotFound(ManufacturerNotExistException e){
        messageResponse = new ResponseDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(messageResponse);
    }



    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseDTO> handleException(Exception e) {
        messageResponse = new ResponseDTO("UNKNOWN_ERROR");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(messageResponse);
    }
}
