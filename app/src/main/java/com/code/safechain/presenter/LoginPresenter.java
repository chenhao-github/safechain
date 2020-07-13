package com.code.safechain.presenter;

import com.code.safechain.base.BaseActivity;
import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.LoginConstract;
import com.code.safechain.interfaces.MyConstract;

import static com.code.safechain.interfaces.LoginConstract.*;

/**
 * @Auther: hchen
 * @Date: 2020/7/7 0007
 * @Description:
 */
public class LoginPresenter extends BasePresenter<LoginConstract.View> implements LoginConstract.Presenter {

    @Override
    public void login(String account, String pwd, String code) {

    }
}
