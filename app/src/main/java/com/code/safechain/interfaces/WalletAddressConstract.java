package com.code.safechain.interfaces;

import com.code.safechain.ui.wallet.bean.WalletAddressRsBean;

import java.util.HashMap;

/**
 * @Auther: hchen
 * @Date: 2020/7/9 0009
 * @Description:
 */
public interface WalletAddressConstract {
    interface View extends IBaseView{
        void getWalletAddressReturn(WalletAddressRsBean walletAddressRsBean);
    }

    interface Presenter extends IBasePresenter<View>{
        void getWalletAddress(HashMap<String, Object> map);
    }
}
