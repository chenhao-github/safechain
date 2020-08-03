package com.code.safechain.presenter;

import com.code.safechain.base.BaseActivity;
import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.LoginConstract;
import com.code.safechain.interfaces.MyConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.ui.login.bean.VerificationRsBean;
import com.code.safechain.utils.LoggerUtil;
import com.code.safechain.utils.RxUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.code.safechain.interfaces.LoginConstract.*;

/**
 * @Auther: hchen
 * @Date: 2020/7/7 0007
 * @Description:
 */
public class LoginPresenter extends BasePresenter<LoginConstract.View> implements LoginConstract.Presenter {
    private Disposable mDisposable;
    @Override
    public void login(String json) {
        RequestBody body = null;
        MediaType parse = MediaType.parse("application/json; charset=utf-8");
        body = RequestBody.create(parse,json);
        //
        HttpManager.getInstance().getApiServer().login(body)
                .compose(RxUtils.<RegistRsBean>changeScheduler())
                .subscribe(new Observer<RegistRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(RegistRsBean registRsBean) {
                        mView.loginReturn(registRsBean);
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
