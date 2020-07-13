package com.code.safechain.common;
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
    String BASE_SHOP_URL = "https://cdwan.cn/";
    //传值的key
    String DATA = "data";

    String ACCOUNT = "account";
    String PASSWORD = "password";
    String VERIFICODE = "verifiCode";

}
