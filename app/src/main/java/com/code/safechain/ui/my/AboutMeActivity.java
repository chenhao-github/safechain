package com.code.safechain.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.interfaces.MyConstract;
import com.code.safechain.presenter.MyPresenter;
import com.code.safechain.ui.main.bean.UserBean;
import com.code.safechain.ui.my.bean.GestureRsBean;
import com.code.safechain.ui.my.bean.UploadIconRsBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

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

    @Override
    public void uploadHeaderReturn(UploadIconRsBean uploadIconRsBean) {

    }

    @Override
    public void updateUserDataReturn(GestureRsBean gestureRsBean) {

    }

    @Override
    public void getUserDataReturn(UserBean userBean) {

    }
}
