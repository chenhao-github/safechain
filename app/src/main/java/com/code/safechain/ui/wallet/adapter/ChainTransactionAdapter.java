package com.code.safechain.ui.wallet.adapter;

import android.content.Context;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.base.BaseAdapter;
import com.code.safechain.common.Constants;
import com.code.safechain.ui.wallet.bean.ChainTransactionRsBean;

import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/7/14 0014
 * @Description:
 */
public class ChainTransactionAdapter extends BaseAdapter<ChainTransactionRsBean.ResultBean.DataBean> {
    public ChainTransactionAdapter(Context context, List<ChainTransactionRsBean.ResultBean.DataBean> datas) {
        super(context, datas);
    }

    @Override
    public int getLayout() {
        return R.layout.item_wallet_chain_transaction;
    }

    @Override
    public void bindData(BaseViewHolder holder, ChainTransactionRsBean.ResultBean.DataBean data) {
        TextView price = (TextView) holder.getViewById(R.id.txt_price);
        TextView name = (TextView) holder.getViewById(R.id.txt_type_name);
        TextView time = (TextView) holder.getViewById(R.id.txt_time);
        //赋值
        price.setText("￥"+String.format("%.6f",Double.parseDouble(data.getAmount())));
//        price.setText("￥"+data.getAmount());
        name.setText(Constants.TRANSACTIONS[data.getType()]);
        name.setTextColor(mContext.getResources().getColor(Constants.TRANSACTIONS_COLOR[data.getType()]));
        time.setText(data.getCtime());

    }
}
