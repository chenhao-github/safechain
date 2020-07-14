package com.code.safechain.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.WalletConstract;
import com.code.safechain.model.bean.Chain;
import com.code.safechain.presenter.WalletPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransferActivity extends BaseActivity<WalletConstract.Presenter> implements WalletConstract.View {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.txt_chain)
    TextView mTxtChain;
    @BindView(R.id.img_scan_code)
    ImageView mImgScanCode;
    private Chain mChain;

    @Override
    protected int getLayout() {
        return R.layout.activity_transfer;
    }

    @Override
    protected WalletConstract.Presenter createPresenter() {
        return new WalletPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //获得Chain对象
        Intent intent = getIntent();
        mChain = (Chain) intent.getSerializableExtra(Constants.DATA);
        //设置转账币的名字
        mTxtChain.setText(mChain.getName());


    }

    @OnClick({R.id.img_back, R.id.img_scan_code})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_scan_code:

                break;
        }
    }
}
