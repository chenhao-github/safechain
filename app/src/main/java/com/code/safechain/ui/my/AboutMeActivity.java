package com.code.safechain.ui.my;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import com.code.safechain.utils.ToastUtil;

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



    @OnClick({R.id.img_back, R.id.txt_network_url, R.id.txt_forum_url, R.id.txt_twitter_url,
            R.id.txt_microblog_url})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_network_url:
                setSysClipboardText(getResources().getString(R.string.my_network_url));
                break;
            case R.id.txt_forum_url:
                setSysClipboardText("SkyEcochain");
                break;
            case R.id.txt_twitter_url:
                setSysClipboardText("SkyEcochain");;
                break;
            case R.id.txt_microblog_url:
                setSysClipboardText("SkyEcochain");;
                break;
        }
    }

    public void setSysClipboardText(String data) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", data);
// 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        ToastUtil.showShort("复制成功");
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
