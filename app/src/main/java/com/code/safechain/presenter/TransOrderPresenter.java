package com.code.safechain.presenter;

import android.util.Log;

import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.TranSaleOrderConstract;
import com.code.safechain.interfaces.TransactionOrdersConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.transaction.bean.OrderRsBean;
import com.code.safechain.ui.transaction.bean.OthersSaleOrderRsBean;
import com.code.safechain.utils.RxUtils;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @Auther: hchen
 * @Date: 2020/7/9 0009
 * @Description:
 */
public class TransOrderPresenter extends BasePresenter<TransactionOrdersConstract.View> implements TransactionOrdersConstract.Presenter {
    private Disposable mDisposable;
    @Override
    public void getOrders(HashMap<String,Object> map) {
        HttpManager.getInstance().getApiServer().getOrders(map)
                .compose(RxUtils.<OrderRsBean>changeScheduler())
                .subscribe(new Observer<OrderRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(OrderRsBean orderRsBean) {
                        mView.getOrdersReturn(orderRsBean);
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
