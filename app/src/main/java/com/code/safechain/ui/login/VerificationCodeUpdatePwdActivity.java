package com.code.safechain.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.code.safechain.R;
import com.code.safechain.app.BaseApp;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.RegistConstract;
import com.code.safechain.presenter.RegistPresenter;
import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class VerificationCodeUpdatePwdActivity extends BaseActivity<RegistConstract.Presenter>
        implements RegistConstract.View {

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.txt_send_code)
    TextView mTxtSendCode;

    private ArrayList<String> mCountrysList;
    private ArrayAdapter<String> countryAdapter;
    private ArrayList<String> mFixsList;
    private ArrayAdapter<String> mFixAdapter;
    private String mPhone;

    //5分钟换算成毫秒  60
    private int timeStemp = 60;
    private Timer mTimer;
    private String mType;

    @Override
    protected int getLayout() {
        return R.layout.activity_verification_code_update_pwd;
    }

    @Override
    protected RegistConstract.Presenter createPresenter() {
        return new RegistPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_back, R.id.txt_send_code, R.id.btn_next})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_send_code:
                sendVerifiCode();
                break;
            case R.id.btn_next:
                toUpdatePwdActivity();
                break;
        }
    }

    private void sendVerifiCode() {
        //封装数据到Map
        HashMap<String, Object> map = new HashMap<>();
        //得到手机号 或 邮箱
        String phone = mEtPhone.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            ToastUtil.showShort("账号不能为空!");
            return;
        }
        if(SystemUtils.isMobile(phone)){//是手机号，封装手机号信息
            mType = "1";
            map.put("type","1");
            map.put("phone",phone);
            map.put("nation","+86");
        }else {//不是手机号，验证是否是邮箱
            if(SystemUtils.isEmail(phone)){//是邮箱，封装邮箱
                mType = "2";
                map.put("type","2");
                map.put("email",phone);
            }else {
                ToastUtil.showShort("请输入正确的手机号或邮箱");
                return;
            }
        }

        //加密
        String json = SystemUtils.getJson(map);//得到签名后的数据作为sign的值的json串
        presenter.sendVerifiCode(json);

        //设置 发送验证码不可点击，设置倒计时
        mTxtSendCode.setEnabled(false);//不可用
        mTxtSendCode.setTextColor(getResources().getColor(R.color.colorMyRight));//灰色
        mTxtSendCode.setText(timeStemp+""+Constants.RESEND);//设置300秒后重发
        //倒计时
        getCountDownTime();
    }



    private void toUpdatePwdActivity() {
        //得到手机号 或 邮箱
        String phone = mEtPhone.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            ToastUtil.showShort("账号不能为空!");
            return;
        }
        if(!SystemUtils.isMobile(phone) && !SystemUtils.isEmail(phone)){
            ToastUtil.showShort("账号不合法！");
            return;
        }
        //得到验证码
        String veriCode = mEtCode.getText().toString().trim();
        if(TextUtils.isEmpty(veriCode) || veriCode.length()!=6){
            ToastUtil.showShort("验证码不合法！");
            return;
        }
        //验证码没问题，跳转到重置页面
        Intent intent = new Intent(this, UpdatePwdActivity.class);
        intent.putExtra(Constants.PHONE_NUMBER,phone);
        intent.putExtra(Constants.VERIFICODE,veriCode);
        intent.putExtra("type",mType);//类别
        startActivity(intent);
        finish();
    }

    @Override
    public void sendVerifiCodeReturn() {

    }

    private void getCountDownTime() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeStemp--;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTxtSendCode.setText(timeStemp+""+Constants.RESEND);
                        if(timeStemp==0){
                            mTimer.cancel();
                            //设置 发送验证码可点击
                            timeStemp = 60;
                            mTxtSendCode.setEnabled(true);//可用
                            mTxtSendCode.setTextColor(getResources().getColor(R.color.colorPwdForget));//蓝色
                            mTxtSendCode.setText(Constants.SENDCODE);
                        }
                    }
                });
            }
        },1000,1000);
    }

    @Override
    public void sendPwdReturn(RegistRsBean registRsBean) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        //离开页面关闭倒计时
        if(mTimer != null )
            mTimer.cancel();
    }
}
