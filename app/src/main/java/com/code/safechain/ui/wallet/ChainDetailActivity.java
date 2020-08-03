package com.code.safechain.ui.wallet;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.WalletChainTranConstract;
import com.code.safechain.presenter.WalletChainTranPresenter;
import com.code.safechain.ui.wallet.bean.ChainTransactionRsBean;
import com.code.safechain.ui.wallet.adapter.ChainTransactionAdapter;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class ChainDetailActivity extends BaseActivity<WalletChainTranConstract.Presenter> implements WalletChainTranConstract.View {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.img_info)
    ImageView mImgInfo;
    @BindView(R.id.txt_chain_money1)//总价钱1
    TextView mTxtChainMoney1;
    @BindView(R.id.txt_all)
    TextView mTxtAll;
    @BindView(R.id.txt_into)
    TextView mTxtInto;
    @BindView(R.id.txt_out)
    TextView mTxtOut;
    @BindView(R.id.txt_fail)
    TextView mTxtFail;
    //上次点击的tv
    TextView mLastTxt;
    //交易列表
    @BindView(R.id.rlv_chain_transaction)
    RecyclerView mRlvChainTran;

    private WalletHomeRsBean.ResultBean.DataBean mDataBean;
    private ArrayList<ChainTransactionRsBean.ResultBean.DataBean> mList;
    private ChainTransactionAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_chain_detail;
    }

    @Override
    protected WalletChainTranConstract.Presenter createPresenter() {
        return new WalletChainTranPresenter();
    }

    @Override
    protected void initView() {
        mLastTxt = mTxtAll;//刚进入，把全部作为上次点击的导航textview
        //初始化交易列表
        initRlv();
    }

    private void initRlv() {
        mRlvChainTran.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new ChainTransactionAdapter(this, mList);
        mRlvChainTran.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        //获得Chain对象
        Intent intent = getIntent();
        mDataBean = (WalletHomeRsBean.ResultBean.DataBean) intent.getSerializableExtra(Constants.DATA);
        //设置币的名字
        mTxtTitle.setText(mDataBean.getSymbol());

        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        map.put("token_id",mDataBean.getToken_id());
        map = SystemUtils.getMap(map);
        //得到此币的交易详情
        presenter.getWalletChainTran(map);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick({R.id.img_back, R.id.img_info, R.id.btn_transfer, R.id.btn_collection,
            R.id.txt_all, R.id.txt_into, R.id.txt_out, R.id.txt_fail})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_info:
                //跳转到 info详情页
                Intent itChainInfo = new Intent(this, ChainInfoActivity.class);
                itChainInfo.putExtra(Constants.TOKEN_ID, mDataBean.getToken_id());
                startActivity(itChainInfo);
                break;
            case R.id.txt_all:
                //全部
                setNavigation(mTxtAll);
                break;
            case R.id.txt_into:
                //转入
                setNavigation(mTxtInto);
                break;
            case R.id.txt_out:
                //转出
                setNavigation(mTxtOut);
                break;
            case R.id.txt_fail:
                //失败
                setNavigation(mTxtFail);
                break;
            case R.id.btn_transfer://转账
                Intent itTransfer = new Intent(this, TransferActivity.class);
                itTransfer.putExtra(Constants.DATA, mDataBean);
                startActivity(itTransfer);
                break;
            case R.id.btn_collection://收款
                Intent intentCollection = new Intent(this, CollectionActivity.class);
                startActivity(intentCollection);
                break;
        }
    }

    /**
     * 设置导航的状态信息
     * @param currentTv
     */

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setNavigation(TextView currentTv) {
        if(currentTv != mLastTxt){
            mLastTxt.setBackgroundResource(0);//移除黄色背景
            mLastTxt.setTextColor(getResources().getColor(R.color.colorMyName));//设置字的颜色为灰色
            //设置点击的 功能项 的背景为黄色，字的颜色为白色
            currentTv.setBackground(getResources().getDrawable(R.drawable.bg_txt_yellow));
            currentTv.setTextColor(getResources().getColor(R.color.colorWhite));
            //把当前点击的 功能项 设置为上次点击的功能项
            mLastTxt = currentTv;

            //切换数据

        }
    }

    /**
     * 或钱包 币交易的回调
     * @param chainTransactionRsBean
     */
    @Override
    public void getWalletChainTranReturn(ChainTransactionRsBean chainTransactionRsBean) {
        //转为小数点后两位，设置到界面
        mTxtChainMoney1.setText(String.format("%.2f",Double.parseDouble(chainTransactionRsBean.getResult().getTotal())));
        mAdapter.updataListAddMore(chainTransactionRsBean.getResult().getData());
    }
}
