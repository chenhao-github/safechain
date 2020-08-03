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
import com.code.safechain.interfaces.TransactionConstract;
import com.code.safechain.model.bean.OrderBean;
import com.code.safechain.presenter.TransactionPresenter;
import com.code.safechain.ui.transaction.adapter.OrderAdapter;
import com.code.safechain.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends BaseActivity<TransactionConstract.Presenter>
        implements TransactionConstract.View, BaseAdapter.OnItemClickListener{
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
    private ArrayList<OrderBean> mOrderBeans;
    private OrderAdapter mOrderAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_order;
    }

    @Override
    protected TransactionConstract.Presenter createPresenter() {
        return new TransactionPresenter();
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
       mOrderBeans.add(new OrderBean("HKDT","43.22HKDT","￥8.45","20201983082411111","【已取消】","￥8.17"));
       mOrderBeans.add(new OrderBean("HKDT","43.22HKDT","￥8.45","20201983082411111","【已取消】","￥8.17"));
       mOrderBeans.add(new OrderBean("HKDT","43.22HKDT","￥8.45","20201983082411111","【已取消】","￥8.17"));
       mOrderBeans.add(new OrderBean("HKDT","43.22HKDT","￥8.45","20201983082411111","【已取消】","￥8.17"));
       mOrderAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.img_back, R.id.txt_all, R.id.txt_ongoing, R.id.txt_failed, R.id.txt_completed})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_all:
                switchTv(txtAll);
                break;
            case R.id.txt_ongoing:
                switchTv(txtOngoing);
                break;
            case R.id.txt_failed:
                switchTv(txtFailed);
                break;
            case R.id.txt_completed:
                switchTv(txtCompleted);
                break;
        }
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
        OrderBean position = mOrderBeans.get(holder.getAdapterPosition());
        Intent intent = new Intent(this, OrderDetailActivity.class);
        startActivity(intent);
    }
}
