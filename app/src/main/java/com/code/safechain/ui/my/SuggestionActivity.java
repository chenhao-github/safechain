package com.code.safechain.ui.my;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.code.safechain.R;
import com.code.safechain.app.BaseApp;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.MyConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.presenter.MyPresenter;
import com.code.safechain.ui.main.bean.UserBean;
import com.code.safechain.ui.my.bean.GestureRsBean;
import com.code.safechain.ui.my.bean.SuggestionRsBean;
import com.code.safechain.ui.my.bean.UploadIconRsBean;
import com.code.safechain.utils.LoggerUtil;
import com.code.safechain.utils.RxUtils;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class SuggestionActivity extends BaseActivity<MyConstract.Presenter> implements MyConstract.View {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.et_suggestion)
    EditText mEtSuggestion;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    private boolean canSubmit;

    @Override
    protected int getLayout() {
        return R.layout.activity_suggestion;
    }

    @Override
    protected MyConstract.Presenter createPresenter() {
        return new MyPresenter();
    }

    @Override
    protected void initView() {
        //创建输入框值改变监听，并设置监听
        MyTextWatcher watcher = new MyTextWatcher();
        mEtSuggestion.addTextChangedListener(watcher);//添加监听
        mEtPhone.addTextChangedListener(watcher);


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

    //EditText值改变监听
    class MyTextWatcher implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //得到输入框的值
            String suggestion = mEtSuggestion.getText().toString().trim();
            String phone = mEtPhone.getText().toString().trim();
            if(!TextUtils.isEmpty(suggestion) || !TextUtils.isEmpty(phone)){
                //有一个不为空，设置按钮背景为黄色
                mBtnSubmit.setBackgroundResource(R.drawable.bg_button_yellow);
                mBtnSubmit.setEnabled(true);
                canSubmit = true;
            }else {
                //都为空，设置按钮背景为灰色
                mBtnSubmit.setBackgroundResource(R.drawable.bg_button_gray);
                mBtnSubmit.setEnabled(false);
                canSubmit = false;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.img_back, R.id.btn_submit})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:
                onSubmit();//提交业务
                break;
        }
    }

    private void onSubmit() {
        String msg = mEtSuggestion.getText().toString();
        String phone = mEtPhone.getText().toString();
        if(SystemUtils.isMobile(phone)){
            //封装数据
            HashMap<String, Object> map = new HashMap<>();
            map.put("mobile", phone);
            map.put("content", msg);

            map = SystemUtils.getMap(map);
            HttpManager.getInstance().getApiServer().addSuggestion(map)
                    .compose(RxUtils.<SuggestionRsBean>changeScheduler())
                    .subscribe(new Observer<SuggestionRsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(SuggestionRsBean suggestionRsBean) {
                            if(suggestionRsBean.getError() == 0){
                                ToastUtil.showShort("提交成功");
                                finish();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
