package com.code.safechain.ui.my;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.app.BaseApp;
import com.code.safechain.base.BaseFragment;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.MyConstract;
import com.code.safechain.presenter.MyPresenter;
import com.code.safechain.ui.login.LoginActivity;
import com.code.safechain.ui.login.VerificationCodeActivity;
import com.code.safechain.ui.main.bean.UserBean;
import com.code.safechain.ui.my.bean.GestureRsBean;
import com.code.safechain.ui.my.bean.UploadIconRsBean;
import com.code.safechain.utils.APKVersionCodeUtils;
import com.code.safechain.utils.LocalManageUtil;
import com.code.safechain.utils.LoggerUtil;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * @Auther: hchen
 * @Date: 2020/7/4 0004
 * @Description:
 * LocalManageUtil.saveSelectLanguage(this, 1);//保存选择的语言
 * //重启APP到主页面
 * MainActivity activity = (MainActivity) getActivity();
 * activity.reStartApp();
 */
public class MyFragment extends BaseFragment<MyConstract.Presenter> implements MyConstract.View {
    @BindView(R.id.img_language)
    ImageView mImgLanguage;
    @BindView(R.id.rl_header)//上面的头像区域
    RelativeLayout mRlHeader;
    @BindView(R.id.img_header_change)//上传头像图片
    ImageView imgHeaderChange;
    @BindView(R.id.txt_name)//名字
     TextView mTxtName;
    @BindView(R.id.ll_regist_login)//登录注册区域
    LinearLayout mLlRegistLogin;

    @BindView(R.id.rl_realname)//实名认证行
    RelativeLayout rlRealname;
    @BindView(R.id.txt_realname_rs)//实名认证 未设置/已设置
    TextView txtRealNameRs;

    @BindView(R.id.txt_gesture_rs)//手势秘密的 未设置/已设置
    TextView txtGestureRs;

    @BindView(R.id.rl_suggestion)//意见反馈
    RelativeLayout rlSuggestion;
    @BindView(R.id.txt_version_rs)//版本号
    TextView txtVersionRs;
    @BindView(R.id.txt_user_statis)//用户状态
    TextView txtUserStatis;


    @Override
    protected int getLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected MyConstract.Presenter createPresenter() {
        return new MyPresenter();
    }

    @Override
    protected void initView() {
        showLoginState();//显示登录状态
    }

    //控制登录状态的显示隐藏
    private void showLoginState() {
        //得到token
        String token = SpUtils.getInstance(getActivity()).getString(Constants.TOKEN);
        if(TextUtils.isEmpty(token)){//token为空，没有登录，或者退出登录，显示 注册，登录按钮
            mLlRegistLogin.setVisibility(View.VISIBLE);//显示 注册登录区域
            //隐藏头像，名字
            mRlHeader.setVisibility(View.INVISIBLE);
            mTxtName.setVisibility(View.INVISIBLE);
        }else {
            mLlRegistLogin.setVisibility(View.INVISIBLE);//隐藏 注册登录区域
            //显示头像，名字
            mRlHeader.setVisibility(View.VISIBLE);
            mTxtName.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
//        showLanguage();//显示语言
        //获得设置的语言，把页面中对应的语言打钩
        setSetedLanguage(SpUtils.getInstance(getActivity()).getSelectLanguage());
        //设置用户名
        if(!TextUtils.isEmpty(BaseApp.userBean.getResult().getPhone()))
            mTxtName.setText(BaseApp.userBean.getResult().getPhone());
        if(!TextUtils.isEmpty(BaseApp.userBean.getResult().getEmail()))
            mTxtName.setText(BaseApp.userBean.getResult().getEmail());

        //设置版本号
        String versionName = APKVersionCodeUtils.getVerName(getActivity());
        txtVersionRs.setText(versionName);
        //获取语言，并右上角显示图标

        //得到是否设置手势密码
//        setGestureRsTxt();
        //设置用户信息
        setUserInfo();

        //获得用户信息
//        getUserData();
    }


    private void setSetedLanguage(int selectLanguage) {
        if (selectLanguage == 0){//没有设置自己的语言，显示系统语言
            showLanguage();
        }else if(selectLanguage == 1){//本app设置的英文
            mImgLanguage.setImageResource(R.mipmap.icon_my_language_en);
        }else if(selectLanguage == 2){//本app设置的中文
            mImgLanguage.setImageResource(R.mipmap.icon_my_language_zh);
        }
    }
    private void showLanguage() {
        Locale locale = LocalManageUtil.getSystemLocale(getActivity());//获得手机系统语言
        String country = locale.getCountry();
        if(country.equals("CN")){//中文
            mImgLanguage.setImageResource(R.mipmap.icon_my_language_zh);
        }else {//不是中文，则全为英文
            mImgLanguage.setImageResource(R.mipmap.icon_my_language_en);
        }
    }

    private void setUserInfo() {
        //设置实名认证
        if(!TextUtils.isEmpty(BaseApp.userBean.getResult().getCard_id())){
            txtRealNameRs.setText(BaseApp.getRes().getString(R.string.my_realname_yes));
        }
        //设置手势密码
        if(BaseApp.userBean.getResult().getHas_paywd() == 0){
            txtGestureRs.setText(BaseApp.getRes().getString(R.string.my_gesture_set));
        }
        //设置用户排名
        txtUserStatis.setText("您是第"+BaseApp.userBean.getResult().getPk_user()+"位用户，共"
                +BaseApp.userBean.getResult().getTotal()+"位用户");
    }

   /* private void getUserData() {
        //封装数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("token",SpUtils.getInstance(getActivity()).getString(Constants.TOKEN));
        map = SystemUtils.getMap(map);
        presenter.getUserData(map);
    }*/

    @OnClick({R.id.img_header_change, R.id.img_language, R.id.rl_realname, R.id.rl_gesture, R.id.rl_aboutme,
            R.id.rl_suggestion, R.id.rl_version, R.id.rl_paytype, R.id.rl_loginout, R.id.btn_regist,
    R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_change:
                break;
            case R.id.img_language:
                //设置到手势密码设置界面
                startActivityForResult(new Intent(getActivity(),SettingLanguageActivity.class),300);
                break;
            case R.id.rl_realname:
                //跳转到实名认证页面
                startActivityForResult(new Intent(getActivity(), RealNameActivity.class),200);
                break;
            case R.id.rl_aboutme:
                //跳转到关于我们页面
                toActivity(AboutMeActivity.class);
                break;
            case R.id.rl_suggestion:
                //跳转到意见反馈页面
                toActivity(SuggestionActivity.class);
                break;
            case R.id.rl_gesture:
                //设置到手势密码设置界面
                startActivityForResult(new Intent(getActivity(),SetGestureActivity.class),100);
                break;
            case R.id.rl_paytype:
                //跳转到支付方式设置页面
                startActivity(new Intent(getActivity(), PayTypeActivity.class));
                break;
            case R.id.rl_loginout: //登出
                SpUtils.getInstance(getActivity()).setValue(Constants.TOKEN,"");
                showLoginState();
                break;
            case R.id.btn_regist: //跳转到注册 发送验证码页面
                startActivity(new Intent(getActivity(), VerificationCodeActivity.class));
                getActivity().finish();
                break;
            case R.id.btn_login: //跳转到登录界面
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == getActivity().RESULT_OK){//设置手势密码成功
            txtGestureRs.setText(BaseApp.getRes().getString(R.string.my_gesture_set));
        }
        if(requestCode == 200 && resultCode == getActivity().RESULT_OK){//实名认证成功
            txtRealNameRs.setText(BaseApp.getRes().getString(R.string.my_realname_yes));
        }
        if(requestCode == 300 && resultCode == getActivity().RESULT_OK){//设置语言成功

        }

    }

    //跳转页面
    private void toActivity(Class cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivityForResult(intent,200);
    }

    @Override
    public void uploadHeaderReturn(UploadIconRsBean uploadIconRsBean) {

    }

    @Override
    public void updateUserDataReturn(GestureRsBean gestureRsBean) {

    }

    @Override
    public void getUserDataReturn(UserBean userBean) {
        String s = "";
    }
}
