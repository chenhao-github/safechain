package com.code.safechain.interfaces;

/**
 * @Auther: hchen
 * @Date: 2020/7/7 0007
 * @Description:
 */
public interface LoginConstract {
    interface View extends IBaseView{
        void loginReturn();//登录后的返回
    }

    interface Presenter extends IBasePresenter<View>{
        void login(String account, String pws, String code);//登录
    }
}
