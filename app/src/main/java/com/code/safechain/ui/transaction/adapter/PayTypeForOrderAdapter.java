package com.code.safechain.ui.transaction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.common.Constants;
import com.code.safechain.ui.transaction.bean.OrderDetailRsBean;
import com.code.safechain.ui.transaction.bean.OthersSaleOrderRsBean;

import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/8/7 0007
 * @Description:
 */
public class PayTypeForOrderAdapter extends BaseAdapter  {
    private List<OrderDetailRsBean.ResultBean.PaysBean> mList;
    private Context mContext;

    public PayTypeForOrderAdapter(Context pContext, List<OrderDetailRsBean.ResultBean.PaysBean> pList) {
        this.mContext = pContext;
        this.mList = pList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     * 下面是重要代码
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(mContext).inflate(R.layout.item_transaction_paytype, null);
        if(convertView!=null) {
            TextView paytype=(TextView)convertView.findViewById(R.id.txt_paytype);
            paytype.setText(Constants.PAYTYPE[mList.get(position).getPay_type()]);
        }
        return convertView;
    }
}
