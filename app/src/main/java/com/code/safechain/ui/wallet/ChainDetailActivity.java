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

public class ChainDetailActivity extends BaseActivity<WalletConstract.Presenter> implements WalletConstract.View {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.img_info)
    ImageView mImgInfo;

    @Override
    protected int getLayout() {
        return R.layout.activity_chain_detail;
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
        setTitle();//设置币的名字

    }

    private void setTitle() {
        Intent intent = getIntent();
        Chain chain = (Chain) intent.getSerializableExtra(Constants.DATA);
        mTxtTitle.setText(chain.getName());
    }

    @OnClick({R.id.img_back, R.id.img_info, R.id.btn_transfer, R.id.btn_collection})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_info:

                break;
            case R.id.btn_transfer://转账

                break;
            case R.id.btn_collection://收款
                Intent intent = new Intent(this, CollectionActivity.class);
                startActivity(intent);
                break;
        }
    }
}
