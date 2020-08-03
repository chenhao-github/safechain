package com.code.safechain.ui.transaction.adapter;

import android.content.Context;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.base.BaseAdapter;
import com.code.safechain.model.bean.OrderBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/7/25 0025
 * @Description:
 */
public class OrderAdapter extends BaseAdapter<OrderBean> {
    private ArrayList<OrderBean> mOrderBeans;

    public OrderAdapter(Context context, List<OrderBean> datas) {
        super(context, datas);
    }

    @Override
    public int getLayout() {
        return R.layout.item_transaction_order;
    }

    @Override
    public void bindData(BaseViewHolder holder, OrderBean data) {
        TextView chainName = (TextView) holder.getViewById(R.id.txt_chain_name);
        TextView status = (TextView) holder.getViewById(R.id.txt_status);
        TextView number = (TextView) holder.getViewById(R.id.txt_number);
        TextView price = (TextView) holder.getViewById(R.id.txt_unit_price);
        TextView orderNumber = (TextView) holder.getViewById(R.id.txt_order_number);
        TextView trade = (TextView) holder.getViewById(R.id.txt_trade);

        chainName.setText(data.getChainName());
        status.setText(data.getStatus());
        number.setText(data.getNumber()+"");
        price.setText(data.getUnitPrice()+"");
        orderNumber.setText(data.getOrderNo());
        trade.setText(data.getVolume());

    }
}
