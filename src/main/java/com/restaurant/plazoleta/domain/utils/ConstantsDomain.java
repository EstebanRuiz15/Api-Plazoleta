package com.restaurant.plazoleta.domain.utils;

public final class ConstantsDomain {
    private ConstantsDomain(){
        throw new IllegalStateException("Utility class");
    }
    public static final String ERROR_VALIDATION="Error of validation";
    public static final String NAME_EMPTY = "Name cannot be empty.";
    public static final String NAME_INVALID = "Name can only contain letters.";

    public static final String LAST_NAME_EMPTY = "Last name cannot be empty.";
    public static final String LAST_NAME_INVALID = "Last name can only contain letters.";

    public static final String DOCUMENT_EMPTY = "Document number cannot be empty.";
    public static final String DOCUMENT_INVALID = "Document number must be positive.";

    public static final String CEL_PHONE_EMPTY = "Cell phone cannot be empty.";
    public static final String CEL_PHONE_INVALID = "Phone number must start with an optional '+' and contain only numbers (maximum 13 characters).";

    public static final String BIRTHDAY_EMPTY = "Birthdate cannot be empty.";
    public static final String BIRTHDAY_INVALID = "Birthdate cannot be in the future.";

    public static final String EMAIL_EMPTY = "Email cannot be empty.";
    public static final String EMAIL_INVALID = "Email should be valid.";

    public static final String PASSWORD_EMPTY = "Password cannot be empty.";
    public static final String PASSWORD_INVALID = "The password must have at least 8 characters, a capital letter and a number";
    public static final String ONLY_LETTERS= "^[A-Za-z]+$";
    public static final String CEL_PHONE_REGEX = "^\\+?\\d{1,13}$";
    public static final Integer EIGHT = 8;
    public static final String REGEX_PASSWORD="^(?=.*[A-Z])(?=.*\\d).{8,}$";
    public static final String ERROR_MESSAGE_BIRTHDATE="Invalid date of birth";
    public static final String ERROR_USER="The user ";
    public static final String NOT_EXIST=" not exist";
    public static final String NOT_HAVE_OWNER_ROL=" not have the rol owner";
    public static final String COMUNICATION_ERROR_WITH_SERVICE="Error communicating with User service ";
    public static final String OWNER="OWNER";
}

