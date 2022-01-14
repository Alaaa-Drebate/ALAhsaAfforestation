package com.example.alahsaafforestation.api;


public class Constants {

    public static final int NORMAL_USER = 1;
    public static final int SELLER = 3;
    public static final int VOLUNTEER = 2;
    public static final int ADMIN = 4;

    public static final int VOLUNTEER_AVAILABLE = 1;
    public static final int VOLUNTEER_UNAVAILABLE = 0;

    public static final String BASE_URL = "trees-api/index.php";
    public static final String LOGIN_URL = "trees-api/index.php?type=login";
    public static final String REGISTER_URL = "trees-api/index.php?type=register";
    public static final String UPDATE_USER_DATA = "trees-api/index.php?type=update-user";

    public static final String SERVICES_BY_VOLUNTEER = "trees-api/index.php?type=get_services_by_volunteer_id";

    public static final String PHARMACIES_ALL_URL = "trees-api/index.php?type=get_acriculture_pharmacies";

    public static final String PRODUCTS_ALL_URL = "trees-api/index.php?type=get_products";
    public static final String PRODUCTS_BY_SELLER_ID = "trees-api/index.php?type=get_products_by_seller_id";

    public static final String GET_MESSAGES_WITH = "trees-api/index.php?type=get_messages_with";

    public static final String GET_MESSAGES_LIST = "trees-api/index.php?type=get-messages-list";

    public static final String GET_ALL_VOLUNTEERS = "trees-api/index.php?type=get_volunteers";

    public static final String RESET_PASSWORD = "index.php?type=reset_password";
    





}
