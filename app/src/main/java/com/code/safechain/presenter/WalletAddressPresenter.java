package com.code.safechain.presenter;

import android.util.Log;

import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.WalletAddressConstract;
import com.code.safechain.interfaces.WalletConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.ui.wallet.bean.WalletAddressRsBean;
import com.code.safechain.utils.RxUtils;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @Auther: hchen
 * @Date: 2020/7/9 0009
 * @Description:
 */
public class WalletAddressPresenter extends BasePresenter<WalletAddressConstract.View> implements WalletAddressConstract.Presenter {
    private Disposable mDisposable;
    @Override
    public void getWalletAddress(HashMap<String,Object> map) {
        HttpManager.getInstance().getApiServer().getWalletAddress(map)
                .compose(RxUtils.<WalletAddressRsBean>changeScheduler())
                .subscribe(new Observer<WalletAddressRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(WalletAddressRsBean walletAddressRsBean) {
                        mView.getWalletAddressReturn(walletAddressRsBean);
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
