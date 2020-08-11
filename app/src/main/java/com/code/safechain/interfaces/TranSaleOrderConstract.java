package com.code.safechain.interfaces;

import com.code.safechain.ui.transaction.bean.OthersSaleOrderRsBean;

import java.util.HashMap;

/**
 * @Auther: hchen
 * @Date: 2020/7/9 0009
 * @Description:
 */
public interface TranSaleOrderConstract {
    interface View extends IBaseView{
        void getSaleOrderReturn(OthersSaleOrderRsBean saleOrderBean);
    }

    interface Presenter extends IBasePresenter<View>{
        void getSaleOrder(HashMap<String, Object> map);
    }
}
