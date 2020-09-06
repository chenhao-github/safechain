package com.code.safechain.ui.transaction;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.code.safechain.R;
import com.code.safechain.app.BaseApp;
import com.code.safechain.base.BaseFragment;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.TransactionConstract;
import com.code.safechain.presenter.TransactionPresenter;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Auther: hchen
 * @Date: 2020/7/4 0004
 * @Description:
 */
public class TransactionFragment extends BaseFragment<TransactionConstract.Presenter>
        implements TransactionConstract.View,View.OnClickListener {

    @BindView(R.id.txt_buy)
    TextView txtBuy;
    @BindView(R.id.txt_sale)
    TextView txtSale;
    @BindView(R.id.img_order)
    ImageView imgOrder;
    @BindView(R.id.img_filter)
    ImageView imgFilter;
    private FragmentManager mFm;
    public BuyFragment mBuy;
    private SaleFragment mSale;
    private PopupWindow mPw;
    private int type = 0;//0表示我要买  1表示我要卖

    @Override
    protected int getLayout() {
        return R.layout.fragment_transaction;
    }

    @Override
    protected TransactionConstract.Presenter createPresenter() {
        return new TransactionPresenter();
    }

    @Override
    protected void initView() {
        mFm = getChildFragmentManager();
        mBuy = new BuyFragment();
        mSale = new SaleFragment();
        //添加两个fragment
        mFm.beginTransaction()
                .add(R.id.fragment_contain, mBuy)
                .add(R.id.fragment_contain,mSale)
                .hide(mSale).commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        type = 0;//默认选中 我要买
    }

    @OnClick({R.id.txt_buy, R.id.txt_sale, R.id.img_order, R.id.img_filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_buy:
                type = 0;//选中 我要买
                dealBuySaleAndFragment(0);
                break;
            case R.id.txt_sale:
                type = 1;//选中 我要卖
                dealBuySaleAndFragment(1);
                break;
            case R.id.img_order:
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                intent.putExtra("type",type);
                startActivity(intent);
                break;
            case R.id.img_filter:
                showPopupwindow();//弹出筛选popupwindow
                break;
        }
    }

    /**
     * 处理 我要买和我要卖 的字体颜色，两个fragment的隐藏显示
     * @param i  0 是我要买 主导   1 我要卖 主导
     */
    private void dealBuySaleAndFragment(int i) {
        if(i == 0){//显示我要买
            //切换 我要买  我要卖 的字体大小和颜色
            txtBuy.setTextSize(20);
            txtBuy.setTextColor(getResources().getColor(R.color.colorMyNetWorkUrl));
            txtSale.setTextSize(17);
            txtSale.setTextColor(getResources().getColor(R.color.colorIdentity));
            mFm.beginTransaction().show(mBuy).hide(mSale).commit();
        }else {
            //切换到我要卖，先判断是否实名认证
            if(BaseApp.userBean!=null&&!TextUtils.isEmpty(BaseApp.userBean.getResult().getCard_id())){
                txtBuy.setTextSize(17);
                txtBuy.setTextColor(getResources().getColor(R.color.colorIdentity));
                txtSale.setTextSize(20);
                txtSale.setTextColor(getResources().getColor(R.color.colorMyNetWorkUrl));
                mFm.beginTransaction().show(mSale).hide(mBuy).commit();
            }else {
                ToastUtil.showShort("请先实名认证！");
            }
        }
    }

    //弹出筛选popupwindow
    private void showPopupwindow() {
        View pwLayout = View.inflate(getActivity(), R.layout.popup_trans_buy_filter, null);
        mPw = new PopupWindow(pwLayout, 960, 840);
        SystemUtils.setBackgroundAlpha(getActivity().getWindow(), Constants.SHADOW);//设置背景半透明效果
        mPw.showAtLocation(txtBuy, Gravity.CENTER,0,0);
        //获得 取消 筛选 按钮
        Button cancel = pwLayout.findViewById(R.id.btn_cancel);
        Button filter = pwLayout.findViewById(R.id.btn_filter);
        cancel.setOnClickListener(this);
        filter.setOnClickListener(this);

    }

    //关闭  筛选  按钮的监听
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_cancel){//关闭
            mPw.dismiss();
            SystemUtils.setBackgroundAlpha(getActivity().getWindow(), Constants.NO_SHADOW);
        }else if(v.getId() == R.id.btn_filter){//筛选
            //网络请求实现筛选
            Toast.makeText(context, "进行筛选", Toast.LENGTH_SHORT).show();
            mPw.dismiss();
            SystemUtils.setBackgroundAlpha(getActivity().getWindow(), Constants.NO_SHADOW);
        }
    }
}
