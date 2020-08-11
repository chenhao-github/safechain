package com.code.safechain.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.code.safechain.R;
import com.code.safechain.ui.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplushActivity extends AppCompatActivity {
    private int n = 2;
    private Timer mTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splush);
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
