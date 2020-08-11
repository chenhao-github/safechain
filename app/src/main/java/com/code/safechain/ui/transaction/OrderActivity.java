package com.code.safechain.ui.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.base.BaseAdapter;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.TransactionConstract;
import com.code.safechain.interfaces.TransactionOrdersConstract;
import com.code.safechain.model.bean.OrderBean;
import com.code.safechain.presenter.TransOrderPresenter;
import com.code.safechain.presenter.TransactionPresenter;
import com.code.safechain.ui.transaction.adapter.OrderAdapter;
import com.code.safechain.ui.transaction.bean.OrderRsBean;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class OrderActivity extends BaseActivity<TransactionOrdersConstract.Presenter>
        implements TransactionOrdersConstract.View, BaseAdapter.OnItemClickListener{
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_all)
    TextView txtAll;
    @BindView(R.id.txt_ongoing)
    TextView txtOngoing;
    @BindView(R.id.txt_failed)
    TextView txtFailed;
    @BindView(R.id.txt_completed)
    TextView txtCompleted;
    //最后点击的导航
    TextView lastTv;
    @BindView(R.id.rlv_order)
    RecyclerView rlvOrder;
    private ArrayList<OrderRsBean.ResultBean> mOrderBeans;
    private OrderAdapter mOrderAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_order;
    }

    @Override
    protected TransactionOrdersConstract.Presenter createPresenter() {
        return new TransOrderPresenter();
    }

    @Override
    protected void initView() {
        lastTv = txtAll;//进入系统默认是 全部

        rlvOrder.setLayoutManager(new LinearLayoutManager(this));
        mOrderBeans = new ArrayList<>();
        mOrderAdapter = new OrderAdapter(this, mOrderBeans);
        rlvOrder.setAdapter(mOrderAdapter);
        mOrderAdapter.setOnItemClickListener(this);


    }

    @Override
    protected void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        map.put("role_type",1);//1买家  2卖家
        map = SystemUtils.getMap(map);
        presenter.getOrders(map);
    }

    @OnClick({R.id.img_back, R.id.txt_all, R.id.txt_ongoing, R.id.txt_failed, R.id.txt_completed})
    public void onViewClicked(View view) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        map.put("role_type",1);//1买家  2卖家
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_all:
                switchTv(txtAll);

                break;
            case R.id.txt_ongoing:
                switchTv(txtOngoing);

                map.put("state",1);//进行中
                break;
            case R.id.txt_failed:
                switchTv(txtFailed);
                break;
            case R.id.txt_completed:
                switchTv(txtCompleted);

                map.put("state",2);//已完成
                break;
        }

        map = SystemUtils.getMap(map);
        presenter.getOrders(map);
    }

    //切换textview导航
    private void switchTv(TextView currentTv) {
        lastTv.setTextColor(getResources().getColor(R.color.colorMyName));
        lastTv.setBackground(null);
        currentTv.setTextColor(getResources().getColor(R.color.colorWhite));
        currentTv.setBackground(getResources().getDrawable(R.drawable.bg_button_yellow));
        lastTv = currentTv;

    }

    @Override
    public void onItemClick(BaseAdapter.BaseViewHolder holder) {
        OrderRsBean.ResultBean resultBean = mOrderBeans.get(holder.getAdapterPosition());
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("order",resultBean);
        startActivity(intent);
    }

    //得到订单的回传
    @Override
    public void getOrdersReturn(OrderRsBean orderRsBean) {
        mOrderAdapter.updataListClearAddMore(orderRsBean.getResult());

    }
}
