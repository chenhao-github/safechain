package com.code.safechain.ui.transaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.interfaces.TransactionConstract;
import com.code.safechain.presenter.TransactionPresenter;
import com.code.safechain.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity<TransactionConstract.Presenter>
        implements TransactionConstract.View {

    @BindView(R.id.img_payment)
    ImageView imgPayment;
    @BindView(R.id.btn_cancel_order)
    Button btnCancelOrder;
    @BindView(R.id.btn_to_pay)
    Button btnToPay;

    @Override
    protected int getLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected TransactionConstract.Presenter createPresenter() {
        return new TransactionPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_back, R.id.img_payment, R.id.btn_cancel_order, R.id.btn_to_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_payment:
                break;
            case R.id.btn_cancel_order:
                ToastUtil.showShort("取消订单");
                break;
            case R.id.btn_to_pay:
                ToastUtil.showShort("去付款");
                break;
        }
    }
}
