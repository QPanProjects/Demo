package com.quanpan302.cars.util;

/**
 *
 */

public interface AppConstants {
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "30";

    int MAX_PAGE_SIZE = 50;

    // table common default values
    int DB_SELECT_LIST_BND_MIN = 1;
    int DB_SELECT_LIST_BND_MAX = 5;
    int DB_SELECT_BATCH_SIZE = 30;

    int DEFAULT_NAME_SIZE = 40;

    int DEFAULT_EMAIL_SIZE = 40;
    int DEFAULT_USERNAME_SIZE = 15;
    int DEFAULT_PASSWORD_SIZE = 100;

    // table users m:m roles table
    String DEFAULT_TABLE_NAME_USERS = "users";
    String DEFAULT_TABLE_NAME_USER_ROLESS = "user_roles";
    String DEFAULT_TABLE_NAME_ROLES = "roles";

    // table stores
    String DEFAULT_TABLE_NAME_STORES = "stores";
}
