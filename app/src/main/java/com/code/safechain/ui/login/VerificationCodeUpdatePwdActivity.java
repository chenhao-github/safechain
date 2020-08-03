package com.code.safechain.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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

import butterknife.BindView;
import butterknife.OnClick;

public class VerificationCodeUpdatePwdActivity extends BaseActivity<RegistConstract.Presenter>
        implements RegistConstract.View {

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
    private String mPhone;

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
        mPhone = mEtPhone.getText().toString().trim();//得到手机号
//        String phoneFix = mSpPhoneFix的选中值;
        String phoneFix = "+86";
        if(!TextUtils.isEmpty(mPhone)){
            // 封装数据
            HashMap<String, Object> map = new HashMap<>();
            map.put("type",1);
            map.put("phone", mPhone);
            map.put("nation",phoneFix);
            //加密
            String json = SystemUtils.getJson(map);//得到签名后的数据作为sign的值的json串
            presenter.sendVerifiCode(json);
        }else {
            ToastUtil.showShort(BaseApp.getRes().getString(R.string.regist_phone_not_null));
        }
    }

    private void toUpdatePwdActivity() {
        //网络请求判断验证码
        //得到验证码
        String veriCode = mEtCode.getText().toString().trim();
        if(TextUtils.isEmpty(veriCode) || veriCode.length()!=6){
            ToastUtil.showShort("验证码不合法！");
            return;
        }
        //验证码没问题，跳转到重置页面
        Intent intent = new Intent(this, UpdatePwdActivity.class);
        intent.putExtra(Constants.PHONE_NUMBER,mPhone);
        intent.putExtra(Constants.VERIFICODE,veriCode);
        startActivity(intent);
    }

    @Override
    public void sendVerifiCodeReturn() {

    }

    @Override
    public void sendPwdReturn(RegistRsBean registRsBean) {

    }
}
