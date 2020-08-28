package com.code.safechain.presenter;

import android.util.Log;

import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.LoginConstract;
import com.code.safechain.interfaces.TransactionBuyConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.ui.transaction.bean.TransactionBuyRsBean;
import com.code.safechain.utils.RxUtils;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static com.code.safechain.interfaces.LoginConstract.Presenter;
import static com.code.safechain.interfaces.LoginConstract.View;

/**
 * @Auther: hchen
 * @Date: 2020/7/7 0007
 * @Description:
 */
public class TransactionBuyPresenter extends BasePresenter<TransactionBuyConstract.View> implements TransactionBuyConstract.Presenter {
    private Disposable mDisposable;
    @Override
    public void buyChain(String json) {
        RequestBody body = null;
        MediaType parse = MediaType.parse("application/json; charset=utf-8");
        body = RequestBody.create(parse,json);
        //
        HttpManager.getInstance().getApiServer().buyChain(body)
                .compose(RxUtils.<TransactionBuyRsBean>changeScheduler())
                .subscribe(new Observer<TransactionBuyRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(TransactionBuyRsBean transactionBuyRsBean) {
                        mView.buyChainReturn(transactionBuyRsBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        String str = e.getMessage();
                        String s = "";
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        addSubscribe(mDisposable);
    }
}
