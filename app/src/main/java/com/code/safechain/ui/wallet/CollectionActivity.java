package com.code.safechain.ui.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.interfaces.WalletConstract;
import com.code.safechain.presenter.WalletPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectionActivity extends BaseActivity<WalletConstract.Presenter> implements WalletConstract.View {
    @BindView(R.id.img_back)
    ImageView mImgBack;

    @Override
    protected int getLayout() {
        return R.layout.activity_collection;
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

    }

    @OnClick(R.id.img_back)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }
}
