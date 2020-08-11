package com.code.safechain.ui.transaction.adapter;

import android.content.Context;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.app.BaseApp;
import com.code.safechain.base.BaseAdapter;
import com.code.safechain.common.Constants;
import com.code.safechain.ui.transaction.bean.OrderRsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/7/25 0025
 * @Description:
 */
public class OrderAdapter extends BaseAdapter<OrderRsBean.ResultBean> {
    private ArrayList<OrderRsBean.ResultBean> mOrderBeans;

    public OrderAdapter(Context context, List<OrderRsBean.ResultBean> datas) {
        super(context, datas);
    }

    @Override
    public int getLayout() {
        return R.layout.item_transaction_order;
    }

    @Override
    public void bindData(BaseViewHolder holder, OrderRsBean.ResultBean data) {
        TextView chainName = (TextView) holder.getViewById(R.id.txt_chain_name);
        TextView status = (TextView) holder.getViewById(R.id.txt_status);
        TextView number = (TextView) holder.getViewById(R.id.txt_number);
        TextView price = (TextView) holder.getViewById(R.id.txt_unit_price);
        TextView orderNumber = (TextView) holder.getViewById(R.id.txt_order_number);
        TextView trade = (TextView) holder.getViewById(R.id.txt_trade);

        chainName.setText("");
        status.setText(Constants.ORDERSTATE[data.getState()]);
        if(data.getState() == 2){//已完成 设置为绿色
            status.setTextColor(BaseApp.getRes().getColor(R.color.colorGreenInto));
        }
        number.setText(data.getNum());
        price.setText(data.getPrice());
        orderNumber.setText(data.getOrder_no());
        trade.setText("￥ "+data.getTotal());

    }
}
