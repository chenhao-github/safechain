package com.code.safechain.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.view.View;

import com.code.safechain.app.BaseApp;

import java.util.Locale;

/**
 * @Auther: hchen
 * @Date: 2020/7/4 0004
 * @Description:国际化语言切换控制工具类
 */
public class LocalManageUtil {
    /**
     * 设置语言
     * @param context
     * @return
     */
    public static Context setLocal(Context context) {
        Locale locale = getSetLanguageLocale(context);//获得设置的语言
        return updateResources(context, locale);//设置语言
    }
    /**
     * 获取选择的语言设置
     *
     * @param context
     * @return  String language = locale.getLanguage();  得到语言值 zh  en
     */
    public static Locale getSetLanguageLocale(Context context) {
        switch (SpUtils.getInstance(context).getSelectLanguage()) {
            case 0://没有设置语言，用系统默认
                return getSystemLocale(context);
            case 1:
                return Locale.CHINA;
            case 2:
                return Locale.ENGLISH;
            default:
                return Locale.CHINA;
        }
    }

    /**
     * 获取系统的locale
     * @return Locale对象
     */
    public static Locale getSystemLocale(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

    /**
     * 设置语言
     * @param context
     * @param locale
     * @return
     */
    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    /**
     * 保存选择的语言
     * @param i
     */
    public static void saveSelectLanguage(int i) {
        SpUtils.getInstance(BaseApp.baseApp).saveLanguage(i);
    }
}
