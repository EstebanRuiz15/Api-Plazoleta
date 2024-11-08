package com.restaurant.plazoleta.infraestructur.util;

public final class InfraConstants {
    private InfraConstants(){
        throw new IllegalStateException("Utility class");
    }
    public static final String CREATE_OWNER_SUCCES="Create new owner Successfull";
    public static final String RESTAURANT_NAME_EMPTY = "Restaurant name cannot be empty.";
    public static final String RESTAURANT_NAME_INVALID = "Restaurant name cannot be ampty or only numbers.";
    public static final String NIT_EMPTY = "NIT cannot be null.";
    public static final String NIT_INVALID = "NIT must be a valid number.";
    public static final String ADDRESS_EMPTY = "Address cannot be empty.";
    public static final String PHONE_NUMBER_EMPTY = "Phone number cannot be empty.";
    public static final String PHONE_NUMBER_INVALID = "Phone number must start with an optional '+' and contain only numbers (maximum 13 characters).";
    public static final String LOGO_URL_EMPTY = "Logo URL cannot be empty.";
    public static final String LOGO_URL_INVALID = "Logo URL must be a valid URL.";
    public static final String USER_ID_INVALID = "Invalid user id";
    public static final String RESTAURANT_NAME_REGEX = ".*[a-zA-Z]+.*";
    public static final String PHONE_NUMBER_REGEX = "^\\+?\\d{1,13}$";
    public static final int ZERO=0;
    public static final int FIVETEEN=15;
    public static final String CREATE_RESTAURANT_SUCCES="create new restaurant successfull";
    public static final String DETAIL="Restaurant creation details";
    public static final String NAME_NOT_EMPTY = "Name must not be empty or null";
    public static final String PRICE_POSITIVE_INTEGER = "Price must be a positive integer";
    public static final String DESCRIPTION_NOT_EMPTY = "Description must not be empty or null";
    public static final String IMAGE_URL_VALID = "Image URL must be a valid URL";
    public static final String RESTAURANT_POSITIVE_INTEGER = "Restaurant ID must be a positive integer";
    public static final String CATEGORY_POSITIVE_INTEGER = "Category ID must be a positive integer";
    public static final String DISH_SUCCES="Create new dish successfull";
    public static final String DIS_UPDATE_SUCCES="Modify dish successfull";
    public static final String DISH_DISABLE_SUCCES="Disbled dish successfull";
    public static final String DISH_ENABLE_SUCCES="Enable dish successfull";
    public static final String ERROR="error";
    public static final Integer UNAUTHORIZED_CODE=401;


}
