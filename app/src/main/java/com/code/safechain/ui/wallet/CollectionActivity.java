package com.code.safechain.ui.wallet;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.code.safechain.model.HttpManager;
import com.code.safechain.presenter.WalletPresenter;
import com.code.safechain.ui.wallet.bean.WalletAddressOnlyRsBean;
import com.code.safechain.ui.wallet.bean.WalletAddressRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;
import com.code.safechain.utils.RxUtils;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;
import com.code.safechain.utils.ZXingUtils;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class CollectionActivity extends BaseActivity<WalletConstract.Presenter> implements WalletConstract.View {
    @BindView(R.id.rl_top)
    RelativeLayout mRlTop;
    @BindView(R.id.img_qr_code)
    ImageView mImgQrCode;
    @BindView(R.id.txt_wallet_address)
    TextView mTxtWalletAddress;
    @BindView(R.id.txt_wallet_copy)
    TextView mTxtWalletCopy;

    private WalletHomeRsBean.ResultBean.DataBean mDataBean;
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
        mDataBean = (WalletHomeRsBean.ResultBean.DataBean) getIntent().getSerializableExtra(Constants.DATA);
//        mTxtWalletAddress.setText(mDataBean.getAddr());
//        //生成二维码，并显示
//        Bitmap bitmap = ZXingUtils.createQRImage(mDataBean.getAddr(), 123, 123);
//        mImgQrCode.setImageBitmap(bitmap);
    }

    @Override
    protected void initData() {
        //封装数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        map.put("token_id", mDataBean.getToken_id());
        map = SystemUtils.getMap(map);

        HttpManager.getInstance().getApiServer().getWalletAddressOnly(map)
                .compose(RxUtils.<WalletAddressOnlyRsBean>changeScheduler())
                .subscribe(new Observer<WalletAddressOnlyRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WalletAddressOnlyRsBean walletAddressOnlyRsBean) {
                        mTxtWalletAddress.setText(walletAddressOnlyRsBean.getResult().getToken_addr());
                        //生成二维码，并显示
                        Bitmap bitmap = ZXingUtils.createQRImage(walletAddressOnlyRsBean.getResult().getToken_addr(), 123, 123);
                        mImgQrCode.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        String s = "";
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick({R.id.img_back, R.id.ll_share, R.id.ll_copy, R.id.ll_set_money, R.id.txt_wallet_copy})
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
            case R.id.ll_copy://复制到剪贴板
                setSysClipboardText();
                break;
            case R.id.ll_set_money:
                Toast.makeText(this, "设置金额", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void setSysClipboardText() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", mDataBean.getAddr());
// 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        ToastUtil.showShort("复制成功");
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
