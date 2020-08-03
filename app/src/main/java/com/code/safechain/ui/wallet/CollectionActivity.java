package com.code.safechain.ui.wallet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.WalletConstract;
import com.code.safechain.presenter.WalletPresenter;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectionActivity extends BaseActivity<WalletConstract.Presenter> implements WalletConstract.View {
    @BindView(R.id.rl_top)
    RelativeLayout mRlTop;
    private PopupWindow mPw;

    @Override
    protected int getLayout() {
        return R.layout.activity_collection;
    }

    @Override
    protected WalletConstract.Presenter createPresenter() {
        return new WalletPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_back, R.id.ll_share, R.id.ll_copy, R.id.ll_set_money})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
            case R.id.ll_share:
                //点击分享弹出pw：显示黑窗 和 小二维码两个按钮 分享和 取消
                showPw();
                break;
            case R.id.ll_copy:
                Toast.makeText(this, "复制", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_set_money:
                Toast.makeText(this, "设置金额", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showPw() {
        View view = View.inflate(this,R.layout.popup_share,null);
        getPwView(view);//得到pw界面组件
        //设置合适宽高
        mPw = new PopupWindow(view, 960, 1380);
        SystemUtils.setBackgroundAlpha(getWindow(), Constants.SHADOW);//设置屏幕透明度，具有半透明效果
        mPw.showAsDropDown(mRlTop,59,65);

    }

    private void getPwView(View view) {
        Button cancle = view.findViewById(R.id.btn_cancel);
        Button share = view.findViewById(R.id.btn_share);
        ButtonClickLis lis = new ButtonClickLis();//创建监听
        //添加监听
        cancle.setOnClickListener(lis);
        share.setOnClickListener(lis);
    }
    class ButtonClickLis implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_cancel){
                mPw.dismiss();//关闭pw
                SystemUtils.setBackgroundAlpha(getWindow(),Constants.NO_SHADOW);//设置背景不透明
            }else if(v.getId() == R.id.btn_share){
                //实现微信  QQ 微博分享
                ToastUtil.showShort("微信  QQ 微博分享");


            }
        }
    }


}
