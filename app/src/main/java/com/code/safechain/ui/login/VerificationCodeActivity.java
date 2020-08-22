package com.code.safechain.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.code.safechain.R;
import com.code.safechain.app.BaseApp;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.LoginConstract;
import com.code.safechain.interfaces.RegistConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.presenter.LoginPresenter;
import com.code.safechain.presenter.RegistPresenter;
import com.code.safechain.ui.login.adapter.CountryCodeAdapter;
import com.code.safechain.ui.login.bean.CountryCodeBean;
import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.ui.main.SplushActivity;
import com.code.safechain.utils.RxUtils;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class VerificationCodeActivity extends BaseActivity<RegistConstract.Presenter> implements RegistConstract.View {

    @BindView(R.id.sp_country)
    Spinner mSpCountry;
    @BindView(R.id.txt_country_code)
    TextView mTxtCountryCode;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.txt_send_code)
    TextView mTxtSendCode;

    private ArrayList<String> mCountrysList;
    private CountryCodeAdapter countryAdapter;
    private ArrayList<String> mFixsList;
    private ArrayAdapter<String> mFixAdapter;

    //5分钟换算成毫秒  60
    private int timeStemp = 60;
    private Timer mTimer;
    private int mCountryCode;

    @Override
    protected int getLayout() {
        return R.layout.activity_verification_code;
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
        //获得国家代码
        getCountryCode();
    }

    private void getCountryCode() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type",4);
        map = SystemUtils.getMap(map);
        HttpManager.getInstance().getApiServer().getCountryCode(map)
                .compose(RxUtils.<CountryCodeBean>changeScheduler())
                .subscribe(new Observer<CountryCodeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CountryCodeBean countryCodeBean) {
                        List<CountryCodeBean.ResultBean> result = countryCodeBean.getResult();
                        initSpinnerCountry(result);//配置国家信息
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initSpinnerCountry(final List<CountryCodeBean.ResultBean> result) {
        //定义适配器
        countryAdapter = new CountryCodeAdapter(this,result);
        //设置下拉列表下拉时的菜单样式
//        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将适配器添加到下拉列表上
        mSpCountry.setAdapter(countryAdapter);
        //设置默认中国选中
        int i = 0;
        mCountryCode = 0;
        for (int j = 0; j < result.size(); j++) {
            if(result.get(j).getCname().equals("中国")){
                i = j;
                mCountryCode = result.get(i).getTel_code();
                break;
            }
        }
        //设置中国默认选中
        mSpCountry.setSelection(i,true);
        mTxtCountryCode.setText("+"+ mCountryCode);

        //条目点击监听
        mSpCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCountryCode = result.get(position).getTel_code();
                mTxtCountryCode.setText("+"+ mCountryCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
   /* private void initSpinnerPhoneFix() {
        //创建数据源
        mFixsList = new ArrayList<>();
        mFixsList.add("+86");
        mFixsList.add("+96");
        //定义适配器
        mFixAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mFixsList);
        //设置下拉列表下拉时的菜单样式
        mFixAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将适配器添加到下拉列表上
        mSpPhoneFix.setAdapter(mFixAdapter);

    }*/


    @OnClick({R.id.img_back, R.id.txt_send_code, R.id.txt_regist_email, R.id.txt_goto_login, R.id.btn_next})
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
            case R.id.txt_regist_email:
                startActivity(new Intent(this,VerificationCodeEmailActivity.class));
                break;
            case R.id.txt_goto_login:
                finish();
                break;
            case R.id.btn_next:
                toSetPwdActivity();
                break;
        }
    }

    private void sendVerifiCode() {
        //验证手机号
        String phoneNum = mEtPhone.getText().toString().trim();//得到手机号
        if(!checkPhone(phoneNum)){//验证手机号  不合法
            return;
        }
        // 封装数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("type",1);
        map.put("phone", phoneNum);
        map.put("nation",mCountryCode);//设置国家编号
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

    private boolean checkPhone(String phoneNum) {
        if(TextUtils.isEmpty(phoneNum)){
            Toast.makeText(this, "手机号不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(phoneNum.length() != 11){
            Toast.makeText(this, "手机号不合法！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void toSetPwdActivity() {
        String phoneNum = mEtPhone.getText().toString().trim();//得到手机号
        if(!checkPhone(phoneNum)){//验证手机号  不合法
            return;
        }
        //得到验证码
        String veriCode = mEtCode.getText().toString().trim();
        if(TextUtils.isEmpty(veriCode) || veriCode.length()!=6){
            ToastUtil.showShort("验证码不合法！");
            return;
        }
        Intent intent = new Intent(this, SetPwdActivity.class);
        intent.putExtra(Constants.PHONE_NUMBER,phoneNum);//电话号码
        intent.putExtra(Constants.VERIFICODE,veriCode);//验证码
        intent.putExtra(Constants.NATION,mCountryCode);//国家代码
        intent.putExtra(Constants.DATA,1);//手机 设置密码
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
