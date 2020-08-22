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
//    String BASE_SHOP_URL = "http://13.251.156.240";
    String BASE_SHOP_URL = "http://www.safe-chain.io";
    //signKey
    String SIGNKEY = "482ee7c37b7313c66c38f962ea729b62";
    //传值的key
    String DATA = "data";
    String PHONE_NUMBER = "phonenumber";
    String VERIFICODE = "verifiCode";
    String NATION = "nation";
    String ACCOUNT = "account";
    String PASSWORD = "password";
    String TOKEN = "token";
    String TOKEN_ID = "token_id";
    String PAYWD = "paywd";
    //转账类型
    String[] TRANSACTIONS = {"",
            "【"+BaseApp.baseApp.getResources().getString(R.string.wallet_chain_into)+"】",
            "【"+BaseApp.baseApp.getResources().getString(R.string.wallet_chain_out)+"】",
            "【"+BaseApp.baseApp.getResources().getString(R.string.wallet_chain_fail)+"】"};
    //转账类型的颜色
    int[] TRANSACTIONS_COLOR = {R.color.colorGreenInto, R.color.colorBlueOut, R.color.colorRedError};

    //窗口透明度常量
    float SHADOW = 0.2f;//阴影
    float NO_SHADOW = 1f;//无阴影

    //卖单列表的单页数量
    int SIZE = 10;

    //支付方式
    String[] PAYTYPE = {"无",
            BaseApp.baseApp.getResources().getString(R.string.transaction_paytype_wechat),
            BaseApp.baseApp.getResources().getString(R.string.transaction_paytype_alipay),
            "VISA",
            BaseApp.baseApp.getResources().getString(R.string.transaction_paytype_unionpay)};

    //支付方式
    String PAY_EXIN_NAME="pay_wexin_name";//微信号
    String PAY_WEXIN_ICON="pay_wexin_icon";//微信支付图片
    String PAY_Alipay_NAME="pay_alipay_name";//支付宝号
    String PAY_Alipay_ICON="pay_alipay_icon";//支付宝支付图片
    String PAY_UNIONPAY_USER_NAME="pay_unionpay_user_name";//银联 持卡人姓名
    String PAY_UNIONPAY_BANK_NO="pay_unionpay_bank_no";//银联 银行卡号
    String PAY_UNIONPAY_BANK_NAME="pay_unionpay_bank_name";//银联 银行名
    String PAY_UNIONPAY_BANK_ADDRESS="pay_unionpay_bank_address";//银联 银行地址
    //订单状态
    String[] ORDERSTATE = {"无","【进行中】","【已完成】"};

    //倒计时 xs后重新发送
    String RESEND = BaseApp.baseApp.getResources().getString(R.string.regist_phone_resend);
    //发送验证码
    String SENDCODE = BaseApp.baseApp.getResources().getString(R.string.login_send_code);

}
