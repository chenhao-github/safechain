package com.code.safechain.interfaces;

import com.code.safechain.ui.my.bean.GestureRsBean;

/**
 * @Auther: hchen
 * @Date: 2020/7/9 0009
 * @Description:
 */
public interface GestureConstract {
    interface View extends IBaseView{
        void sendPaywdReturn(GestureRsBean gestureRsBean);
    }

    interface Presenter extends IBasePresenter<View>{
        void sendPaywd(String json);//发送支付密码（手势密码）
    }
}
