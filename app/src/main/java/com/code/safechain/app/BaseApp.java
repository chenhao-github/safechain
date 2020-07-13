package com.code.safechain.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.code.safechain.utils.LocalManageUtil;

public class BaseApp extends Application {

    public static BaseApp baseApp;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApp = this;
    }

    /**
     * 此方法早于 onCreate执行，设置app语言
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LocalManageUtil.setLocal(base);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocalManageUtil.setLocal(baseApp);
    }

}
