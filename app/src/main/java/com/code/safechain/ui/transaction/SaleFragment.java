package com.code.safechain.ui.transaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.code.safechain.R;
import com.code.safechain.base.BaseFragment;
import com.code.safechain.interfaces.TransactionConstract;
import com.code.safechain.presenter.TransactionPresenter;
import com.code.safechain.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Auther: hchen
 * @Date: 2020/7/22 0022
 * @Description:
 */
public class SaleFragment extends BaseFragment<TransactionConstract.Presenter> implements TransactionConstract.View {
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected int getLayout() {
        return R.layout.fragment_transaction_sale;
    }

    @Override
    protected TransactionConstract.Presenter createPresenter() {
        return new TransactionPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_submit})
    public void onViewClicked(View v) {
        if(v.getId() == R.id.btn_submit)
            ToastUtil.showShort("提交");
    }
}
