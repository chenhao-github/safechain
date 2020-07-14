package com.code.safechain.ui.wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

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
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.scanning)
    TextView scanning;
    @BindView(R.id.QRCodeBox)
    RelativeLayout QRCodeBox;
    @BindView(R.id.walletPath)
    TextView walletPath;
    @BindView(R.id.walletMsg)
    RelativeLayout walletMsg;
    @BindView(R.id.ic_share)
    ImageView icShare;
    @BindView(R.id.tx_share)
    TextView txShare;
    @BindView(R.id.share)
    LinearLayout share;
    @BindView(R.id.ic_copy)
    ImageView icCopy;
    @BindView(R.id.tx_copy)
    TextView txCopy;
    @BindView(R.id.copy)
    LinearLayout copy;
    @BindView(R.id.ic_setMoney)
    ImageView icSetMoney;
    @BindView(R.id.tx_setMoney)
    TextView txSetMoney;
    @BindView(R.id.setMoney)
    LinearLayout setMoney;
    @BindView(R.id.qr_all)
    ConstraintLayout qrAll;
    @BindView(R.id.appLog)
    ImageView appLog;

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

    @OnClick({R.id.img_back,R.id.share, R.id.copy, R.id.setMoney})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.share:
                //点击分享显示黑窗 和 小二维码两个按钮 分享和 取消

                break;
            case R.id.copy:
                Toast.makeText(this, "复制", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setMoney:
                Toast.makeText(this, "设置金额", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
