package com.example.marwaadel.shopownertablet.utils;

import android.content.Context;

/**
 * Utility class
 */
public class Utils {
    /**
     * Format the date with SimpleDateFormat
     */

    private Context mContext = null;



    /**
     * Public constructor that takes mContext for later use
     */

    public Utils(Context con) {
        mContext = con;
    }
    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

}

