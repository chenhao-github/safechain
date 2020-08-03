package com.code.safechain.interfaces;

import com.code.safechain.ui.wallet.bean.WalletAddressRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;

import java.util.HashMap;

import okhttp3.ResponseBody;

/**
 * @Auther: hchen
 * @Date: 2020/7/9 0009
 * @Description:
 */
public interface WalletHomeConstract {
    interface View extends IBaseView{
        void getWalletHomeReturn(WalletHomeRsBean walletHomeRsBean);
    }

    interface Presenter extends IBasePresenter<View>{
        void getWalletHome(HashMap<String, Object> map);
    }
}
