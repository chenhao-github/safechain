package com.code.safechain.common;
import com.code.safechain.R;
import com.code.safechain.app.BaseApp;
import java.io.File;

/**
 * 常量库
 */
public interface Constants {
    //是否为debug状态,正式上线版本需要改为false
    boolean isDebug = true;

    String PATH_DATA = BaseApp.baseApp.getCacheDir().getAbsolutePath() + File.separator + "data";
    //网络缓存地址
    String PATH_CACHE = PATH_DATA + "/safechain";
    //钱包APP的基础地址
    String BASE_SHOP_URL = "http://13.251.156.240";
    //signKey
    String SIGNKEY = "482ee7c37b7313c66c38f962ea729b62";
    //传值的key
    String DATA = "data";
    String PHONE_NUMBER = "phonenumber";
    String VERIFICODE = "verifiCode";
    String ACCOUNT = "account";
    String PASSWORD = "password";
    String TOKEN = "token";
    String TOKEN_ID = "token_id";
    //转账类型
    String[] TRANSACTIONS = {"【转入】","【转出】","【失败】"};
    //转账类型的颜色
    int[] TRANSACTIONS_COLOR = {R.color.colorGreenInto, R.color.colorBlueOut, R.color.colorRedError};

    //窗口透明度常量
    float SHADOW = 0.2f;//阴影
    float NO_SHADOW = 1f;//无阴影
}
