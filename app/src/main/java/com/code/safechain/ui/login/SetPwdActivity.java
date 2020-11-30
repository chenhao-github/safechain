package com.code.safechain.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.RegistConstract;
import com.code.safechain.presenter.RegistPresenter;
import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.utils.DeviceIdFactory;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetPwdActivity extends BaseActivity<RegistConstract.Presenter> implements RegistConstract.View {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.et_pwd_sure)
    EditText mEtPwdSure;
    @BindView(R.id.rb_agree)
    RadioButton mRbAgree;

    boolean isShow;//密码输入框眼睛是否显示
    private String mVeriCode;
    private String mPhoneNumber;
    private int mRegistType;
    private String mNation;

    @Override
    protected int getLayout() {
        return R.layout.activity_set_pwd;
    }

    @Override
    protected RegistConstract.Presenter createPresenter() {
        return new RegistPresenter();
    }

    @Override
    protected void initView() {
        mPhoneNumber = getIntent().getStringExtra(Constants.PHONE_NUMBER);//得到手机号码，或邮箱号
        mVeriCode = getIntent().getStringExtra(Constants.VERIFICODE);//得到验证码
        mNation = getIntent().getStringExtra(Constants.NATION);//得到国家代码
        mRegistType = getIntent().getIntExtra(Constants.DATA, 0);//1 手机，2邮箱
        dealDrawableRightOfSetPwd(mEtPwd);//处理 密码输入框内部右边的图片的点击事件，实现切换
        dealDrawableRightOfSetPwd(mEtPwdSure);//处理 密码输入框内部右边的图片的点击事件，实现切换

    }

    /**
     * 处理 密码输入框内部右边的图片的点击事件，实现切换
     */
    private void dealDrawableRightOfSetPwd(final EditText et) {
        setDrawableRight(et, isShow);//默认为不显示
        et.setOnTouchListener(new View.OnTouchListener() {//添加点击监听
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = et.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > et.getWidth() - et.getPaddingRight() - drawable.getIntrinsicWidth()){
                    isShow = !isShow;
                    setDrawableRight(et,isShow);
                }
                return false;
            }
        });
    }

    private void setDrawableRight(EditText et, boolean isShow) {
        //获得右方显示的图片
        Drawable drawableHidden = getResources().getDrawable(isShow ? R.mipmap.ic_pwd_show:R.mipmap.ic_pwd_hidden);//得到图片
        drawableHidden.setBounds(0,0,55,55);//设置图片宽高，和左边上边的距离
        et.setCompoundDrawables(null,null,drawableHidden,null);
        et.setInputType(isShow ? InputType.TYPE_CLASS_TEXT : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_back, R.id.btn_regist})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
//                fileList();
                finish();//结束本页面
                break;
            case R.id.btn_regist:
                toRegist();//去注册
                break;
        }
    }

    private void toRegist() {
        String pwd = mEtPwd.getText().toString().trim();
        String pwdSure = mEtPwdSure.getText().toString().trim();
        if(TextUtils.isEmpty(pwd) || pwd.length()<8 || pwd.length()>20){
            ToastUtil.showShort("密码不合法!");
            return;
        }
        if(TextUtils.isEmpty(pwdSure) || !pwdSure.equals(pwd)){
            ToastUtil.showShort("两次密码输入不一致!");
            return;
        }
        if(!mRbAgree.isChecked()){
            ToastUtil.showShort("请选择用户协议");
            return;
        }
        //封装数据到Map
        HashMap<String, Object> map = new HashMap<>();
        map.put("type",mRegistType);//注册的类别  1手机 2邮箱
        map.put("passwd",pwd);
        if(mRegistType == 1){//手机号注册，需要国家代码
            map.put("phone",mPhoneNumber);//手机号码
            map.put("nation",mNation);
        }else if(mRegistType == 2){//邮箱注册
            map.put("email",mPhoneNumber);//邮箱号
        }
        map.put("sms_code",mVeriCode);
        map.put("device_type",2);
        map.put("device_id", DeviceIdFactory.getInstance(this).getDeviceUuid());
        //加密
        String json = SystemUtils.getJson(map);

        presenter.sendPwd(json);
    }

    @Override
    public void sendPwdReturn(RegistRsBean registRsBean) {
        if(registRsBean.getError() == 0){
            //跳转到注册成功界面
//            startActivity(new Intent(this, RegistSuccessActivity.class));
//            finish();
            //跳转到登录页面
            startActivity(new Intent(this, LoginActivity.class));
            finish();

        }else if (registRsBean.getError() == -202){
            ToastUtil.showShort(registRsBean.getMessage());
        }
    }


    @Override
    public void sendVerifiCodeReturn() {

    }


}
