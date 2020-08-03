package com.code.safechain.utils;
import android.util.Log;

import com.code.safechain.common.Constants;

public class LoggerUtil {
    public static void logD(String tag,String msg){
        if (Constants.isDebug){//打印的开关
            Log.d(tag, "logD: "+msg);
        }
    }

    public static void logI(String tag,String msg){
        if (Constants.isDebug){//打印的开关
            Log.i(tag, "logD: "+msg);
        }
    }

    public static void print(String msg){
        if (Constants.isDebug){
            System.out.print(msg);
        }
    }
}
