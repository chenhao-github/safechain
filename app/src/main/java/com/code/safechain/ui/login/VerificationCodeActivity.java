package com.code.safechain.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.LoginConstract;
import com.code.safechain.interfaces.RegistConstract;
import com.code.safechain.presenter.LoginPresenter;
import com.code.safechain.presenter.RegistPresenter;
import com.code.safechain.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class VerificationCodeActivity extends BaseActivity<RegistConstract.Presenter> implements RegistConstract.View {

    @BindView(R.id.sp_country)
    Spinner mSpCountry;
    @BindView(R.id.sp_phone_fix)
    Spinner mSpPhoneFix;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;

    private ArrayList<String> mCountrysList;
    private ArrayAdapter<String> countryAdapter;
    private ArrayList<String> mFixsList;
    private ArrayAdapter<String> mFixAdapter;

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
        //得到传过来的账号和密码
        String account = getIntent().getStringExtra(Constants.ACCOUNT);
        String pws = getIntent().getStringExtra(Constants.PASSWORD);
        //初始化spinner
        initSpinnerCountry();
        initSpinnerPhoneFix();
        //初始化手机号输入框



    }

    private void initSpinnerCountry() {
        //创建数据源
        mCountrysList = new ArrayList<>();
        mCountrysList.add("中国");
        mCountrysList.add("迪拜");
        mCountrysList.add("美国");
        //定义适配器
        countryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mCountrysList);
        //设置下拉列表下拉时的菜单样式
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将适配器添加到下拉列表上
        mSpCountry.setAdapter(countryAdapter);
    }
    private void initSpinnerPhoneFix() {
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

    }


    @OnClick({R.id.img_back, R.id.txt_send_code, R.id.txt_regist_email, R.id.txt_goto_login, R.id.btn_next})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_send_code:
                ToastUtil.showShort("发送验证码");
                break;
            case R.id.txt_regist_email:
                ToastUtil.showShort("邮箱注册");
                break;
            case R.id.txt_goto_login:
                finish();
                break;
            case R.id.btn_next:
                toSetPwdActivity();
                break;
        }
    }

    private void toSetPwdActivity() {
        Intent intent = new Intent(this, SetPwdActivity.class);

        startActivity(intent);
    }

    @Override
    public void sendVerifiCodeReturn() {

    }

    @Override
    public void sendPwdReturn() {

    }
}
