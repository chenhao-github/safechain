package com.code.safechain.ui.wallet.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.code.safechain.R;
import com.code.safechain.base.BaseAdapter;
import com.code.safechain.model.bean.Chain;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;

import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/7/11 0011
 * @Description:
 */
public class ChainNameAdapter extends BaseAdapter<WalletHomeRsBean.ResultBean.DataBean> {

    public ChainNameAdapter(Context context, List<WalletHomeRsBean.ResultBean.DataBean> mChains) {
        super(context, mChains);
    }

    @Override
    public int getLayout() {
        return R.layout.item_wallet_chain_name;
    }

    @Override
    public void bindData(BaseViewHolder holder, WalletHomeRsBean.ResultBean.DataBean data) {
        TextView name = (TextView) holder.getViewById(R.id.txt_chain_name);
        name.setText(data.getSymbol());
    }
}
