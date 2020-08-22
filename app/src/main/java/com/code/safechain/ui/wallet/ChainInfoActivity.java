package com.code.safechain.ui.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.WalletConstract;
import com.code.safechain.interfaces.WalletHomeConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.presenter.WalletHomePresenter;
import com.code.safechain.presenter.WalletPresenter;
import com.code.safechain.ui.login.bean.CountryCodeBean;
import com.code.safechain.ui.wallet.bean.ChainInfoRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;
import com.code.safechain.utils.RxUtils;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ChainInfoActivity extends BaseActivity<WalletHomeConstract.Presenter> implements WalletHomeConstract.View {
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.txt_chain_name)
    TextView mTxtChainName;
    @BindView(R.id.img_chain_icon)
    ImageView mImgChainIcon;
    @BindView(R.id.txt_issue_date)
    TextView mTxtIssueDate;
    @BindView(R.id.txt_issue_cost)
    TextView mTxtIssueCost;


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

        getChainInfo();//获得币详情
    }

    private void getChainInfo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type",2);//表示币详情
//        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        map.put("token_id", getIntent().getIntExtra(Constants.TOKEN_ID,0));
        map = SystemUtils.getMap(map);
        HttpManager.getInstance().getApiServer().getChainInfo(map)
                .compose(RxUtils.<ChainInfoRsBean>changeScheduler())
                .subscribe(new Observer<ChainInfoRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ChainInfoRsBean chainInfoRsBean) {
                        setChainInfo(chainInfoRsBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setChainInfo(ChainInfoRsBean chainInfoRsBean) {
        ChainInfoRsBean.ResultBean result = chainInfoRsBean.getResult();
        mTxtTitle.setText(result.getSymbol());
        Glide.with(this).load(result.getLogo_url()).into(mImgChainIcon);
        mTxtChainName.setText(result.getSymbol());
        mTxtIssueDate.setText(result.getCreated_at().substring(0,10));
//        mTxtIssueCost.setText(String.format("%.5f",Double.parseDouble(result.getPrice_cny())));
        mTxtIssueCost.setText(result.getPrice_cny());

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
