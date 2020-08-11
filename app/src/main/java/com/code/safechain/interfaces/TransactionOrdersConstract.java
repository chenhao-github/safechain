package com.code.safechain.interfaces;

import com.code.safechain.ui.transaction.bean.OrderRsBean;
import com.code.safechain.ui.transaction.bean.TransactionBuyRsBean;

import java.util.HashMap;

import okhttp3.ResponseBody;

/**
 * @Auther: hchen
 * @Date: 2020/7/7 0007
 * @Description:
 */
public interface TransactionOrdersConstract {
    interface View extends IBaseView{
        void getOrdersReturn(OrderRsBean orderRsBean);//订单的返回
    }

    interface Presenter extends IBasePresenter<View>{
        void getOrders(HashMap<String, Object> map);//订单
    }
}
