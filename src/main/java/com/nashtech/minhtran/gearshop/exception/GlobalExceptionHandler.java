package com.nashtech.minhtran.gearshop.exception;

import com.nashtech.minhtran.gearshop.constants.ErrorCode;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    ResponseDTO messageResponse;
    @ExceptionHandler({InvalidEmailException.class})
    public ResponseEntity<ResponseDTO> handleInvalidEmail(InvalidEmailException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.INVALID_EMAIL_FORMAT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({InvalidPasswordException.class})
    public ResponseEntity<ResponseDTO> handleInvalidPassword(InvalidPasswordException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.INVALID_PASSWORD_FORMAT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({EmailExistException.class})
    public ResponseEntity<ResponseDTO> handleEmailExist(EmailExistException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.EXIST_EMAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({EmptyBodyException.class})
    public ResponseEntity<ResponseDTO> handleEmptyBody(EmptyBodyException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.EMPTY_BODY);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({ManufacturerNotExistException.class})
    public ResponseEntity<ResponseDTO> handleNotExistManufacturer(ManufacturerNotExistException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.ERROR_MANUFACTURER_NOT_EXIST);
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(messageResponse);
    }

    @ExceptionHandler({EmptyNameManufacturerException.class})
    public ResponseEntity<ResponseDTO> handleEmptyNameManufacturer(EmptyNameManufacturerException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.ERROR_MANUFACTURER_EMPTY_NAME);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({EmptyNameCategoryException.class})
    public ResponseEntity<ResponseDTO> handleEmptyNameCategory(EmptyNameCategoryException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.ERROR_MANUFACTURER_NOT_EXIST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({CategoryNotExistException.class})
    public ResponseEntity<ResponseDTO> handleNotExistCategory(CategoryNotExistException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.ERROR_CATEGORY_NOT_EXIST);
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(messageResponse);
    }

    @ExceptionHandler({ProductNotExistException.class})
    public ResponseEntity<ResponseDTO> handleNotExistProduct(ProductNotExistException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.ERROR_MANUFACTURER_NOT_EXIST);
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(messageResponse);
    }

    @ExceptionHandler({ProductDetailNotExistException.class})
    public ResponseEntity<ResponseDTO> handleNotExistProductDetail(ProductDetailNotExistException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.ERROR_PRODUCT_DETAIL_NOT_EXIST);
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(messageResponse);
    }

    @ExceptionHandler({EmptyProductIdException.class})
    public ResponseEntity<ResponseDTO> handleEmptyProductId(EmptyProductIdException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.ERROR_PRODUCT_ID_EMPTY);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({RetrieveCategoriesException.class})
    public ResponseEntity<ResponseDTO> handleRetrieveCategories(RetrieveCategoriesException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.ERROR_RETRIEVE_CATEGORIES);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(messageResponse);
    }

    @ExceptionHandler({RetrieveManufacturerException.class})
    public ResponseEntity<ResponseDTO> handleRetrieveManufacturer(RetrieveManufacturerException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.ERROR_RETRIEVE_MANUFACTURERS);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(messageResponse);
    }

    @ExceptionHandler({RetrieveUserException.class})
    public ResponseEntity<ResponseDTO> handleRetrieveUser(RetrieveUserException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.ERROR_RETRIEVE_USERS_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(messageResponse);
    }

    @ExceptionHandler({RetrieveProductException.class})
    public ResponseEntity<ResponseDTO> handleRetrieveUser(RetrieveProductException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.ERROR_RETRIEVE_PRODUCTS);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(messageResponse);
    }

    @ExceptionHandler({RetrieveProductDetailException.class})
    public ResponseEntity<ResponseDTO> handleRetrieveUser(RetrieveProductDetailException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.ERROR_RETRIEVE_PRODUCT_DETAIL);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(messageResponse);
    }

    @ExceptionHandler({RetrieveSingleCategoryException.class})
    public ResponseEntity<ResponseDTO> handleRetrieveSingleCategory(RetrieveSingleCategoryException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.ERROR_RETRIEVE_SINGLE_CATEGORY);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(messageResponse);
    }
    @ExceptionHandler({InvalidOldPasswordException.class})
    public ResponseEntity<ResponseDTO> handleInvalidOldPassword(InvalidOldPasswordException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.INVALID_OLD_PASSWORD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(messageResponse);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ResponseDTO> handleUserNotFound(UserNotFoundException e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), ErrorCode.ERROR_USER_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(messageResponse);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseDTO> handleException(Exception e){
        logger.error(e.getMessage());
        messageResponse = new ResponseDTO(new Date(), "UNKNOWN_ERROR");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(messageResponse);
    }
}
