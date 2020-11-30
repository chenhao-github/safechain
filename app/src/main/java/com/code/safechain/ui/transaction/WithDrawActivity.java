package com.code.safechain.ui.transaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.TranSaleOrderConstract;
import com.code.safechain.interfaces.TransactionConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.presenter.TranSaleOrderPresenter;
import com.code.safechain.ui.transaction.adapter.BuyChainSaleAdapter;
import com.code.safechain.ui.transaction.bean.OthersSaleOrderRsBean;
import com.code.safechain.ui.transaction.bean.UpdateOrderRsBean;
import com.code.safechain.ui.transaction.bean.WithDrawRsBean;
import com.code.safechain.utils.RxUtils;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class WithDrawActivity extends BaseActivity<TranSaleOrderConstract.Presenter> implements
        TranSaleOrderConstract.View, View.OnClickListener{
    @BindView(R.id.rlv_withdraw_chain)
    RecyclerView mRlvWithDrawChain;
    private ArrayList<OthersSaleOrderRsBean.ResultBean> mChainSaleBeans;
    private BuyChainSaleAdapter mAdapter;
    private OthersSaleOrderRsBean.ResultBean mSaleOrder;
    private String mToken;
    @Override
    protected int getLayout() {
        return R.layout.activity_with_draw;
    }

    @Override
    protected TranSaleOrderConstract.Presenter createPresenter() {
        return new TranSaleOrderPresenter();
    }

    @Override
    protected void initView() {
        mToken = SpUtils.getInstance(this).getString(Constants.TOKEN);
        mRlvWithDrawChain.setLayoutManager(new LinearLayoutManager(this));
        mChainSaleBeans = new ArrayList<>();
        mAdapter = new BuyChainSaleAdapter(this, mChainSaleBeans,"withdraw");//撤销卖单
        mRlvWithDrawChain.setAdapter(mAdapter);
        mAdapter.setOnClickListener(this);//添加 条目中的组件的点击监听
    }

    @Override
    protected void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        map.put("size",Constants.SIZE);
        map.put("own",1);
//        map.put("lastid",Constants.SIZE);//最后一条数据的 商品id（主键id）,从这里开始取 size条

        map = SystemUtils.getMap(map);
        presenter.getSaleOrder(map);
    }

    @Override
    public void getSaleOrderReturn(OthersSaleOrderRsBean saleOrderBean) {
        mAdapter.updataListClearAddMore(saleOrderBean.getResult());//清空旧数据，显示最新数据
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_buy){
            //得到点击的卖单数据
            mSaleOrder = (OthersSaleOrderRsBean.ResultBean) v.getTag();
            int store_id = mSaleOrder.getStore_id();
            updateOrder(store_id);
        }
    }

    private void updateOrder(int store_id) {
        //提交
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", mToken);
        map.put("store_id",store_id);//卖家的撤销
        //加密
        String json = SystemUtils.getJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);

        HttpManager.getInstance().getApiServer().withDraw(body)
                .compose(RxUtils.<WithDrawRsBean>changeScheduler())
                .subscribe(new Observer<WithDrawRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WithDrawRsBean withDrawRsBean) {
                        if(withDrawRsBean != null)
                            ToastUtil.showShort(withDrawRsBean.getMessage());
                        if(withDrawRsBean.getError() == 0){//撤单成功，请求新数据
                            initData();//重新请求最新数据
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String m = "";
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
