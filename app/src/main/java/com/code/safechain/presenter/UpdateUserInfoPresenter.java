package com.code.safechain.presenter;

import android.annotation.SuppressLint;

import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.GestureConstract;
import com.code.safechain.interfaces.UpdateUserInfoConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.my.bean.GestureRsBean;
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
public class UpdateUserInfoPresenter extends BasePresenter<UpdateUserInfoConstract.View>
        implements UpdateUserInfoConstract.Presenter {

    private Disposable mDisposable;
    @SuppressLint("CheckResult")
    @Override
    public void updateUserInfo(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
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
                    mView.updateUserInfoReturn(gestureRsBean);
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
