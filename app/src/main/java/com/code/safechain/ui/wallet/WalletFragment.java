package com.code.safechain.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.base.BaseAdapter;
import com.code.safechain.base.BaseFragment;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.WalletAddressConstract;
import com.code.safechain.interfaces.WalletConstract;
import com.code.safechain.interfaces.WalletHomeConstract;
import com.code.safechain.model.bean.Chain;
import com.code.safechain.presenter.WalletAddressPresenter;
import com.code.safechain.presenter.WalletHomePresenter;
import com.code.safechain.presenter.WalletPresenter;
import com.code.safechain.ui.wallet.adapter.ChainAdapter;
import com.code.safechain.ui.wallet.bean.WalletAddressRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;
import com.code.safechain.utils.LoggerUtil;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.ResponseBody;


/**
 * @Auther: hchen
 * @Date: 2020/7/4 0004
 * @Description:
 */
public class WalletFragment extends BaseFragment<WalletHomeConstract.Presenter>
        implements  WalletHomeConstract.View{
    @BindView(R.id.txt_money)
    TextView mTxtMoney;
    @BindView(R.id.rlv_chain)
    RecyclerView mRlvChain;
    private ArrayList<WalletHomeRsBean.ResultBean.DataBean> mChains;
    private ChainAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected WalletHomeConstract.Presenter createPresenter() {
        return new WalletHomePresenter();
    }

    @Override
    protected void initView() {
        mRlvChain.setLayoutManager(new LinearLayoutManager(getActivity()));
        mChains = new ArrayList<>();
        mAdapter = new ChainAdapter(getActivity(), mChains);
        mRlvChain.setAdapter(mAdapter);
        //点击跳转到币的详情页面
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseAdapter.BaseViewHolder holder) {
                WalletHomeRsBean.ResultBean.DataBean dataBean = mChains.get(holder.getLayoutPosition());
                Intent intent = new Intent(getActivity(), ChainDetailActivity.class);
                intent.putExtra(Constants.DATA, dataBean);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        LoggerUtil.logI("111","initData");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LoggerUtil.logI("111","onDestroy");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoggerUtil.logI("111","onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        LoggerUtil.logI("111","onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        LoggerUtil.logI("111","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LoggerUtil.logI("111","onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LoggerUtil.logI("111","onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LoggerUtil.logI("111","onDetach");
    }

    //第一次进来获取币列表信息，从币的详情页返回来刷新数据，因为有转账要更新数据
    @Override
    public void onResume() {
        super.onResume();
        LoggerUtil.logI("111","onResume");
        //获得钱包信息
        //封装数据到Map
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(getActivity()).getString(Constants.TOKEN));
        map.put("type",1);
        map = SystemUtils.getMap(map);
        presenter.getWalletHome(map);
    }

    @Override
    public void getWalletHomeReturn(WalletHomeRsBean walletHomeRsBean) {
        if(walletHomeRsBean.getResult() != null){
//            mTxtMoney.setText(String.format("%.6f", walletHomeRsBean.getResult().getTotal()));
            mTxtMoney.setText(walletHomeRsBean.getResult().getTotal()+"");
            mAdapter.updataListClearAddMore(walletHomeRsBean.getResult().getData());

        }

    }
}
