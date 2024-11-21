package com.jjcl.myaspectj.utils;


import android.os.Process;
import android.util.Log;


/**
 * Created by Administrator on 2017/11/27 0027.
 */

public class JDebug {
    private static boolean isDebugForLogcat = true;

    public static boolean useSerial = true; //for debug串口在设备无法使用，
    private static boolean isInit = false;

    private static void initLogger() {
       /* if(!isInit) {
            LogHelper.InitalLogger();
            isInit = true;*//*
            logger = Logger.getLogger(MyApp.class);
*/
//        }
    }

    public static void print(String log) {
        if (isDebugForLogcat) {
            Log.e("jasd", log + " pid=" + Process.myTid());
        }

    }

    public static void printT(String tag, String log) {
        if (isDebugForLogcat) {
            Log.e(tag, log + " pid=" + Process.myTid());
        }

    }
}
