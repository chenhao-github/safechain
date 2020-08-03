package com.code.safechain.interfaces;

import com.code.safechain.ui.wallet.bean.TransferRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;

import java.util.HashMap;

/**
 * @Auther: hchen
 * @Date: 2020/7/9 0009
 * @Description:
 */
public interface WalletTransferConstract {
    interface View extends IBaseView{
        void transferReturn(TransferRsBean transferRsBean);
    }

    interface Presenter extends IBasePresenter<View>{
        void transfer(String json);
    }
}
