package com.code.safechain.interfaces;

import com.code.safechain.ui.wallet.bean.AddAddressRsBean;
import com.code.safechain.ui.wallet.bean.WalletAddressRsBean;

import java.util.HashMap;

/**
 * @Auther: hchen
 * @Date: 2020/7/9 0009
 * @Description:
 */
public interface WalletAddAddressConstract {
    interface View extends IBaseView{
        void addAddressReturn(AddAddressRsBean addAddressRsBean);
    }

    interface Presenter extends IBasePresenter<View>{
        void addAddress(String json);
    }
}
