package com.code.safechain.interfaces;

import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.ui.transaction.bean.TransactionBuyRsBean;

import okhttp3.ResponseBody;

/**
 * @Auther: hchen
 * @Date: 2020/7/7 0007
 * @Description:
 */
public interface TransactionBuyConstract {
    interface View extends IBaseView{
        void buyChainReturn(TransactionBuyRsBean transactionBuyRsBean);//买币的返回
    }

    interface Presenter extends IBasePresenter<View>{
        void buyChain(String json);//买币
    }
}
