package com.eighteengray.commonutillibrary;

import android.util.Log;

public class LogUtils {

    private static boolean ENABLE_LOG = false;

    public LogUtils() {
    }

    public static void enableDebugMode(boolean enabled) {
        ENABLE_LOG = enabled;
    }

    public static void d(String tag, String msg) {
        if(ENABLE_LOG) {
            Log.d(tag, msg);
        }

    }

    public static void d(String tag, String msg, Throwable tr) {
        if(ENABLE_LOG) {
            Log.d(tag, msg, tr);
        }

    }

    public static void i(String tag, String msg) {
        if(ENABLE_LOG) {
            Log.i(tag, msg);
        }

    }

    public static void i(String tag, String msg, Throwable tr) {
        if(ENABLE_LOG) {
            Log.i(tag, msg, tr);
        }

    }

    public static void w(String tag, String msg) {
        if(ENABLE_LOG) {
            Log.w(tag, msg);
        }

    }

    public static void w(String tag, Throwable tr) {
        if(ENABLE_LOG) {
            Log.w(tag, tr);
        }

    }

    public static void w(String tag, String msg, Throwable tr) {
        if(ENABLE_LOG) {
            Log.w(tag, msg, tr);
        }

    }

    public static void e(String tag, String msg) {
        if(ENABLE_LOG) {
            Log.e(tag, msg);
        }

    }

    public static void e(String tag, String msg, Throwable tr) {
        if(ENABLE_LOG) {
            Log.e(tag, msg, tr);
        }

    }

    public static void p(Object obj) {
        if(ENABLE_LOG) {
            System.out.println(obj);
        }

    }

}
