package com.code.safechain.presenter;

import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.WalletChainTranConstract;
import com.code.safechain.interfaces.WalletHomeConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.wallet.bean.ChainTransactionRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;
import com.code.safechain.utils.RxUtils;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Auther: hchen
 * @Date: 2020/7/9 0009
 * @Description:
 */
public class WalletChainTranPresenter extends BasePresenter<WalletChainTranConstract.View> implements WalletChainTranConstract.Presenter {
    private Disposable mDisposable;
    @Override
    public void getWalletChainTran(HashMap<String, Object> map) {
        HttpManager.getInstance().getApiServer().getWalletChainTrans(map)
                .compose(RxUtils.<ChainTransactionRsBean>changeScheduler())
                .subscribe(new Observer<ChainTransactionRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(ChainTransactionRsBean chainTransactionRsBean) {
                        mView.getWalletChainTranReturn(chainTransactionRsBean);
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
