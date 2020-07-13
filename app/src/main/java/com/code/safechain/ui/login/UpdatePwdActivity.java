package com.code.safechain.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.interfaces.RegistConstract;
import com.code.safechain.presenter.RegistPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdatePwdActivity extends BaseActivity<RegistConstract.Presenter> implements RegistConstract.View {

    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.et_pwd_sure)
    EditText mEtPwdSure;

    boolean isShow;//密码输入框眼睛是否显示

    @Override
    protected int getLayout() {
        return R.layout.activity_update_pwd;
    }

    @Override
    protected RegistConstract.Presenter createPresenter() {
        return new RegistPresenter();
    }

    @Override
    protected void initView() {
        dealDrawableRightOfSetPwd(mEtPwd);//处理 密码输入框内部右边的图片的点击事件，实现切换
        dealDrawableRightOfSetPwd(mEtPwdSure);//处理 密码输入框内部右边的图片的点击事件，实现切换
    }

    /**
     * 处理 密码输入框内部右边的图片的点击事件，实现切换
     */
    private void dealDrawableRightOfSetPwd(EditText et) {
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
    public void sendVerifiCodeReturn() {

    }

    @Override
    public void sendPwdReturn() {

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

                //跳转到登录界面
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }


}
