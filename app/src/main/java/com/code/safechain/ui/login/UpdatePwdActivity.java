package com.code.safechain.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.UpdateUserInfoConstract;
import com.code.safechain.presenter.UpdateUserInfoPresenter;
import com.code.safechain.ui.my.bean.GestureRsBean;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdatePwdActivity extends BaseActivity<UpdateUserInfoConstract.Presenter>
        implements UpdateUserInfoConstract.View {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.et_pwd_sure)
    EditText mEtPwdSure;

    boolean isShow;//密码输入框眼睛是否显示
    private String mPhoneNumber;
    private String mVeriCode;
    private String mType;

    @Override
    protected int getLayout() {
        return R.layout.activity_update_pwd;
    }

    @Override
    protected UpdateUserInfoConstract.Presenter createPresenter() {
        return new UpdateUserInfoPresenter();
    }

    @Override
    protected void initView() {
        requestPermiss();//处理动态权限
        mPhoneNumber = getIntent().getStringExtra(Constants.PHONE_NUMBER);//得到手机号码 或邮箱
        mVeriCode = getIntent().getStringExtra(Constants.VERIFICODE);//得到验证码
        mType = getIntent().getStringExtra("type");//得到类别 1手机  2邮箱
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


    @Override
    public void updateUserInfoReturn( GestureRsBean gestureRsBean) {
        if(gestureRsBean.getError() == 0){
            ToastUtil.showShort("修改成功!");
            //跳转到登录界面
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @OnClick({R.id.img_back, R.id.btn_update})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                fileList();
                break;
            case R.id.btn_update:
                //网络请求修改
                toUpdate();
                break;
        }
    }

    //去重置密码
    private void toUpdate() {
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
        //封装数据到Map
        HashMap<String, Object> map = new HashMap<>();
//        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        if("1".equals(mType)){//手机
            map.put("phone",mPhoneNumber);
            map.put("nation","86");
            map.put("sms_code",mVeriCode);
        }else if ("2".equals(mType)){//邮箱
            map.put("email",mPhoneNumber);
            map.put("email_code",mVeriCode);
        }
        map.put("type",mType);
        map.put("passwd",pwd);
        //加密
        String json = SystemUtils.getJson(map);
        presenter.updateUserInfo(json);
    }

    //申请权限
    private void requestPermiss() {
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> permissionsList = new ArrayList<>();
            String[] permissions = {
                    Manifest.permission.READ_PHONE_STATE
            };

            for (String perm : permissions) {
                if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(perm)) {
                    permissionsList.add(perm);// 进入到这里代表没有权限.
                }
            }
            if (permissionsList.isEmpty()) {
                return;
            } else {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 0);
            }
        }
    }
}
