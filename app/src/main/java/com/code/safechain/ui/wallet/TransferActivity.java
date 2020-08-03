package com.code.safechain.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.WalletConstract;
import com.code.safechain.interfaces.WalletTransferConstract;
import com.code.safechain.model.bean.Chain;
import com.code.safechain.presenter.WalletPresenter;
import com.code.safechain.presenter.WalletTransferPresenter;
import com.code.safechain.ui.wallet.bean.TransferRsBean;
import com.code.safechain.ui.wallet.bean.WalletAddressRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;
import com.code.safechain.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransferActivity extends BaseActivity<WalletTransferConstract.Presenter> implements WalletTransferConstract.View {

    @BindView(R.id.txt_chain)
    TextView mTxtChain;
    @BindView(R.id.txt_chain_name)
    TextView mTxtChainName;
    @BindView(R.id.txt_balance)
    TextView mTxtBalance;
    @BindView(R.id.et_chain_address)
    EditText mEtChainAddress;//收款地址

    private WalletHomeRsBean.ResultBean.DataBean mChain;

    @Override
    protected int getLayout() {
        return R.layout.activity_transfer;
    }

    @Override
    protected WalletTransferConstract.Presenter createPresenter() {
        return new WalletTransferPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //获得Chain对象
        Intent intent = getIntent();
        mChain = (WalletHomeRsBean.ResultBean.DataBean)intent.getSerializableExtra(Constants.DATA);
        //设置转账币的名字
        mTxtChain.setText(mChain.getSymbol());
        mTxtChainName.setText(mChain.getSymbol());
        //设置余额值  0.00是传入的数据，用真实数据替换
        mTxtBalance.setText(String.format("%.2f",Double.parseDouble(mChain.getNum())));


    }

    @OnClick({R.id.img_back, R.id.img_scan_code, R.id.img_address_book})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_scan_code:

                break;
            case R.id.img_address_book:
                //跳转到地址本
                Intent intent = new Intent(this, AddressBookActivity.class);
                intent.putExtra(Constants.DATA,mChain);
                startActivityForResult(intent,100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 100){
            WalletAddressRsBean.ResultBean.DataBean address =
                    (WalletAddressRsBean.ResultBean.DataBean) data.getSerializableExtra(Constants.DATA);
            mEtChainAddress.setText(address.getAddr());//设置地址本的收款地址
        }
    }

    //转账的回传
    @Override
    public void transferReturn(TransferRsBean transferRsBean) {

    }
}
