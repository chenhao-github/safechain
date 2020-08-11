package com.code.safechain.interfaces;

import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.ui.transaction.bean.MySaleOrderRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;

import java.util.HashMap;

/**
 * @Auther: hchen
 * @Date: 2020/7/7 0007
 * @Description:
 */
public interface MySaleChainConstract {
    interface View extends IBaseView{
        void getWalletHomeReturn(WalletHomeRsBean walletHomeRsBean);//得到资产的返回
        void saleChainReturn(MySaleOrderRsBean mySaleOrderRsBean);//卖币的返回
    }

    interface Presenter extends IBasePresenter<View>{
        void getWalletHome(HashMap<String, Object> map);
        void saleChain(String json);//卖币
    }
}
