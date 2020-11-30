package com.code.safechain.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.app.BaseApp;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.WalletConstract;
import com.code.safechain.interfaces.WalletTransferConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.model.bean.Chain;
import com.code.safechain.presenter.WalletPresenter;
import com.code.safechain.presenter.WalletTransferPresenter;
import com.code.safechain.ui.wallet.bean.ChainInfoRsBean;
import com.code.safechain.ui.wallet.bean.TransferRsBean;
import com.code.safechain.ui.wallet.bean.WalletAddressRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;
import com.code.safechain.utils.RxUtils;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;
import com.code.safechain.zxing.activity.CaptureActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TransferActivity extends BaseActivity<WalletTransferConstract.Presenter> implements WalletTransferConstract.View {
    @BindView(R.id.txt_chain)
    TextView mTxtChain;
    @BindView(R.id.txt_chain_name)
    TextView mTxtChainName;
    @BindView(R.id.txt_balance)
    TextView mTxtBalance;
    @BindView(R.id.et_amount)
    EditText mEtAmount;
    @BindView(R.id.et_chain_address)
    EditText mEtChainAddress;//收款地址
    @BindView(R.id.txt_miner_fee)
    TextView mTxtMinerFee;

    private WalletHomeRsBean.ResultBean.DataBean mChain;
    private WalletAddressRsBean.ResultBean.DataBean mAddress;
    private HashMap<String, Object> mMap;
    private String mPaywd;

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
        mPaywd = getIntent().getStringExtra(Constants.PAYWD);//得到手势密码
        //设置转账币的名字
        mTxtChain.setText(mChain.getSymbol());
        mTxtChainName.setText(mChain.getSymbol());
        //设置余额值  0.00是传入的数据，用真实数据替换
//        mTxtBalance.setText("￥ "+String.format("%.2f",mChain.getSum()));
        mTxtBalance.setText(String.format("%.2f",Double.parseDouble(mChain.getNum())));
        //获得矿工费
        getMinerFee();
    }
    //获取币详情，中包含 旷工费 gas_limit, fee_type（币id找到币的名字）
    private void getMinerFee() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type",2);//表示币详情
        map.put("token_id", mChain.getToken_id());
        map = SystemUtils.getMap(map);
        HttpManager.getInstance().getApiServer().getChainInfo(map)
                .compose(RxUtils.<ChainInfoRsBean>changeScheduler())
                .subscribe(new Observer<ChainInfoRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ChainInfoRsBean chainInfoRsBean) {
                        setMinerFee(chainInfoRsBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //旷工费 gas_limit, fee_type（币id找到币的名字）
    private void setMinerFee(ChainInfoRsBean chainInfoRsBean) {
        String chainName = "";
        if(BaseApp.mChains != null && BaseApp.mChains.size()>0){
            for (int i = 0; i < BaseApp.mChains.size(); i++) {
                if(chainInfoRsBean.getResult().getFee_type() == BaseApp.mChains.get(i).getToken_id()){
                    chainName = BaseApp.mChains.get(i).getSymbol();
                    break;
                }
            }
        }
        mTxtMinerFee.setText("链内0矿工费，链外"+chainInfoRsBean.getResult().getGas_limit()+chainName);
    }

    @OnClick({R.id.img_back, R.id.img_scan_code, R.id.img_address_book, R.id.btn_transfer})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_scan_code:
                startActivityForResult(new Intent(this, CaptureActivity.class),200);
                break;
            case R.id.img_address_book:
                //跳转到地址本
                Intent intent = new Intent(this, AddressBookActivity.class);
                intent.putExtra(Constants.DATA,mChain);
                startActivityForResult(intent,100);
                break;
            case R.id.btn_transfer:
                transfer();//转账
                break;
        }
    }

    //转账
    private void transfer() {
        //得到转账金额和收款地址
        String amount = mEtAmount.getText().toString().trim();
        String addressData = mEtChainAddress.getText().toString().trim();
        if(TextUtils.isEmpty(amount) || TextUtils.isEmpty(addressData)){
            ToastUtil.showShort("金额和地址不能为空");
            return;
        }

        //封装数据到Map
        mMap = new HashMap<>();
        mMap.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        mMap.put("token_id", mChain.getToken_id());
        mMap.put("to_addr",addressData);
        mMap.put("amount",amount);
        mMap.put("paywd",mPaywd);//设置手势密码
        //加密
        String json = SystemUtils.getJson(mMap);
        presenter.transfer(json);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择地址本
        if(requestCode == 100 && resultCode == 100){
            WalletAddressRsBean.ResultBean.DataBean address =
                    (WalletAddressRsBean.ResultBean.DataBean) data.getSerializableExtra(Constants.DATA);
            mEtChainAddress.setText(address.getAddr());//设置地址本的收款地址
        }
        if (requestCode == 200 && resultCode == 200){
            String code = data.getStringExtra(Constants.DATA);
            mEtChainAddress.setText(code);
        }

    }

    //转账的回传
    @Override
    public void transferReturn(TransferRsBean transferRsBean) {
        if (transferRsBean.getError() == 0){
            ToastUtil.showShort(getResources().getString(R.string.wallet_transfer_success));
            setResult(100);
            finish();
        }else {
            ToastUtil.showShort(transferRsBean.getMessage());
        }
    }
}
