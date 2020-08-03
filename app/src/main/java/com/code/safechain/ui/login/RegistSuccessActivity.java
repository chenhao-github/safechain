package com.code.safechain.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.code.safechain.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistSuccessActivity extends AppCompatActivity {

    @BindView(R.id.btn_goto_login)
    Button mBtnGotoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_success);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_goto_login)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_goto_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
