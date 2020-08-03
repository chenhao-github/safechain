package com.code.safechain.ui.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.WalletConstract;
import com.code.safechain.interfaces.WalletHomeConstract;
import com.code.safechain.presenter.WalletHomePresenter;
import com.code.safechain.presenter.WalletPresenter;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChainInfoActivity extends BaseActivity<WalletHomeConstract.Presenter> implements WalletHomeConstract.View {
    @BindView(R.id.txt_title)
    TextView mTxtTitle;


    @Override
    protected int getLayout() {
        return R.layout.activity_chain_info;
    }

    @Override
    protected WalletHomeConstract.Presenter createPresenter() {
        return new WalletHomePresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type",2);//表示币详情
        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        map.put("token_id", getIntent().getIntExtra(Constants.TOKEN_ID,0));
        map = SystemUtils.getMap(map);
        presenter.getWalletHome(map);
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

    @Override
    public void getWalletHomeReturn(WalletHomeRsBean walletHomeRsBean) {
        String s = "";
    }
}
