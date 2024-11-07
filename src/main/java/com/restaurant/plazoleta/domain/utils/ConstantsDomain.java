package com.restaurant.plazoleta.domain.utils;

public final class ConstantsDomain {
    private ConstantsDomain(){
        throw new IllegalStateException("Utility class");
    }
    public static final String ERROR_VALIDATION="Error of validation";
    public static final String NAME_EMPTY = "Name cannot be empty.";
    public static final String ERROR_USER="The user ";
    public static final String NOT_EXIST=" not exist";
    public static final String NOT_HAVE_OWNER_ROL=" not have the rol owner";
    public static final String COMUNICATION_ERROR_WITH_SERVICE="Error communicating with User service ";
    public static final String OWNER="OWNER";
    public static final String CATEGORY_NOT_FOUND="Category not found";
    public static final String Dish_NOT_FOUND="Dish not found: ";
    public static final String RESTAURANT_NOT_FOUND="Restaurant not found";
    public static final String NAME_AL_READY_EXIST="The name is already exist";
    public static final String CREAT_SUCCESSFULL="Create new successfully";
    public static final String NOT_ALL_FIELD_EMPTY="The 3 fields cannot be empty, at least one must contain to modify";
}

