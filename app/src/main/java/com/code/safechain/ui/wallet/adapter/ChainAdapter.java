package com.code.safechain.ui.wallet.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.code.safechain.R;
import com.code.safechain.base.BaseAdapter;
import com.code.safechain.model.bean.Chain;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/7/11 0011
 * @Description:
 */
public class ChainAdapter extends BaseAdapter<WalletHomeRsBean.ResultBean.DataBean> {

    public ChainAdapter(Context context, List<WalletHomeRsBean.ResultBean.DataBean> datas) {
        super(context, datas);
    }

    @Override
    public int getLayout() {
        return R.layout.item_wallet_chain;
    }

    @Override
    public void bindData(BaseViewHolder holder, WalletHomeRsBean.ResultBean.DataBean data) {
        ImageView icon = (ImageView) holder.getViewById(R.id.img_chain_icon);
        TextView name = (TextView) holder.getViewById(R.id.txt_chain_name);
        TextView money1 = (TextView) holder.getViewById(R.id.txt_chain_money1);
        TextView money2 = (TextView) holder.getViewById(R.id.txt_chain_money2);
        Glide.with(mContext).load(data.getLogo_url()).into(icon);

        name.setText(data.getSymbol());
//        money1.setText(String.format("%.2f", Double.parseDouble(data.getNum())));
//        money2.setText("￥ " + String.format("%.2f",data.getSum()));
        money1.setText(data.getNum());
        money2.setText("￥" + data.getSum());
    }
}
