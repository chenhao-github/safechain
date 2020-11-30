package com.code.safechain.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.RegistConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.ui.login.bean.VerificationRsBean;
import com.code.safechain.utils.LoggerUtil;
import com.code.safechain.utils.RxUtils;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @Auther: hchen
 * @Date: 2020/7/8 0008
 * @Description:
 */
public class RegistPresenter extends BasePresenter<RegistConstract.View> implements RegistConstract.Presenter {

    private Disposable mDisposable;
    //获取验证码
    @Override
    public void sendVerifiCode(String json) {
        RequestBody body = null;
        MediaType parse = MediaType.parse("application/json; charset=utf-8");
        body = RequestBody.create(parse,json);
        //获取验证码
        HttpManager.getInstance().getApiServer().getVerificationCode(body)
            .compose(RxUtils.<VerificationRsBean>changeScheduler())
            .subscribe(new Observer<VerificationRsBean>() {
                @Override
                public void onSubscribe(Disposable d) {
                    mDisposable=d;
                }

                @Override
                public void onNext(VerificationRsBean verificationRsBean) {
                    LoggerUtil.logI("111",verificationRsBean.toString());
                }

                @Override
                public void onError(Throwable e) {
                    String s = "";
                }

                @Override
                public void onComplete() {

                }
            });

        addSubscribe(mDisposable);
    }

    //注册 发送密码
    @Override
    public void sendPwd(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        HttpManager.getInstance().getApiServer().regist(body)
                .compose(RxUtils.<RegistRsBean>changeScheduler())
                .subscribe(new Observer<RegistRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(RegistRsBean registRsBean) {
                        mView.sendPwdReturn(registRsBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        String str1 = e.getMessage();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        addSubscribe(mDisposable);
    }
}
