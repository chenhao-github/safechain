package com.code.safechain.ui.transaction;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.TransactionConstract;
import com.code.safechain.interfaces.TransactionOrdersConstract;
import com.code.safechain.presenter.TransOrderPresenter;
import com.code.safechain.presenter.TransactionPresenter;
import com.code.safechain.ui.transaction.bean.OrderRsBean;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity<TransactionOrdersConstract.Presenter>
        implements TransactionOrdersConstract.View {

    @BindView(R.id.img_payment)//右上角付款图标
    ImageView imgPayment;
    @BindView(R.id.txt_time_remaining)//倒计时
    TextView mTxtTimeRemaining;
    @BindView(R.id.txt_payment_chain_name)//币的名字
    TextView mTxtPaymentChainName;
    @BindView(R.id.txt_total_price)//总价
    TextView mTxtTotalPrice;
    @BindView(R.id.txt_unit_price)//单价
    TextView mTxtUnitPrice;
    @BindView(R.id.txt_number)//数量
    TextView mTxtNumber;
    @BindView(R.id.txt_order_number)//订单号
    TextView mTxtOrderNumber;
    @BindView(R.id.txt_seller_nickname)//卖家昵称
    TextView mTxtSellerNickName;
    @BindView(R.id.img_paytype)//支付图标
    ImageView mImgPaytype;



    @BindView(R.id.btn_cancel_order)//取消订单
    Button btnCancelOrder;
    @BindView(R.id.btn_to_pay)//去付款
    Button btnToPay;
    private OrderRsBean.ResultBean mOrder;

    //12分钟换算成毫秒
    private int timeStemp = 720000;
    private CountDownTimer timer;

    @Override
    protected int getLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected TransactionOrdersConstract.Presenter createPresenter() {
        return new TransOrderPresenter();
    }

    @Override
    protected void initView() {
        mOrder = (OrderRsBean.ResultBean) getIntent().getSerializableExtra("order");

        //赋值
        mTxtTotalPrice.setText(mOrder.getTotal());
        mTxtUnitPrice.setText(mOrder.getPrice());
        mTxtNumber.setText(mOrder.getNum());
        mTxtOrderNumber.setText(mOrder.getOrder_no());
        if(mOrder.getPay_type() == 1){
            mImgPaytype.setImageResource(R.mipmap.ic_add_paytype_wechat_select);
        }
        if(mOrder.getPay_type() == 2){
            mImgPaytype.setImageResource(R.mipmap.ic_add_paytype_alipay_select);
        }
        if(mOrder.getPay_type() == 4){
            mImgPaytype.setImageResource(R.mipmap.ic_add_paytype_unionpay_select);
        }

        getCountDownTime();//调用倒计时

    }
    private void getCountDownTime() {
        timer = new CountDownTimer(timeStemp, 1000) {
            @Override
            public void onTick(long l) {

                long day = l / (1000 * 24 * 60 * 60); //单位天
                long hour = (l - day * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60); //单位时
                long minute = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60)) / (1000 * 60); //单位分
                long second = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;//单位秒

                mTxtTimeRemaining.setText(minute + ":" +second  );
//                mTxtCountDown.setText(hour + ":" + minute + ":" + second );
                Log.i("111", "onTick: "+minute + ":" + second);
            }

            @Override
            public void onFinish() {
                //倒计时为0时执行此方法
                finish();//结束页面，取消订单
            }
        }.start();
    }

    @Override
    protected void initData() {


//        HashMap<String, Object> map = new HashMap<>();
//        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
//        map.put("role_type",1);//1买家  2卖家
//        map.put("order_no", mOrder.getOrder_no());//详情用订单号
//        map = SystemUtils.getMap(map);
//        presenter.getOrders(map);
    }

    @OnClick({R.id.img_back, R.id.img_payment, R.id.btn_cancel_order, R.id.btn_to_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_payment:
                break;
            case R.id.btn_cancel_order:
                ToastUtil.showShort("取消订单");
                break;
            case R.id.btn_to_pay:
                ToastUtil.showShort("去付款");
                break;
        }
    }

    @Override
    public void getOrdersReturn(OrderRsBean orderRsBean) {
        String s = "";
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();//停止，关闭倒计时
    }
}
