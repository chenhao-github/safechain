package com.code.safechain.ui.transaction;

import android.content.Intent;
import android.graphics.BitmapFactory;
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
import com.code.safechain.ui.transaction.bean.GetPayTypeRsBean;
import com.code.safechain.ui.transaction.bean.OrderDetailRsBean;
import com.code.safechain.ui.transaction.bean.OrderRsBean;
import com.code.safechain.ui.transaction.bean.UpdateOrderRsBean;
import com.code.safechain.utils.RxUtils;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class OrderDetailActivity extends BaseActivity<TransactionOrdersConstract.Presenter>
        implements TransactionOrdersConstract.View {
    @BindView(R.id.txt_please_pay) //请付款
    TextView mTxtPleasePay;
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
    @BindView(R.id.txt_seller_nickname_title)//卖家昵称 标题
    TextView mTxtSellerNickNameTitle;
    @BindView(R.id.txt_seller_nickname)//卖家昵称
    TextView mTxtSellerNickName;
    @BindView(R.id.img_weixin)//支付图标--微信
    ImageView mImgWeixin;
    @BindView(R.id.img_alipay)//支付图标--支付宝
    ImageView mImgAlipay;
    @BindView(R.id.img_unionpay)//支付图标--银联
    ImageView mImgUnionpay;

    @BindView(R.id.btn_cancel_order)//取消订单
    Button btnCancelOrder;
    @BindView(R.id.btn_to_pay)//去付款  等待买家确认收款  取消的订单
    Button btnToPay;
    @BindView(R.id.btn_confirm_pay)//确认付款  等待买家付款
    Button btnConfirmPay;
    @BindView(R.id.txt_surepay_hint)
    TextView mSurepayHint;


    private OrderRsBean.ResultBean mOrder;//列表传过来的
    OrderDetailRsBean.ResultBean orderDetailRs;//新获取的

    //12分钟换算成毫秒
//    private long allTime = 720000;
    private long timeDown;
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
        mTxtTotalPrice.setText("￥"+mOrder.getTotal());
        mTxtUnitPrice.setText("￥"+mOrder.getPrice());
        mTxtNumber.setText(String.format("%.6f", Double.parseDouble(mOrder.getNum())));
        mTxtCtime.setText(mOrder.getCtime());
        mTxtOrderNumber.setText(mOrder.getOrder_no());
        //获得订单详情
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        map.put("role_type",mType+1);//1买家  2卖家
        map.put("order_no",mOrder.getOrder_no());//订单号
        map = SystemUtils.getMap(map);
        HttpManager.getInstance().getApiServer().getOrderDetail(map)
                .compose(RxUtils.<OrderDetailRsBean>changeScheduler())
                .subscribe(new Observer<OrderDetailRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OrderDetailRsBean orderDetailRsBean) {
                        //获得订单详情 ，包括 支付方式，用于 去支付
                        if(orderDetailRsBean != null && orderDetailRsBean.getResult() != null){
                            orderDetailRs = orderDetailRsBean.getResult();
                            setBottomInfo();//设置下方显示区域

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShort("获取订单详情失败，请重试！");
                        finish();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /*public static Long getMilliSecond(String date) {
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
    }*/

    private void setBottomInfo() {
        //设置卖家昵称，订单列表没有提供，订单详情中提供
        try {
            mTxtSellerNickName.setText(orderDetailRs.getNickname());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置收款方式  已付款3，已完成4 显示买家的付款方式 1种  其他状态，显示卖家设定的支付方式
        if(orderDetailRs.getState() == 3 || orderDetailRs.getState() == 4){
            int pay_type = orderDetailRs.getPay_type();
            if(pay_type== 1){
                mImgWeixin.setVisibility(View.VISIBLE);
            }else if(pay_type == 2){
                mImgAlipay.setVisibility(View.VISIBLE);
            }else {
                mImgUnionpay.setVisibility(View.VISIBLE);
            }
        }else {
            List<OrderDetailRsBean.ResultBean.PaysBean> pays = orderDetailRs.getPays();
            if(pays != null && pays.size()>0){
                for (int i = 0; i < pays.size(); i++) {
                    OrderDetailRsBean.ResultBean.PaysBean paysBean = pays.get(i);
                    if(paysBean.getPay_type() == 1){
                        mImgWeixin.setVisibility(View.VISIBLE);
                    }else if(paysBean.getPay_type() == 2){
                        mImgAlipay.setVisibility(View.VISIBLE);
                    }else {
                        mImgUnionpay.setVisibility(View.VISIBLE);
                    }
                }
            }

        }
        //买家或者卖家 进入详情 判断新倒计时是否已超时，如果超时这显示 已取消，不显示倒计时，不关闭本页面
        timeDown = orderDetailRs.getTimedown()*1000;
        if(orderDetailRs.getState()==1 && timeDown<=0){//未付款，倒计时结束（在列表等待导致）
            orderDetailRs.setState(2);//设置已取消
        }
        mTxtPleasePay.setVisibility(View.VISIBLE);//显示 请付款  头部的信息
        if(mType == 0){//我要买 买家进来
            //设置昵称提示信息  买家看到的是卖家昵称
            mTxtSellerNickNameTitle.setText(getResources().getString(R.string.transaction_seller_nickname1));

            btnToPay.setVisibility(View.VISIBLE);  //显示 取消订单后的按钮--多个用处，修改值
            if(orderDetailRs.getState() == 1){//未付款，显示  取消订单和去付款
                //显示倒计时
                mTxtTimeTitle.setVisibility(View.VISIBLE);
                mTxtTimeRemaining.setVisibility(View.VISIBLE);
                getCountDownTime();//调用倒计时

                btnToPay.setText(getResources().getString(R.string.transaction_to_pay));
                mTxtPleasePay.setText(getResources().getString(R.string.transaction_please_pay));//头部的信息//显示 请付款  头部的信息
                btnCancelOrder.setVisibility(View.VISIBLE);//显示 取消订单
            }else if(orderDetailRs.getState() == 2){//取消的订单，显示已取消
                btnToPay.setText(getResources().getString(R.string.transaction_order_failed));
                mTxtPleasePay.setText(getResources().getString(R.string.transaction_order_failed));//头部的信息
            }else if(orderDetailRs.getState() == 3){//已付款 显示等待卖家确认
                mTxtPleasePay.setText(getResources().getString(R.string.transaction_wait_confirm));//头部的信息
                btnToPay.setText(getResources().getString(R.string.transaction_wait_confirm));
            }else if(orderDetailRs.getState() == 4){//已完成 显示已完成
                mTxtPleasePay.setText(getResources().getString(R.string.transaction_order_completed));//头部的信息
                btnToPay.setText(getResources().getString(R.string.transaction_order_completed));
            }
        }else {//我要卖  卖家进来
            //设置昵称提示信息  卖家看到的是买家昵称
            mTxtSellerNickNameTitle.setText(getResources().getString(R.string.transaction_seller_nickname2));
            //显示确认收款
            btnConfirmPay.setVisibility(View.VISIBLE);//显示卖家的按钮
            if(orderDetailRs.getState() == 1){//未付款，显示  等待买家付款
                mTxtPleasePay.setText(getResources().getString(R.string.transaction_wait_pay));//头部的信息
                btnConfirmPay.setText(getResources().getString(R.string.transaction_wait_pay));
            }else if(orderDetailRs.getState() == 2){//取消的订单，显示已取消
                mTxtPleasePay.setText(getResources().getString(R.string.transaction_order_failed));//头部的信息
                btnConfirmPay.setText(getResources().getString(R.string.transaction_order_failed));
            }else if(orderDetailRs.getState() == 3){//已付款 显示确认收款
                mTxtPleasePay.setText(getResources().getString(R.string.transaction_pay_confirm));//头部的信息
                btnConfirmPay.setText(getResources().getString(R.string.transaction_pay_confirm));//确认收款 提交按钮
                mSurepayHint.setVisibility(View.VISIBLE);
            }else if(orderDetailRs.getState() == 4){//已完成 显示已完成
                mTxtPleasePay.setText(getResources().getString(R.string.transaction_order_completed));//头部的信息
                btnConfirmPay.setText(getResources().getString(R.string.transaction_order_completed));
            }
        }
    }

    private void getCountDownTime() {
        timeDown = orderDetailRs.getTimedown()*1000;
        timer = new CountDownTimer(timeDown, 1000) {
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

    @OnClick({R.id.img_back, R.id.btn_cancel_order, R.id.btn_to_pay, R.id.btn_confirm_pay})
    public void onViewClicked(View view) {
        String s = "";
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_cancel_order:
//                ToastUtil.showShort("取消订单");
                updateOrder(2);
                break;
            case R.id.btn_to_pay://买家
                String txt = btnToPay.getText().toString();//得到按钮的值
                //值是去付款  跳转到付款页面
                if(getResources().getString(R.string.transaction_to_pay).equals(txt)){
                    Intent it = new Intent(this, PaymentActivityForOrder.class);
                    it.putExtra("orderNo",orderDetailRs.getOrder_no());//传入订单号
                    startActivity(it);
                    finish();
                }
                break;
            case R.id.btn_confirm_pay://卖家
                if(mOrder.getState() == 3){//已付款 确认收款
                    updateOrder(4);
                }
                break;
        }
    }

    //取消订单
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
                        }else {
                            ToastUtil.showShort(updateOrderRsBean.getMessage());
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
