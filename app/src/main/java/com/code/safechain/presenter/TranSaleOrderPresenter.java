package com.code.safechain.presenter;

import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.TranSaleOrderConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.transaction.bean.OthersSaleOrderRsBean;
import com.code.safechain.utils.RxUtils;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Auther: hchen
 * @Date: 2020/7/9 0009
 * @Description:
 */
public class TranSaleOrderPresenter extends BasePresenter<TranSaleOrderConstract.View> implements TranSaleOrderConstract.Presenter {
    private Disposable mDisposable;
    @Override
    public void getSaleOrder(HashMap<String,Object> map) {
        HttpManager.getInstance().getApiServer().getSaleOrderList(map)
                .compose(RxUtils.<OthersSaleOrderRsBean>changeScheduler())
                .subscribe(new Observer<OthersSaleOrderRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(OthersSaleOrderRsBean saleOrderRsBean) {
                        mView.getSaleOrderReturn(saleOrderRsBean);
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
