package com.code.safechain.interfaces;

import com.code.safechain.ui.login.bean.RegistRsBean;

/**
 * @Auther: hchen
 * @Date: 2020/7/7 0007
 * @Description:
 */
public interface RegistConstract {
    interface View extends IBaseView{
        void sendVerifiCodeReturn();//发送验证码的的返回
        void sendPwdReturn(RegistRsBean registRsBean);//发送密码的返回
    }

    interface Presenter extends IBasePresenter<View>{
        //号码类型，电话号码，国家代码，电子邮箱
        void sendVerifiCode(String json);//发送验证码到服务器
        void sendPwd(String json);//发送密码等，进行登录
    }
}
