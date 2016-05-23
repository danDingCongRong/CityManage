package cn.edu.bupt.citymanageapp.util;

import android.util.Log;

/**
 * Created by chenjun14 on 16/5/18.
 */
public class MLog {

    private static String TARGET = "cityManager";

    private static boolean debug = true;

    public static void d(String target, String msg) {
        if (debug) {
            Log.d(target, msg);
        }
    }

    public static void d(String msg) {
        if (debug) {
            Log.d(TARGET, msg);
        }
    }

    public static void e(String target, String msg) {
        if (debug) {
            Log.d(target, msg);
        }
    }

    public static void e(String msg) {
        if (debug) {
            Log.d(TARGET, msg);
        }
    }

    public static void w(String target, String msg) {
        if (debug) {
            Log.d(target, msg);
        }
    }

    public static void w(String msg) {
        if (debug) {
            Log.d(TARGET, msg);
        }
    }

    public static void v(String target, String msg) {
        if (debug) {
            Log.d(target, msg);
        }
    }

    public static void v(String msg) {
        if (debug) {
            Log.d(TARGET, msg);
        }
    }

    public static void i(String target, String msg) {
        if (debug) {
            Log.i(target, msg);
        }
    }

    public static void i(String msg) {
        if (debug) {
            Log.d(TARGET, msg);
        }
    }

}
