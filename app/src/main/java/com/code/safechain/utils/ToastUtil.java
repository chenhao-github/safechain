package com.code.safechain.utils;

import android.widget.Toast;

import com.code.safechain.app.BaseApp;
public class ToastUtil {
    public static void showShort(String msg){
        Toast.makeText(BaseApp.baseApp,msg,Toast.LENGTH_SHORT).show();
    }
    public static void showLong(String msg){
        Toast.makeText(BaseApp.baseApp,msg,Toast.LENGTH_LONG).show();
    }
}
