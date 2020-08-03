package com.code.safechain.interfaces;

import com.code.safechain.ui.login.bean.RegistRsBean;

/**
 * @Auther: hchen
 * @Date: 2020/7/7 0007
 * @Description:
 */
public interface LoginConstract {
    interface View extends IBaseView{
        void loginReturn(RegistRsBean registRsBean);//登录后的返回
    }

    interface Presenter extends IBasePresenter<View>{
        void login(String json);//登录
    }
}
