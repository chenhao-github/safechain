package com.code.safechain.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.RegistConstract;
import com.code.safechain.presenter.RegistPresenter;
import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class VerificationCodeEmailActivity extends BaseActivity<RegistConstract.Presenter> implements RegistConstract.View {

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.txt_send_code)
    TextView mTxtSendCode;

    //5分钟换算成毫秒  60
    private int timeStemp = 60;
    private Timer mTimer;

    @Override
    protected int getLayout() {
        return R.layout.activity_verification_code_email;
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
                sendVerifiCode();//发送邮箱验证码
                break;
            case R.id.btn_next:
                toSetPwdActivity();
                break;
        }
    }

    private void sendVerifiCode() {
        //验证邮箱
        String phoneNum = mEtPhone.getText().toString().trim();//得到邮箱
        if(!checkPhone(phoneNum)){//验证邮箱  不合法
            return;
        }
        // 封装数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("type",2);
        map.put("email", phoneNum);
        //加密
        String json = SystemUtils.getJson(map);//得到签名后的数据作为sign的值的json串
        presenter.sendVerifiCode(json);

        //设置 发送验证码不可点击，设置倒计时
        mTxtSendCode.setEnabled(false);//不可用
        mTxtSendCode.setTextColor(getResources().getColor(R.color.colorMyRight));//灰色
        mTxtSendCode.setText(timeStemp+""+ Constants.RESEND);//设置300秒后重发
        //倒计时
        getCountDownTime();
    }

    //验证邮箱
    private boolean checkPhone(String phoneNum) {

        if(TextUtils.isEmpty(phoneNum)){
            Toast.makeText(this, "邮箱不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!SystemUtils.isEmail(phoneNum)){
            Toast.makeText(this, "邮箱不合法！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void toSetPwdActivity() {
        String phoneNum = mEtPhone.getText().toString().trim();//得到邮箱
        if(!checkPhone(phoneNum)){//验证邮箱  不合法
            return;
        }
        //得到验证码
        String veriCode = mEtCode.getText().toString().trim();
        if(TextUtils.isEmpty(veriCode) || veriCode.length()!=6){
            ToastUtil.showShort("验证码不合法！");
            return;
        }
        Intent intent = new Intent(this, SetPwdActivity.class);
        intent.putExtra(Constants.PHONE_NUMBER,phoneNum);
        intent.putExtra(Constants.VERIFICODE,veriCode);
        intent.putExtra(Constants.DATA,2);//邮箱设置密码
        startActivity(intent);
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
    public void sendVerifiCodeReturn() {

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
