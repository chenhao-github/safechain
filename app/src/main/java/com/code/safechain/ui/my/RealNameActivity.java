package com.code.safechain.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.interfaces.MyConstract;
import com.code.safechain.presenter.MyPresenter;
import com.code.safechain.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RealNameActivity extends BaseActivity<MyConstract.Presenter> implements MyConstract.View {
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.et_realname)
    EditText mEtRealname;
    @BindView(R.id.et_identity)
    EditText mEtIdentity;
    @BindView(R.id.img_camera_front)
    ImageView mImgCameraFront;
    @BindView(R.id.img_camera_back)
    ImageView mImgCameraBack;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;

    @Override
    protected int getLayout() {
        return R.layout.activity_real_name;
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

    @OnClick({R.id.img_back, R.id.img_camera_front, R.id.img_camera_back, R.id.btn_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_camera_front:
                break;
            case R.id.img_camera_back:
                break;
            case R.id.btn_confirm:
                ToastUtil.showShort("上传");
                break;
        }
    }
}
