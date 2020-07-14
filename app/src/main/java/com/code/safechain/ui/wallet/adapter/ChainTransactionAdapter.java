package com.code.safechain.ui.wallet.adapter;

import android.content.Context;

import com.code.safechain.base.BaseAdapter;
import com.code.safechain.model.bean.ChainTransaction;

import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/7/14 0014
 * @Description:
 */
public class ChainTransactionAdapter extends BaseAdapter<ChainTransaction> {
    public ChainTransactionAdapter(Context context, List<ChainTransaction> datas) {
        super(context, datas);
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void bindData(BaseViewHolder holder, ChainTransaction data) {

    }
}
