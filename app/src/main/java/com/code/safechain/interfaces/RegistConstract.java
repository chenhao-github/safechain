package com.code.safechain.interfaces;

/**
 * @Auther: hchen
 * @Date: 2020/7/7 0007
 * @Description:
 */
public interface RegistConstract {
    interface View extends IBaseView{
        void sendVerifiCodeReturn();//发送验证码的的返回
        void sendPwdReturn();//发送密码的返回
    }

    interface Presenter extends IBasePresenter<View>{
        void sendVerifiCode(String code);//发送验证码到服务器
        void sendPwd(String pwd);//发送密码
    }
}
