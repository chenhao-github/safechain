package com.code.safechain.presenter;

import android.annotation.SuppressLint;

import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.GestureConstract;
import com.code.safechain.interfaces.RegistConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.ui.login.bean.VerificationRsBean;
import com.code.safechain.ui.my.bean.GestureRsBean;
import com.code.safechain.utils.LoggerUtil;
import com.code.safechain.utils.RxUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @Auther: hchen
 * @Date: 2020/7/8 0008
 * @Description:
 */
public class GesturePresenter extends BasePresenter<GestureConstract.View> implements GestureConstract.Presenter {

    private Disposable mDisposable;
    @SuppressLint("CheckResult")
    @Override
    public void sendPaywd(String json) {
        RequestBody body = null;
        MediaType parse = MediaType.parse("application/json; charset=utf-8");
        body = RequestBody.create(parse,json);
        //
        HttpManager.getInstance().getApiServer().updateUser(body)
            .compose(RxUtils.<GestureRsBean>changeScheduler())
            .subscribe(new Observer<GestureRsBean>() {
                @Override
                public void onSubscribe(Disposable d) {
                    mDisposable=d;
                }

                @Override
                public void onNext(GestureRsBean gestureRsBean) {
                    mView.sendPaywdReturn(gestureRsBean);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });

        addSubscribe(mDisposable);
    }


}
