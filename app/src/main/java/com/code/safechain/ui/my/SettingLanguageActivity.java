package com.code.safechain.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.code.safechain.R;
import com.code.safechain.ui.main.MainActivity;
import com.code.safechain.utils.LocalManageUtil;
import com.code.safechain.utils.SpUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingLanguageActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.img_system_checked0)
    ImageView mImgSystemChecked0;
    @BindView(R.id.rl_language_system)
    RelativeLayout mRlLanguageSystem;
    @BindView(R.id.img_system_checked1)
    ImageView mImgSystemChecked1;
    @BindView(R.id.rl_language_en)
    RelativeLayout mRlLanguageEn;
    @BindView(R.id.img_system_checked2)
    ImageView mImgSystemChecked2;
    @BindView(R.id.rl_language_zh)
    RelativeLayout mRlLanguageZh;

    private int lastLanguage = 0;//默认是系统

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_language);
        ButterKnife.bind(this);

        //获得设置的语言，把页面中对应的语言打钩
        setSetedLanguage(SpUtils.getInstance(this).getSelectLanguage());

    }

    private void setSetedLanguage(int selectLanguage) {
        if (selectLanguage == 0){
            mImgSystemChecked0.setImageResource(R.mipmap.icon_my_language_checked);
        }else if(selectLanguage == 1){
            mImgSystemChecked1.setImageResource(R.mipmap.icon_my_language_checked);
        }else if(selectLanguage == 2){
            mImgSystemChecked2.setImageResource(R.mipmap.icon_my_language_checked);
        }
    }

    @OnClick({R.id.img_back, R.id.rl_language_system, R.id.rl_language_en, R.id.rl_language_zh})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.rl_language_system:
                switchLanguage(0);//切换语言的选中状态，并切换本app的语言
                break;
            case R.id.rl_language_en:
                switchLanguage(1);
                break;
            case R.id.rl_language_zh:
                switchLanguage(2);
                break;
        }
    }

    private void switchLanguage(int i) {
        if(i == lastLanguage){//点击当前设置的语言，没有任何变化
            return;
        }
        if(i == 0){//系统默认
            mImgSystemChecked0.setImageResource(R.mipmap.icon_my_language_checked);
            mImgSystemChecked1.setImageBitmap(null);
            mImgSystemChecked2.setImageBitmap(null);
        }else if(i == 1){//英文
            mImgSystemChecked1.setImageResource(R.mipmap.icon_my_language_checked);
            mImgSystemChecked0.setImageBitmap(null);
            mImgSystemChecked2.setImageBitmap(null);
        }else {//中文
            mImgSystemChecked2.setImageResource(R.mipmap.icon_my_language_checked);
            mImgSystemChecked0.setImageBitmap(null);
            mImgSystemChecked1.setImageBitmap(null);
        }
        LocalManageUtil.saveSelectLanguage(i);
        lastLanguage = i;//记录当前点击的语言，作为下次的参照
        reStartApp();
    }

    /**
     * 设置完语言后，调用此方法 重启app加载设置的语言
     */
    public void reStartApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Process.killProcess(Process.myPid());
    }
}
