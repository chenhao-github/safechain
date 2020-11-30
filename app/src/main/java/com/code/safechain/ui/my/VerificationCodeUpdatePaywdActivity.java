package com.code.safechain.ui.my;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.RegistConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.presenter.RegistPresenter;
import com.code.safechain.ui.login.UpdatePwdActivity;
import com.code.safechain.ui.login.bean.CountryCodeBean;
import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.ui.my.bean.CheckVerifiCodeRs;
import com.code.safechain.utils.RxUtils;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class VerificationCodeUpdatePaywdActivity extends BaseActivity<RegistConstract.Presenter>
        implements RegistConstract.View {

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.txt_send_code)
    TextView mTxtSendCode;


    //5分钟换算成毫秒  60
    private int timeStemp = 60;
    private Timer mTimer;
    private String mType = "1";//1手机 2邮箱
    private String mToken;

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
        //得到token
        mToken = SpUtils.getInstance(this).getString(Constants.TOKEN);
        mEtPhone.setEnabled(false);//设置账号不能修改
        String account = SpUtils.getInstance(this).getString(Constants.ACCOUNT);//得到登录账号
        mEtPhone.setText(account);//显示账号
        if(SystemUtils.isMobile(account)) {//是手机号，封装手机号信息
            mType = "1";
        }else {//邮箱
            mType = "2";
        }
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
            //先验证 验证码是否正确，，调用接口，，不正确则提示，，正确跳转到 设置验证码页面
                checkVerifiCode();//验证 验证码
                break;
        }
    }

    private void checkVerifiCode() {
        //得到验证码
        String veriCode = mEtCode.getText().toString().trim();
        if(TextUtils.isEmpty(veriCode) || veriCode.length()!=6){
            ToastUtil.showShort("验证码不合法！");
            return;
        }
        //得到手机号 或 邮箱
        String phone = mEtPhone.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            ToastUtil.showShort("账号不能为空!");
            return;
        }
        //封装数据到Map
        HashMap<String, Object> map = new HashMap<>();
        map.put("type",Integer.parseInt(mType));//类别
        map.put("token",mToken);
        map.put("sms_code",veriCode);
        if(mType.equals("1")){//是手机号，封装手机号信息
            map.put("phone",phone);
            map.put("nation","86");
        }else {//不是手机号，验证是否是邮箱
            map.put("email",phone);
        }
        //加密
        map = SystemUtils.getMap(map);//得到签名后的数据作为sign的值的json串

        HttpManager.getInstance().getApiServer().checkVerifiCode(map)
                .compose(RxUtils.<CheckVerifiCodeRs>changeScheduler())
                .subscribe(new Observer<CheckVerifiCodeRs>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CheckVerifiCodeRs checkVerifiCodeRs) {
                        if(checkVerifiCodeRs.getError() == 0){
                            if(checkVerifiCodeRs.getResult().isCheck_state()){
                                //跳转到设置页面
                                startActivity(new Intent(VerificationCodeUpdatePaywdActivity.this, SetGestureActivity.class));
                                finish();
                            }else {
                                Toast.makeText(VerificationCodeUpdatePaywdActivity.this, "验证码错误！", Toast.LENGTH_SHORT).show();
                            }
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

    private void sendVerifiCode() {
        //封装数据到Map
        HashMap<String, Object> map = new HashMap<>();
        //得到手机号 或 邮箱
        String phone = mEtPhone.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            ToastUtil.showShort("账号不能为空!");
            return;
        }
        map.put("type",mType);
        if(mType.equals("1")){//是手机号，封装手机号信息
            map.put("phone",phone);
            map.put("nation","86");
        }else {//不是手机号，验证是否是邮箱
            map.put("email",phone);
        }
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
