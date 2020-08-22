package com.code.safechain.ui.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.common.Constants;
import com.code.safechain.ui.login.bean.CountryCodeBean;
import com.code.safechain.ui.transaction.bean.OthersSaleOrderRsBean;

import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/8/7 0007
 * @Description:
 */
public class CountryCodeAdapter extends BaseAdapter  {
    private List<CountryCodeBean.ResultBean> mList;
    private Context mContext;

    public CountryCodeAdapter(Context pContext, List<CountryCodeBean.ResultBean> pList) {
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
            TextView country=(TextView)convertView.findViewById(R.id.txt_paytype);
            country.setText(mList.get(position).getCname());
        }
        return convertView;
    }
}
