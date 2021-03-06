package com.code.safechain.ui.login;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.LoginConstract;
import com.code.safechain.presenter.LoginPresenter;
import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.ui.main.MainActivity;
import com.code.safechain.utils.DeviceIdFactory;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginConstract.Presenter> implements LoginConstract.View {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.et_account)
    EditText mEtAccount;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.txt_pwd_forget)
    TextView mTxtPwdForget;
    @BindView(R.id.txt_goto_regist)
    TextView mTxtGotoRegist;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    boolean isShow;//密码输入框眼睛是否显示
    private String mLoginType;//登录类别 1手机号 2邮箱
    private String mPhone;
    private String mPwd;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginConstract.Presenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initView() {
        //设置去注册的下划线
//        mTxtGotoRegist.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        dealDrawableRight();//处理 密码输入框内部右边的图片的点击事件，实现切换
    }

    @Override
    protected void initData() {

    }

    /**
     * 处理 密码输入框内部右边的图片的点击事件，实现切换
     */
    private void dealDrawableRight() {
        setDrawableRight(isShow);//默认为不显示
        mEtPwd.setOnTouchListener(new View.OnTouchListener() {//添加点击监听
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = mEtPwd.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > mEtPwd.getWidth() - mEtPwd.getPaddingRight() - drawable.getIntrinsicWidth()){
                    isShow = !isShow;
                    setDrawableRight(isShow);
                }
                return false;
            }
        });
    }

    private void setDrawableRight(boolean isShow) {
        //获得右方显示的图片
        Drawable drawableHidden = getResources().getDrawable(isShow ? R.mipmap.ic_pwd_show:R.mipmap.ic_pwd_hidden);//得到图片
        drawableHidden.setBounds(0,0,55,55);//设置图片宽高，和左边上边的距离
        mEtPwd.setCompoundDrawables(null,null,drawableHidden,null);
        mEtPwd.setInputType(isShow ? InputType.TYPE_CLASS_TEXT : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    public void loginReturn(RegistRsBean registRsBean) {
        if(registRsBean.getError() == 0){
            String token = registRsBean.getResult().getToken();
            SpUtils.getInstance(this).setValue(Constants.TOKEN, token);//保存登录token
            //保存账号，密码，类别，登录时间
            SpUtils.getInstance(this).setValue(Constants.ACCOUNT,mPhone);
            SpUtils.getInstance(this).setValue(Constants.PASSWORD,mPwd);
            SpUtils.getInstance(this).setValue(Constants.LOGIN_TYPE,mLoginType);
            SpUtils.getInstance(this).setValue(Constants.LOGIN_TIME,new Date().getTime());

            startActivity(new Intent(this,MainActivity.class));
            finish();
        }else {
            ToastUtil.showShort("账号密码不正确!");
        }
    }

    @OnClick({R.id.img_back, R.id.txt_pwd_forget, R.id.txt_goto_regist, R.id.btn_login})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_pwd_forget:
                //跳转到修改密码的 第一步：手机验证码 验证
                startActivity(new Intent(this, VerificationCodeUpdatePwdActivity.class));
                break;
            case R.id.txt_goto_regist:
                toVerificationCode();//注册，先获取验证码
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }
    //去登陆
    private void login() {
        //封装数据到Map
        HashMap<String, Object> map = new HashMap<>();
        //得到账号和密码
        mPhone = mEtAccount.getText().toString().trim();
        mPwd = mEtPwd.getText().toString().trim();
        if(TextUtils.isEmpty(mPhone)){
            ToastUtil.showShort("账号不能为空!");
            return;
        }
        if(SystemUtils.isMobile(mPhone)){//是手机号，封装手机号信息
            mLoginType = "1";
            map.put("type","1");
            map.put("phone", mPhone);
            map.put("nation","+86");
        }else {//不是手机号，验证是否是邮箱
            if(SystemUtils.isEmail(mPhone)){//是邮箱，封装邮箱
                mLoginType = "2";
                map.put("type","2");
                map.put("email", mPhone);
            }else {
                ToastUtil.showShort("请输入正确的手机号或邮箱");
                return;
            }
        }

        if(TextUtils.isEmpty(mPwd) || mPwd.length()<4 || mPwd.length()>20){
            ToastUtil.showShort("密码不合法!");
            return;
        }
        map.put("passwd", mPwd);
        map.put("device_type",2);
        map.put("device_id", DeviceIdFactory.getInstance(this).getDeviceUuid());
        //加密
        String json = SystemUtils.getJson(map);
        presenter.login(json);
    }

    //跳转到 验证码发送页面
    private void toVerificationCode() {
        Intent intent = new Intent(this, VerificationCodeActivity.class);
        startActivity(intent);
    }
}
