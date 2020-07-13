package com.code.safechain.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.code.safechain.R;
import com.code.safechain.base.BaseAdapter;
import com.code.safechain.base.BaseFragment;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.WalletConstract;
import com.code.safechain.model.bean.Chain;
import com.code.safechain.presenter.WalletPresenter;
import com.code.safechain.ui.wallet.adapter.ChainAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @Auther: hchen
 * @Date: 2020/7/4 0004
 * @Description:
 */
public class WalletFragment extends BaseFragment<WalletConstract.Presenter>
        implements  WalletConstract.View{
    @BindView(R.id.rlv_chain)
    RecyclerView mRlvChain;
    private ArrayList<Chain> mChains;
    private ChainAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected WalletConstract.Presenter createPresenter() {
        return new WalletPresenter();
    }

    @Override
    protected void initView() {
        mRlvChain.setLayoutManager(new LinearLayoutManager(getActivity()));
        mChains = new ArrayList<>();
        mAdapter = new ChainAdapter(getActivity(), mChains);
        mRlvChain.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseAdapter.BaseViewHolder holder) {
                Chain chain = mChains.get(holder.getLayoutPosition());
                Intent intent = new Intent(getActivity(), ChainDetailActivity.class);
                intent.putExtra(Constants.DATA, chain);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        mChains.add(new Chain(R.mipmap.chain_icon,"ETH","80,000.00","3,000.00"));
        mChains.add(new Chain(R.mipmap.chain_icon,"USDT","5,000.00","1,000.00"));
        mChains.add(new Chain(R.mipmap.chain_icon,"XRP","800.00","0.00"));
        mAdapter.notifyDataSetChanged();
    }
}
