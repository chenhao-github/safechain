package com.code.safechain.ui.transaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.interfaces.TransactionConstract;
import com.code.safechain.presenter.TransactionPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentActivity extends BaseActivity<TransactionConstract.Presenter> implements TransactionConstract.View {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.img_payment)
    ImageView mImgPayment;
    @BindView(R.id.img_payment2)
    ImageView mImgPayment2;
    @BindView(R.id.img_payment3)
    ImageView mImgPayment3;
    @BindView(R.id.btn_successful_payment)
    Button mBtnSuccessfulPayment;

    @Override
    protected int getLayout() {
        return R.layout.activity_payment;
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

    @OnClick({R.id.img_back, R.id.img_payment, R.id.img_payment2, R.id.img_payment3, R.id.btn_successful_payment})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_payment:

                break;
            case R.id.img_payment2:

                break;
            case R.id.img_payment3:

                break;
            case R.id.btn_successful_payment:

                break;
        }
    }
}
