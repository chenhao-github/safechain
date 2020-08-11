package com.code.safechain.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.code.safechain.app.BaseApp;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.security.DigestException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统工具类
 */
public class SystemUtils {

    /**
     * 检查是否有网络
     * @return
     */
    public static boolean checkNetWork(){
        ConnectivityManager manager = (ConnectivityManager) BaseApp.baseApp.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null;
    }

    /**
     * 当前是否是wifi链接
     * @return
     */
    public static boolean isWifiConnected(){
        ConnectivityManager manager = (ConnectivityManager) BaseApp.baseApp.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return info != null;
    }

    /**
     * 检查手机（4,3,2）G是否链接
     */
    public static boolean isMobileNetworkConnected(){
        ConnectivityManager manager = (ConnectivityManager) BaseApp.baseApp.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return info != null;
    }

    public static long getSystemTime(){
        return System.currentTimeMillis();
    }

    /**
     * 获取屏幕的dpi
     * @param at
     * @return
     */
    public static int getDpi(Activity at){
        DisplayMetrics dm = new DisplayMetrics();
        at.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }


    /**
     * 获取包名
     * @param context
     * @return
     */
    public static String getPgName(Context context){
        return context.getPackageName();
    }

    /**
     * 获取版本号
     * @param context
     * @return
     */
    public static Long getVersionCode(Context context, String pg){
        PackageInfo pgInfo = null;
        try {
            pgInfo = context.getPackageManager().getPackageInfo(pg,0);
            return Long.valueOf(pgInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            LoggerUtil.logD("VersionInfo", e.getMessage());
        }
        return null;
    }

    /**
     * 关闭软键盘
     *
     * @param activity
     */
    public static void closeKeyBoard(Activity activity) {
        InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(activity.getWindow().getDecorView().getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha  屏幕透明度0.0-1.0 1表示完全不透明
     *  window表示本窗口，Activity中获取方法是getWindow()  fragment中是getActivity().getWindow()
     */
    public static void setBackgroundAlpha(Window window, float bgAlpha) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = bgAlpha;//设置透明度（这是窗体本身的透明度，非背景）
        window.setAttributes(lp);
    }

    //配合get请求，得到map集合
    public static HashMap<String,Object> getMap(HashMap<String, Object> map){
        map.put("timestamp",new Date().getTime());//添加日期
        try {
            String str = Sha1.SHA1(map);//排序加密后
            map.put("sign",str);//把签名后的数据，封装到map后，key是sign
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    //获得签名后的json串
    public static String getJson(Map<String,Object> map){
        map.put("timestamp",new Date().getTime());//添加日期
        try {
            String str = Sha1.SHA1(map);//排序加密后
            map.put("sign",str);//把签名后的数据，封装到map后，key是sign
            //把map转换为json
            JSONObject jsonObject = new JSONObject();
            for (Map.Entry<String,Object> entry:map.entrySet()) {
                jsonObject.put(entry.getKey(),entry.getValue());
            }
            LoggerUtil.logI("111",jsonObject.toString());
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "";
    }
}
