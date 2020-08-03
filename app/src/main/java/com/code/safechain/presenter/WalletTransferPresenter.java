package com.code.safechain.presenter;

import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.WalletHomeConstract;
import com.code.safechain.interfaces.WalletTransferConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.wallet.bean.TransferRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;
import com.code.safechain.utils.RxUtils;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @Auther: hchen
 * @Date: 2020/7/9 0009
 * @Description:
 */
public class WalletTransferPresenter extends BasePresenter<WalletTransferConstract.View> implements WalletTransferConstract.Presenter {
    private Disposable mDisposable;
    @Override
    public void transfer(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        HttpManager.getInstance().getApiServer().transfer(body)
                .compose(RxUtils.<TransferRsBean>changeScheduler())
                .subscribe(new Observer<TransferRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(TransferRsBean transferRsBean) {
                        mView.transferReturn(transferRsBean);
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
}
