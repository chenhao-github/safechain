package com.code.safechain.presenter;

import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.WalletAddAddressConstract;
import com.code.safechain.interfaces.WalletAddressConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.wallet.bean.AddAddressRsBean;
import com.code.safechain.ui.wallet.bean.WalletAddressRsBean;
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
public class WalletAddAddressPresenter extends BasePresenter<WalletAddAddressConstract.View> implements WalletAddAddressConstract.Presenter {
    private Disposable mDisposable;
    @Override
    public void addAddress(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        HttpManager.getInstance().getApiServer().addAddress(body)
                .compose(RxUtils.<AddAddressRsBean>changeScheduler())
                .subscribe(new Observer<AddAddressRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(AddAddressRsBean addAddressRsBean) {
                        mView.addAddressReturn(addAddressRsBean);
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
