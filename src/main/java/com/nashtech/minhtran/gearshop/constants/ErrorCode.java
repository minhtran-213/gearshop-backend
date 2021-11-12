package com.nashtech.minhtran.gearshop.constants;

public final class ErrorCode {
    // BAD REQUEST ERROR CODE
    public final static String INVALID_EMAIL_FORMAT = "INVALID_EMAIL_FORMAT";
    public final static String INVALID_PASSWORD_FORMAT = "INVALID_PASSWORD_FORMAT";
    public final static String EXIST_EMAIL = "EXIST_EMAIL";
    public final static String EMPTY_BODY = "EMPTY_BODY";

    // product
    public final static String ERROR_RETRIEVE_PRODUCTS = "ERROR_RETRIEVE_PRODUCTS";
    public final static String ERROR_RETRIEVE_PRODUCT_DETAIL = "ERROR_RETRIEVE_PRODUCT_DETAIL";
    public final static String ERROR_UPDATE_PRODUCT = "ERROR_UPDATE_PRODUCT";
    public final static String ERROR_ADD_PRODUCT = "ERROR_ADD_PRODUCT";
    public final static String ERROR_DELETE_PRODUCT = "ERROR_DELETE_PRODUCT";
    public final static String ERROR_PRODUCT_DETAIL_NOT_EXIST = "ERROR_PRODUCT_DETAIL_NOT_EXIST";
    public final static String ERROR_PRODUCT_NOT_EXIST = "ERROR_PRODUCT_NOT_EXIST";
    public final static String ERROR_PRODUCT_ID_EMPTY = "ERROR_PRODUCT_ID_EMPTY";


    public final static String ERROR_RETRIEVE_CATEGORIES = "ERROR_RETRIEVE_CATEGORY";
    public final static String ERROR_RETRIEVE_SINGLE_CATEGORY = "ERROR_RETRIEVE_SINGLE_CATEGORY";
    public final static String ERROR_UPDATE_CATEGORY = "ERROR_UPDATE_CATEGORY";
    public final static String ERROR_ADD_CATEGORY = "ERROR_ADD_CATEGORY";
    public final static String ERROR_DELETE_CATEGORY = "ERROR_DELETE_CATEGORY";
    public final static String ERROR_CATEGORY_EMPTY_NAME = "ERROR_CATEGORY_EMPTY_NAME";
    public final static String ERROR_CATEGORY_NOT_EXIST = "ERROR_CATEGORY_NOT_EXIST";

    public final static String ERROR_RETRIEVE_MANUFACTURERS = "ERROR_RETRIEVE_MANUFACTURER_BY_NAME";
    public final static String ERROR_UPDATE_MANUFACTURER = "ERROR_UPDATE_MANUFACTURER";
    public final static String ERROR_ADD_MANUFACTURER = "ERROR_ADD_MANUFACTURER";
    public final static String ERROR_DELETE_MANUFACTURER = "ERROR_DELETE_MANUFACTURER";
    public final static String ERROR_MANUFACTURER_NOT_EXIST = "ERROR_MANUFACTURER_NOT_EXIST";
    public final static String ERROR_MANUFACTURER_EMPTY_NAME = "ERROR_MANUFACTURER_EMPTY_NAME";

    public static final String ERROR_RETRIEVE_USERS_ERROR = "ERROR_RETRIEVE_USERS_ERROR";
    public final static String INVALID_OLD_PASSWORD = "INVALID_OLD_PASSWORD";
    public final static String ERROR_RETRIEVE_ADDRESS_FROM_USER = "ERROR_RETRIEVE_ADDRESS_FROM_USER";
    public final static String ERROR_USER_NOT_FOUND = "ERROR_USER_NOT_FOUND";

    public static final String RETRIEVE_ADDRESSES_ERROR = "RETRIEVE_ADDRESSES_ERROR";
    public static final String RETRIEVE_ADDRESS_ERROR = "RETRIEVE_ADDRESS_ERROR";
    public static final String FORBIDDEN = "FORBIDDEN";
    public static final String WRONG_USERNAME_OR_PASSWORD = "WRONG_USERNAME_OR_PASSWORD";
    public static final String SAVE_USER_FAIL = "SAVE_USER_FAIL";
    public static final String SAVE_ADDRESS_FAIL = "SAVE_USER_FAIL";


}
