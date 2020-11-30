package com.code.safechain.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.code.safechain.R;
import com.code.safechain.app.BaseApp;
import com.code.safechain.common.Constants;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.login.LoginActivity;
import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.ui.main.bean.UserBean;
import com.code.safechain.utils.DeviceIdFactory;
import com.code.safechain.utils.RxUtils;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SplushActivity extends AppCompatActivity {
    private int n = 2;
    private Timer mTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splush);

        //判断3天内是否登录过
        boolean rs = checkLogin();
        if(rs){//直接登录
           login();
        }else {
            //倒计时
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    n--;
                    if(n==0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(SplushActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
                        mTimer.cancel();
                    }
                }
            },1000,1000);
        }
    }

    private void login() {
        //封装数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        //得到登录信息
        String account = SpUtils.getInstance(this).getString(Constants.ACCOUNT);
        String password = SpUtils.getInstance(this).getString(Constants.PASSWORD);
        String login_type = SpUtils.getInstance(this).getString(Constants.LOGIN_TYPE);
        map.put("type",login_type);
        if(login_type.equals("1")){//是手机号，封装手机号信息
            map.put("phone", account);
            map.put("nation","+86");
        }else {//不是手机号，验证是否是邮箱
            map.put("email", account);
        }
        map.put("passwd", password);
        map.put("device_type",2);
        map.put("device_id", DeviceIdFactory.getInstance(this).getDeviceUuid());
        //加密
        String json = SystemUtils.getJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        //
        HttpManager.getInstance().getApiServer().login(body)
                .compose(RxUtils.<RegistRsBean>changeScheduler())
                .subscribe(new Observer<RegistRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegistRsBean registRsBean) {
                        if(registRsBean.getError() == 0){
                            String token = registRsBean.getResult().getToken();
                            SpUtils.getInstance(SplushActivity.this).setValue(Constants.TOKEN, token);//保存登录token
                            SpUtils.getInstance(SplushActivity.this).setValue(Constants.LOGIN_TIME,new Date().getTime());

                            startActivity(new Intent(SplushActivity.this,MainActivity.class));
                            finish();
                        }else {
                            ToastUtil.showShort("账号密码不正确!");
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

    private boolean checkLogin() {
        long nd = 1000 * 3 * 24 * 60 * 60;//3天
        //得到登录信息
        String account = SpUtils.getInstance(this).getString(Constants.ACCOUNT);
//        String password = SpUtils.getInstance(this).getString(Constants.PASSWORD);
//        String login_type = SpUtils.getInstance(this).getString(Constants.LOGIN_TYPE);
        long time = SpUtils.getInstance(this).getLong(Constants.LOGIN_TIME);
        if(TextUtils.isEmpty(account)){//没有登录账号，进行登录
            return false;
        }else {//有登录账号，判断登录时间
            long n = new Date().getTime() - time;//得到现在时间和上次登录时间的时差
            if(n < nd){//小于3天，自动登录
                return true;
            }else {//大于3天，重新登录
                return false;
            }
        }

    }
}
