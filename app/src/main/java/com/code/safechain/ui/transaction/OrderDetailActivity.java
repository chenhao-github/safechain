package com.code.safechain.ui.transaction;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.TransactionConstract;
import com.code.safechain.interfaces.TransactionOrdersConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.presenter.TransOrderPresenter;
import com.code.safechain.presenter.TransactionPresenter;
import com.code.safechain.ui.transaction.bean.OrderRsBean;
import com.code.safechain.ui.transaction.bean.UpdateOrderRsBean;
import com.code.safechain.utils.RxUtils;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class OrderDetailActivity extends BaseActivity<TransactionOrdersConstract.Presenter>
        implements TransactionOrdersConstract.View {
    @BindView(R.id.txt_please_pay) //请付款
    TextView mTxtPleasePay;
    @BindView(R.id.img_payment)//右上角付款图标
    ImageView imgPayment;

    @BindView(R.id.txt_time_remaining_title)//倒计时 前提示语
    TextView mTxtTimeTitle;
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
    @BindView(R.id.txt_ctime)//下单时间
    TextView mTxtCtime;
    @BindView(R.id.txt_order_number)//订单号
    TextView mTxtOrderNumber;
    @BindView(R.id.txt_seller_nickname)//卖家昵称
    TextView mTxtSellerNickName;
    @BindView(R.id.img_paytype)//支付图标
    ImageView mImgPaytype;



    @BindView(R.id.btn_cancel_order)//取消订单
    Button btnCancelOrder;
    @BindView(R.id.btn_to_pay)//去付款  等待买家确认收款  取消的订单
    Button btnToPay;
    @BindView(R.id.btn_confirm_pay)//确认付款  等待买家付款
    Button btnConfirmPay;


    private OrderRsBean.ResultBean mOrder;

    //12分钟换算成毫秒
    private long allTime = 720000;
    private long timeStemp;
    private CountDownTimer timer;
    private int mType;
    private String mToken;

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
        mToken = SpUtils.getInstance(this).getString(Constants.TOKEN);
        mOrder = (OrderRsBean.ResultBean) getIntent().getSerializableExtra("order");
        mType = getIntent().getIntExtra("type", 0);//类别 0我要买  1我要卖

        //赋值
        mTxtTotalPrice.setText(mOrder.getTotal());
        mTxtUnitPrice.setText(mOrder.getPrice());
        mTxtNumber.setText(String.format("%.6f", Double.parseDouble(mOrder.getNum())));
        mTxtCtime.setText(mOrder.getCtime());
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
        //设置下方显示区域
        setBottomInfo();

        if(mType == 0){//我要买  进入
            mTxtPleasePay.setText(getResources().getString(R.string.transaction_please_pay));
            if(mOrder.getState() == 1){//未付款  显示倒计时
                //计算倒计时时间
                timeStemp = getMilliSecond(mOrder.getCtime());
                if(timeStemp > allTime){
                    Toast.makeText(this, "超过付款时间!", Toast.LENGTH_SHORT).show();
                }else {
                    mTxtTimeTitle.setVisibility(View.VISIBLE);
                    mTxtTimeRemaining.setVisibility(View.VISIBLE);
                    getCountDownTime();//调用倒计时
                }
            }
        }else if (mType == 1){//我要卖  进入
            mTxtPleasePay.setText(getResources().getString(R.string.transaction_please_pay_confirm));
        }

    }

    public static Long getMilliSecond(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long milliSecond = null;
        try {
            Date currentTime = dateFormat.parse(dateFormat.format(new Date()));//现在系统当前时间
            Date pastTime = dateFormat.parse(date);//过去时间
            long diff = currentTime.getTime() - pastTime.getTime();
            milliSecond = diff;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliSecond;
    }

    private void setBottomInfo() {
        if(mType == 0){//我要买 买家进来
            btnToPay.setVisibility(View.VISIBLE);  //显示 取消订单后的按钮
            if(mOrder.getState() == 1){//未付款，显示  取消订单和去付款
                mTxtPleasePay.setVisibility(View.VISIBLE);//显示 请付款
                btnCancelOrder.setVisibility(View.VISIBLE);//显示 取消订单
            }else if(mOrder.getState() == 2){//取消的订单，显示已取消
                btnToPay.setText(getResources().getString(R.string.transaction_order_failed));
            }else if(mOrder.getState() == 3){//已付款 显示等待卖家确认
                btnToPay.setText(getResources().getString(R.string.transaction_wait_confirm));
            }else if(mOrder.getState() == 4){//已完成 显示已完成
                btnToPay.setText(getResources().getString(R.string.transaction_order_completed));
            }
        }else {//我要卖  卖家进来
            //显示确认收款
            btnConfirmPay.setVisibility(View.VISIBLE);//显示卖家的按钮
            if(mOrder.getState() == 1){//未付款，显示  等待买家付款
                btnConfirmPay.setText(getResources().getString(R.string.transaction_wait_pay));
            }else if(mOrder.getState() == 2){//取消的订单，显示已取消
                btnConfirmPay.setText(getResources().getString(R.string.transaction_order_failed));
            }else if(mOrder.getState() == 3){//已付款 显示确认收款
                mTxtPleasePay.setVisibility(View.VISIBLE);//显示 请付款，改为请确认
                mTxtPleasePay.setText(getResources().getString(R.string.transaction_pay_confirm));
                btnConfirmPay.setText(getResources().getString(R.string.transaction_pay_confirm));
            }else if(mOrder.getState() == 4){//已完成 显示已完成
                btnToPay.setText(getResources().getString(R.string.transaction_order_completed));
            }
        }
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
                updateOrder(2);//取消订单
                finish();//结束页面
            }
        }.start();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_back, R.id.img_payment, R.id.btn_cancel_order, R.id.btn_to_pay, R.id.btn_confirm_pay})
    public void onViewClicked(View view) {
        String s = "";
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_payment:
                break;
            case R.id.btn_cancel_order:
//                ToastUtil.showShort("取消订单");
                updateOrder(2);
                break;
            case R.id.btn_to_pay://买家
                if(mOrder.getState() == 1){//未付款  跳转到付款页面

                }
                break;
            case R.id.btn_confirm_pay://卖家
                if(mOrder.getState() == 3){//已付款 确认收款
                    updateOrder(4);
                }
                break;
        }
    }

    private void updateOrder(int i) {
        //提交
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", mToken);
        map.put("role_type",mType+1);  //0买 1卖   接口中1是买  2是卖 所以 加1
        map.put("order_no",mOrder.getOrder_no());
        map.put("state", i);
        //加密
        String json = SystemUtils.getJson(map);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);

        HttpManager.getInstance().getApiServer().updatePaytypeOfOrder(body)
                .compose(RxUtils.<UpdateOrderRsBean>changeScheduler())
                .subscribe(new Observer<UpdateOrderRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpdateOrderRsBean updateOrderRsBean) {
                        if(updateOrderRsBean.getError() == 0){
                            setResult(100);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String m = "";
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getOrdersReturn(OrderRsBean orderRsBean) {
        String s = "";
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null)
            timer.cancel();//停止，关闭倒计时
    }
}
