package com.code.safechain.presenter;

import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.MySaleChainConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.ui.transaction.bean.MySaleOrderRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;
import com.code.safechain.utils.RxUtils;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.code.safechain.interfaces.LoginConstract.Presenter;
import static com.code.safechain.interfaces.LoginConstract.View;

/**
 * @Auther: hchen
 * @Date: 2020/7/7 0007
 * @Description:
 */
public class MySaleChainPresenter extends BasePresenter<MySaleChainConstract.View> implements MySaleChainConstract.Presenter {
    private Disposable mDisposable;

    //得到我的资产
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
                        String s = "";
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        addSubscribe(mDisposable);
    }

    //我要卖币
    @Override
    public void saleChain(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        //
        HttpManager.getInstance().getApiServer().mySaleChain(body)
                .compose(RxUtils.<MySaleOrderRsBean>changeScheduler())
                .subscribe(new Observer<MySaleOrderRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(MySaleOrderRsBean mySaleOrderRsBean) {
                        mView.saleChainReturn(mySaleOrderRsBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        String s = e.getMessage();
                        String i = "";
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        addSubscribe(mDisposable);
    }
}
