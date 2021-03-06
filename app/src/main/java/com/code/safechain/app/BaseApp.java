package com.code.safechain.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.code.safechain.ui.main.bean.UserBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;
import com.code.safechain.utils.LocalManageUtil;

import java.util.ArrayList;
import java.util.List;

public class BaseApp extends Application {

    public static BaseApp baseApp;
    public static UserBean userBean;
    public static List<WalletHomeRsBean.ResultBean.DataBean> mChains;

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


    public static Resources getRes(){//用来获取value下的strings.xml中的字符串资源
        return baseApp.getResources();
    }

}
