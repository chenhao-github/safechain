package com.code.safechain.ui.transaction;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.TransactionBuyConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.presenter.TransactionBuyPresenter;
import com.code.safechain.ui.transaction.adapter.PayTypeAdapter;
import com.code.safechain.ui.transaction.adapter.PayTypeForOrderAdapter;
import com.code.safechain.ui.transaction.bean.OrderDetailRsBean;
import com.code.safechain.ui.transaction.bean.OthersSaleOrderRsBean;
import com.code.safechain.ui.transaction.bean.TransactionBuyRsBean;
import com.code.safechain.ui.transaction.bean.UpdateOrderRsBean;
import com.code.safechain.utils.RxUtils;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PaymentActivityForOrder extends BaseActivity<TransactionBuyConstract.Presenter> implements TransactionBuyConstract.View {
    @BindView(R.id.img_back)
    ImageView mImgPayment;
    @BindView(R.id.txt_money)//实付款
            TextView mTxtMoney;
    @BindView(R.id.txt_payment_hint1)
    TextView mTxtHint1;
    @BindView(R.id.txt_count_down)//剩余时间 倒计时
            TextView mTxtCountDown;
    @BindView(R.id.txt_payee)//收款人
            TextView mTxtPayee;
    //    @BindView(R.id.img_payment2)
//    ImageView mImgPayment2;
    @BindView(R.id.sp_paytype)//支付方式 下拉选择
            Spinner mSpPaytype;
    //    @BindView(R.id.txt_wechat_title2)//微信号 支付宝号  显示
//    TextView mTxtWechatTitle;
    @BindView(R.id.img_qr_code)//微信，支付宝  二维码
            ImageView mImgQrCode;
    //银联区域
    @BindView(R.id.rl_unionpay)
    RelativeLayout mRlUnionpay;
    //    @BindView(R.id.txt_user_name)//银联户主名字
//    TextView mTxtUserName;
    @BindView(R.id.txt_bank_name)//银行名字
    TextView mTxtBankName;
    @BindView(R.id.txt_bank_no)//银联卡号
    TextView mTxtBankNo;
    @BindView(R.id.btn_successful_payment)
    Button mBtnSuccessfulPayment;
    private List<OrderDetailRsBean.ResultBean.PaysBean> mPays;
    private int mPayType;//支付方式

    //12分钟换算成毫秒
//    private int timeStemp = 720000;
    private long timeDown;
    private CountDownTimer timer;
    private String mToken;
    private OrderDetailRsBean.ResultBean orderDetailRs;
    private String mOrderNo;

    @Override
    protected int getLayout() {
        return R.layout.activity_payment;
    }

    @Override
    protected TransactionBuyConstract.Presenter createPresenter() {
        return new TransactionBuyPresenter();
    }

    @Override
    protected void initView() {
        mToken = SpUtils.getInstance(this).getString(Constants.TOKEN);
        //得到订单号
        mOrderNo = getIntent().getStringExtra("orderNo");
        //请求订单详情
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        map.put("role_type",1);//1买家  2卖家
        map.put("order_no", mOrderNo);//订单号
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
                            //设置订单信息
                            setOrderDetail();
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
    //设置 订单信息
    private void setOrderDetail() {
        //买的卖单信息
        getCountDownTime();//开始倒计时
        //赋值
        mTxtMoney.setText("￥ "+String.format("%.6f", Double.parseDouble(orderDetailRs.getTotal())));//设置应付款
//        mTxtPayee.setText(mSaleOrder.getUser_name());//设置收款人
        //获得所有支付方式
        mPays = orderDetailRs.getPays();
        //获取选择的支付方式
//        getChoosePayType();
        //默认显示第一个支付方式的支付图片或银联信息
        if(mPays != null && mPays.size()>0)
            switchPaytype(mPays.get(0));

        //给支付方式 spanner适配支付方式
        PayTypeForOrderAdapter adapter = new PayTypeForOrderAdapter(this, mPays);
        mSpPaytype.setAdapter(adapter);

        mSpPaytype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                OrderDetailRsBean.ResultBean.PaysBean paysBean = mPays.get(position);
                switchPaytype(paysBean);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /*private void getChoosePayType() {
        int payType = mSaleOrder.getPay_type();//获得卖订单中设置的支付方式的组合
        ArrayList<OthersSaleOrderRsBean.ResultBean.PaysBean> paysBeans = new ArrayList<>();
        int weixinIndex = 0;
        int aliIndex = 0;
        int unionIndex = 0;
        //找到每一个支付方式的索引
        for (int i = 0; i < mPays.size(); i++) {
            if(mPays.get(i).getPay_type()==1)
                weixinIndex = i;
            else if (mPays.get(i).getPay_type()==2)
                aliIndex = i;
            else if (mPays.get(i).getPay_type()==4)
                unionIndex = i;
        }
        // 服务器给了所有的支付方式，但是只使用部分支付方式，通过payType得知
        //通过总的支付方式选取 支付方式集合，，
        if(payType == 1){//只显示微信
            paysBeans.add(mPays.get(weixinIndex));
        }else if (payType == 2){//只显示 支付宝
            paysBeans.add(mPays.get(aliIndex));
        }else if(payType == 4){//只显示 银联
            paysBeans.add(mPays.get(unionIndex));
        }else if(payType == 3){//显示 微信和支付宝
            paysBeans.add(mPays.get(weixinIndex));
            paysBeans.add(mPays.get(aliIndex));
        }else if(payType == 5){//显示 微信和银联
            paysBeans.add(mPays.get(weixinIndex));
            paysBeans.add(mPays.get(unionIndex));
        }else if(payType == 6){//显示 支付宝和银联
            paysBeans.add(mPays.get(aliIndex));
            paysBeans.add(mPays.get(unionIndex));
        }else if(payType == 7){//显示 微信 支付宝和银联
            paysBeans.add(mPays.get(weixinIndex));
            paysBeans.add(mPays.get(aliIndex));
            paysBeans.add(mPays.get(unionIndex));
        }
        mPays.clear();//清空完整的支付方式
        mPays.addAll(paysBeans);//添加选择的支付方式

    }*/

    private void getCountDownTime() {
        timeDown = orderDetailRs.getTimedown()*1000;
        timer = new CountDownTimer(timeDown, 1000) {
            @Override
            public void onTick(long l) {

                long day = l / (1000 * 24 * 60 * 60); //单位天
                long hour = (l - day * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60); //单位时
                long minute = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60)) / (1000 * 60); //单位分
                long second = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;//单位秒

                mTxtCountDown.setText(minute + ":" +second );
//                mTxtCountDown.setText(hour + ":" + minute + ":" + second );
                Log.i("111", "onTick: "+minute + ":" + second);
            }

            @Override
            public void onFinish() {
                //倒计时为0时执行此方法
                //倒计时为0时执行此方法
                updateOrder(2);//取消订单
                finish();//结束页面，取消订单
            }
        }.start();
    }

    //取消订单 或 已付款订单
    private void updateOrder(int i) {
        //提交
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", mToken);
        map.put("role_type",1);  //0买 1卖   接口中1是买  2是卖 所以 加1
        map.put("order_no",mOrderNo);
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
    protected void onStop() {
        super.onStop();
        timer.cancel();//停止，关闭倒计时
    }

    private void switchPaytype(OrderDetailRsBean.ResultBean.PaysBean paytype) {
        mPayType = paytype.getPay_type();
        if(mPayType == 1 || mPayType == 2){//微信，支付宝
            if(mPayType == 1){
                mTxtHint1.setText(getResources().getString(R.string.transaction_payment_hint1)+
                        getResources().getString(R.string.transaction_paytype_wechat));
            }else if (mPayType == 2){
                mTxtHint1.setText(getResources().getString(R.string.transaction_payment_hint1)+
                        getResources().getString(R.string.transaction_paytype_alipay));
            }
            //显示微信，支付宝 支付信息
//            mTxtWechatTitle.setVisibility(View.VISIBLE);
            mImgQrCode.setVisibility(View.VISIBLE);
            //隐藏 银联
            mRlUnionpay.setVisibility(View.INVISIBLE);
            //显示 微信，支付宝  账号和二维码
            mTxtPayee.setText(paytype.getUser_name());
            Glide.with(this).load(paytype.getImg_url()).into(mImgQrCode);
        }else {//银联
            mTxtHint1.setText(getResources().getString(R.string.transaction_payment_hint1)+
                    getResources().getString(R.string.transaction_paytype_unionpay));
            //隐藏微信，支付宝 支付信息
//            mTxtWechatTitle.setVisibility(View.INVISIBLE);
            mImgQrCode.setVisibility(View.INVISIBLE);
            //显示银联区域
            mRlUnionpay.setVisibility(View.VISIBLE);
            //显示银联账号信息
            mTxtPayee.setText(paytype.getUser_name());
            mTxtBankName.setText(paytype.getBank_name()+","+paytype.getPay_addr());
            mTxtBankNo.setText(paytype.getBank_no());
        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_back, R.id.btn_successful_payment})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_successful_payment:
                //更新订单信息 为已付款
                updatePaytype();
                break;
        }
    }

    private void updatePaytype() {
        //提交
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", mToken);
        map.put("role_type",1);
//        map.put("order_no",Long.parseLong(mOrder_no));
        map.put("order_no",mOrderNo);
        map.put("state", 3);//3 已付款
//        map.put("type", 1);//1 更改支付方式
        if(mPayType == 0){
            ToastUtil.showShort("没有支付方式，无法提交!");
            return;
        }
        map.put("pay_type",mPayType);
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
    public void buyChainReturn(TransactionBuyRsBean transactionBuyRsBean) {
        if(transactionBuyRsBean.getError() == 0){
            ToastUtil.showShort("购买成功");
        }else {
            ToastUtil.showShort("购买失败");
        }
        finish();
    }
}
