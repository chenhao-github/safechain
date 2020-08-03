package com.code.safechain.interfaces;

import com.code.safechain.ui.wallet.bean.ChainTransactionRsBean;
import java.util.HashMap;

/**
 * @Auther: hchen
 * @Date: 2020/7/9 0009
 * @Description:
 */
public interface WalletChainTranConstract {
    interface View extends IBaseView{
        void getWalletChainTranReturn(ChainTransactionRsBean chainTransactionRsBean);
    }

    interface Presenter extends IBasePresenter<View>{
        void getWalletChainTran(HashMap<String, Object> map);
    }
}
