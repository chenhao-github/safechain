package com.code.safechain.ui.wallet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.code.safechain.R;
import com.code.safechain.base.BaseAdapter;
import com.code.safechain.model.bean.Chain;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/7/11 0011
 * @Description:
 */
public class ChainAdapter extends BaseAdapter<Chain> {

    public ChainAdapter(Context context, List<Chain> datas) {
        super(context, datas);
    }

    @Override
    public int getLayout() {
        return R.layout.item_wallet_chain;
    }

    @Override
    public void bindData(BaseViewHolder holder, Chain data) {
        ImageView icon = (ImageView) holder.getViewById(R.id.img_chain_icon);
        TextView name = (TextView) holder.getViewById(R.id.txt_chain_name);
        TextView money1 = (TextView) holder.getViewById(R.id.txt_chain_money1);
        TextView money2 = (TextView) holder.getViewById(R.id.txt_chain_money2);
        Glide.with(mContext).load(data.getImg()).into(icon);
        name.setText(data.getName());
        money1.setText(data.getMoney1());
        money2.setText("￥ "+data.getMoney2());
    }
}
