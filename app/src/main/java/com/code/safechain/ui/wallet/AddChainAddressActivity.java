package com.code.safechain.ui.wallet;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.base.BaseAdapter;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.WalletAddAddressConstract;
import com.code.safechain.interfaces.WalletConstract;
import com.code.safechain.model.bean.Chain;
import com.code.safechain.presenter.WalletAddAddressPresenter;
import com.code.safechain.presenter.WalletPresenter;
import com.code.safechain.ui.wallet.adapter.ChainNameAdapter;
import com.code.safechain.ui.wallet.bean.AddAddressRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;
import com.code.safechain.utils.DeviceIdFactory;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddChainAddressActivity extends BaseActivity<WalletAddAddressConstract.Presenter> implements
        WalletAddAddressConstract.View {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.txt_save)
    TextView mTxtSave;
    @BindView(R.id.txt_chain_name)
    TextView mTxtChainName;
    @BindView(R.id.et_chain_address)
    EditText mEtChainAddress;

    private ArrayList<Chain> mChains;
    private ChainNameAdapter mAdapter;
    private PopupWindow mPw;
    private WalletHomeRsBean.ResultBean.DataBean mChain;
    private String mName;

    @Override
    protected int getLayout() {
        return R.layout.activity_add_chain_address;
    }

    @Override
    protected WalletAddAddressConstract.Presenter createPresenter() {
        return new WalletAddAddressPresenter();
    }

    @Override
    protected void initView() {
        //得到当前币
        mChain = (WalletHomeRsBean.ResultBean.DataBean) getIntent().getSerializableExtra(Constants.DATA);
        mName = mChain.getSymbol();
        mTxtChainName.setText(mChain.getSymbol());
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_back, R.id.txt_save, R.id.img_open_chain})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_open_chain:
                showPopupwindow();
                break;
            case R.id.txt_save:
                saveAddress();
                break;
        }
    }

    /**
     * 提交新地址
     */
    private void saveAddress() {
        //币名是 mName
        //地址
        String addr = mEtChainAddress.getText().toString().trim();
        if(TextUtils.isEmpty(addr)){
            ToastUtil.showShort("地址不能为空！");
            return;
        }
        //封装数据到Map
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        map.put("token_id", mChain.getToken_id());
        map.put("addr", addr);
        map.put("name", mName);
        //加密
        String json = SystemUtils.getJson(map);
        presenter.addAddress(json);
    }

    @Override
    public void addAddressReturn(AddAddressRsBean addAddressRsBean) {
        ToastUtil.showShort(addAddressRsBean.getMessage());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 按下BACK，同时没有重复
           if(mPw.isShowing()){
               mPw.dismiss();
               SystemUtils.setBackgroundAlpha(getWindow(),Constants.NO_SHADOW);;//设置背景不透明
               return true;//不执行系统的返回
           }
        }

        return super.onKeyDown(keyCode, event);
    }

    private void showPopupwindow() {
        View view = View.inflate(this, R.layout.popup_chain_list, null);
        mPw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, 1100);
        SystemUtils.setBackgroundAlpha(getWindow(), Constants.SHADOW);//设置屏幕透明度，具有半透明效果
        mPw.showAtLocation(mTxtSave, Gravity.CENTER|Gravity.BOTTOM, 0,0);
        RecyclerView rlv = view.findViewById(R.id.rlv_chains);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        dealRlv(rlv);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPw.dismiss();
                SystemUtils.setBackgroundAlpha(getWindow(),Constants.NO_SHADOW);;//设置背景不透明
            }
        });
    }

    //RecycleView配置数据
    private void dealRlv(RecyclerView rlv) {
        rlv.setLayoutManager(new LinearLayoutManager(this));
        mChains = new ArrayList<>();
        mChains.add(new Chain(R.mipmap.chain_icon,"ETH","80,000.00","3,000.00",""));
        mChains.add(new Chain(R.mipmap.chain_icon,"USDT","5,000.00","1,000.00",""));
        mChains.add(new Chain(R.mipmap.chain_icon,"XRP","800.00","0.00",""));

        mAdapter = new ChainNameAdapter(this, mChains);
        rlv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseAdapter.BaseViewHolder holder) {
                mName = mChains.get(holder.getLayoutPosition()).getName();
                mTxtChainName.setText(mName);
                mPw.dismiss();
                SystemUtils.setBackgroundAlpha(getWindow(),Constants.NO_SHADOW);//设置背景不透明
            }
        });
    }
}
