package com.juliovaz.condio.util;

/**
 * Created by julio on 07/05/16.
 */
public abstract class ConstantsCondio {

    public static final String ERROR = "--- ERROR ---";
    public static final String INFO = "--- INFO ---";

    //JSON BASE TAGS
    public static final String APPLICATION_JSON = "application/json";
    public static final String ACCEPT = "Accept";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";

    //USER
    public static final String TAG_USER = "user";
    public static final String TAG_USER_USERNAME = "username";
    public static final String TAG_USER_EMAIL = "email";
    public static final String TAG_USER_PASSWORD = "password";

    //BUILDING_MESSAGE
    public static final String TAG_BUILDING_MESSAGE = "building_message";
    public static final String TAG_BUILDING_MESSAGE_USER_ID = "user_id";
    public static final String TAG_BUILDING_MESSAGE_DESCRIPTION = "message_description";

    //DEVICE
    public static final String TAG_DEVICE = "device";
    public static final String TAG_DEVICE_NAME = "name";
    public static final String TAG_DEVICE_TOKEN = "token";

    //RESERVATION
    public static final String TAG_RESERVATION = "reservation";
    public static final String TAG_RESERVATION_USER= "user_id";
    public static final String TAG_RESERVATION_LOCATION= "building_location_id";
    public static final String TAG_RESERVATION_ID= "reservation_id";
    public static final String TAG_RESERVATION_STATUS = "status";


    public static final String TAG_RESERVATION_EVENT_DATE = "event_date";

    public static final int VIEW_PAGER_RESERVATIONS_POSITION = 0;
    public static final int VIEW_PAGER_BOARD_MESSAGE_POSITION = 1;
    public static final int VIEW_PAGER_SERVICES_POSITION = 2;


    public static final String RESERVATION_ID = "RESERVATION_ID";
    public static final String BUILDING_LOCATION_ID = "BUILDING_LOCATION_ID";
    public static final String RESERVATION_HISTORY_FLAG = "RESERVATION_HISTORY_FLAG";
}
