package com.code.safechain.ui.my;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.app.BaseApp;
import com.code.safechain.base.BaseFragment;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.MyConstract;
import com.code.safechain.presenter.MyPresenter;
import com.code.safechain.utils.APKVersionCodeUtils;
import com.code.safechain.utils.LocalManageUtil;
import com.code.safechain.utils.LoggerUtil;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.img_header_change)//上传头像图片
    ImageView imgHeaderChange;
    @BindView(R.id.rl_realname)//实名认证行
    RelativeLayout rlRealname;
    @BindView(R.id.txt_gesture_rs)//手势秘密的 未设置/已设置
    TextView txtGestureRs;
    @BindView(R.id.rl_suggestion)//意见反馈
    RelativeLayout rlSuggestion;
    @BindView(R.id.txt_version_rs)//版本号
    TextView txtVersionRs;

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

    }

    @Override
    protected void initData() {
        //设置版本号
        String versionName = APKVersionCodeUtils.getVerName(getActivity());
        txtVersionRs.setText(versionName);
        //获取语言，并右上角显示图标

        //得到是否设置手势密码
        setGestureRsTxt();

    }
    //得到是否设置手势密码
    private void setGestureRsTxt() {
        String paywd = SpUtils.getInstance(getActivity()).getString(Constants.PAYWD);
        if(!TextUtils.isEmpty(paywd)){//已设置
            txtGestureRs.setText(BaseApp.getRes().getString(R.string.my_gesture_set));
        }else {
            txtGestureRs.setText(BaseApp.getRes().getString(R.string.my_gesture_noset));
        }
    }


    @Override
    public void uploadHeaderReturn() {
        //上传头像的回传
    }

    @OnClick({R.id.img_header_change, R.id.rl_realname, R.id.rl_gesture, R.id.rl_aboutme,
            R.id.rl_suggestion, R.id.rl_version, R.id.rl_update, R.id.rl_paytype})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_change:
                break;
            case R.id.rl_realname:
                //跳转到实名认证页面
                toActivity(RealNameActivity.class);
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
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == getActivity().RESULT_OK){
            setGestureRsTxt();//设置手势密码 显示 是否设置
        }
    }

    //跳转页面
    private void toActivity(Class cls) {
        startActivity(new Intent(getActivity(), cls));
    }
}
