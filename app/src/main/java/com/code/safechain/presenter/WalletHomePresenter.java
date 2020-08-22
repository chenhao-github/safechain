package com.code.safechain.presenter;

import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.WalletConstract;
import com.code.safechain.interfaces.WalletHomeConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.wallet.bean.WalletAddressRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;
import com.code.safechain.utils.RxUtils;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @Auther: hchen
 * @Date: 2020/7/9 0009
 * @Description:
 */
public class WalletHomePresenter extends BasePresenter<WalletHomeConstract.View> implements WalletHomeConstract.Presenter {
    private Disposable mDisposable;
    @Override
    public void getWalletHome(HashMap<String, Object> map) {
        HttpManager.getInstance().getApiServer().getWalletHome(map)
                .compose(RxUtils.<WalletHomeRsBean>changeScheduler())
                .subscribe(new Observer<WalletHomeRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(WalletHomeRsBean walletHomeRsBean) {
                        mView.getWalletHomeReturn(walletHomeRsBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        String s = e.getMessage();
                        String q = "";
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        addSubscribe(mDisposable);
    }
}
