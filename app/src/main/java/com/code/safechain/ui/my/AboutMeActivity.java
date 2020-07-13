package com.code.safechain.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.interfaces.MyConstract;
import com.code.safechain.presenter.MyPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutMeActivity extends BaseActivity<MyConstract.Presenter> implements MyConstract.View {

    @BindView(R.id.img_back)
    ImageView mImgBack;

    @Override
    protected int getLayout() {
        return R.layout.activity_about_me;
    }

    @Override
    protected MyConstract.Presenter createPresenter() {
        return new MyPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void uploadHeaderReturn() {

    }


    @OnClick(R.id.img_back)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }
}
