    package com.code.safechain.ui.wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.interfaces.WalletConstract;
import com.code.safechain.presenter.WalletPresenter;

    public class ChainDetailActivity extends BaseActivity<WalletConstract.Presenter> implements WalletConstract.View {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chain_detail);
//    }

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

    }
}
