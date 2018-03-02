package com.example.marwaadel.shopownertablet.utils;

/**
 * Constants class store most important strings and paths of the app
 */
public final class Constants {

    /**
     * Constants related to locations in DatabaseReference, such as the name of the node
     * where active lists are stored (ie "activeLists")
     */
    public static final String FIREBASE_PROPERTY_LIST_NAME = "OfferList";

    /**
     * Constants for DatabaseReference object properties
     */
    public static final String KEY_PROVIDER = "PROVIDER";
    public static final String KEY_UID = "UID";
    public static final String KEY_ENCODED_EMAIL = "ENCODED_EMAIL";
    public static final String FIREBASE_PROPERTY_EMAIL = "email";
    public static final String FETCH__UID = "uid";

  //  public static String My_UID = "uid";


    /**
     * Constants for DatabaseReference URL
     */
    public static final String FIREBASE_URL = "https://forwork0.firebaseio.com/";
    public static final String FIREBASE_URL_ACTIVE_LIST = FIREBASE_URL + "/" + FIREBASE_PROPERTY_LIST_NAME;

    /**
     * Constants for bundles, extras and shared preferences keys
     */


    /**
     * Constants for DatabaseReference login
     */
    public static final String PASSWORD_PROVIDER = "password";


}

