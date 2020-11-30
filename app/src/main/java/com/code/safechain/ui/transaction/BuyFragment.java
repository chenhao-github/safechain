package com.code.safechain.ui.transaction;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.app.BaseApp;
import com.code.safechain.base.BaseFragment;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.TranSaleOrderConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.presenter.TranSaleOrderPresenter;
import com.code.safechain.ui.main.bean.UserBean;
import com.code.safechain.ui.transaction.adapter.BuyChainSaleAdapter;
import com.code.safechain.ui.transaction.bean.OthersSaleOrderRsBean;
import com.code.safechain.ui.transaction.bean.TransactionBuyRsBean;
import com.code.safechain.utils.RxUtils;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @Auther: hchen
 * @Date: 2020/7/22 0022
 * @Description:
 */
public class BuyFragment extends BaseFragment<TranSaleOrderConstract.Presenter>
        implements TranSaleOrderConstract.View, View.OnClickListener {
    @BindView(R.id.rlv_buy_chain)
    RecyclerView mRlvBuyChain;
    private ArrayList<OthersSaleOrderRsBean.ResultBean> mChainSaleBeans;
    private BuyChainSaleAdapter mAdapter;
    public PopupWindow mPw;
    private TextView mBuyMoney;
    private TextView mBuyNumber;
    private TextView mPlaceOrder;
    private EditText mInput;
    //购买类型选择
    private static final int TYPE_MONEY = 0;
    private static final int TYPE_NUMBER = 1;
    //最终的购买类型
    private int type_buy = 0;//默认是按金额购买
    private OthersSaleOrderRsBean.ResultBean mSaleOrder;
    private TextView mNumber;
    private TextView mActualPayment;
//    private TextView mAutoCancel;
    private int n = 30;//倒计时
    private TextView mQuota;
    //    private Timer mTimer;

    @Override
    protected int getLayout() {
        return R.layout.fragment_transaction_buy;
    }

    @Override
    protected TranSaleOrderConstract.Presenter createPresenter() {
        return new TranSaleOrderPresenter();
    }

    @Override
    protected void initView() {
        mRlvBuyChain.setLayoutManager(new LinearLayoutManager(getActivity()));
        mChainSaleBeans = new ArrayList<>();
        mAdapter = new BuyChainSaleAdapter(getActivity(), mChainSaleBeans,"buy");//从卖点购买
        mRlvBuyChain.setAdapter(mAdapter);
        mAdapter.setOnClickListener(this);//添加 条目中的组件的点击监听
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){//显示的时候
            HashMap<String, Object> map = new HashMap<>();
            map.put("token", SpUtils.getInstance(getActivity()).getString(Constants.TOKEN));
            map.put("size",Constants.SIZE);
//        map.put("lastid",Constants.SIZE);//最后一条数据的 商品id（主键id）,从这里开始取 size条

            map = SystemUtils.getMap(map);
            presenter.getSaleOrder(map);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(getActivity()).getString(Constants.TOKEN));
        map.put("size",Constants.SIZE);//分页数量
//        map.put("lastid",Constants.SIZE);//最后一条数据的 商品id（主键id）,从这里开始取 size条

        map = SystemUtils.getMap(map);
        presenter.getSaleOrder(map);

        type_buy = TYPE_MONEY;//每次进入页面，默认都是 按金额购买
    }

    //获取卖单的返回
    @Override
    public void getSaleOrderReturn(OthersSaleOrderRsBean saleOrderBean) {
        mAdapter.updataListClearAddMore(saleOrderBean.getResult());
    }

    //条目中的 购买 按钮的点击回调
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id ){
            case R.id.btn_buy://点击的是 购买按钮
                showPwToBuy(v);//处理购买
                break;
            case R.id.txt_buy_money://按金额购买
                dealBuySaleAndFragment(TYPE_MONEY);
                type_buy = TYPE_MONEY;
                break;
            case R.id.txt_buy_number://按数量购买
                dealBuySaleAndFragment(TYPE_NUMBER);
                type_buy = TYPE_NUMBER;
                break;
            /*case R.id.btn_auto_cancel:
                mPw.dismiss();
                SystemUtils.setBackgroundAlpha(getActivity().getWindow(),Constants.NO_SHADOW);
//                mTimer.cancel();//关闭定时器
                break;*/
            case R.id.btn_place_order://下单
                createOrder();
                break;
        }

    }

    private void createOrder() {
        String s = mInput.getText().toString();
        if(TextUtils.isEmpty(s)){
            ToastUtil.showShort("输入不能为空!");
            return;
        }
        float totalPay = 0f;
        //得到购买数据
        if(type_buy == TYPE_MONEY){//如果按金额购买
            //计算总金额
            BigDecimal a1 = new BigDecimal(Double.toString(mSaleOrder.getPrice()));//单价
            BigDecimal b1 = new BigDecimal(mSaleOrder.getNum());//币的原本数量
            BigDecimal input = new BigDecimal(s.toString());//把输入的金额转为 BigDecimal
            BigDecimal total = a1.multiply(b1);// 相乘结果，得到币的总值
           /* if(input.compareTo(total) == 1){
                ToastUtil.showShort("金额超过币值!");
                return;
            }*/
            //得到最大最小金额
            BigDecimal min = new BigDecimal(Double.toString(mSaleOrder.getMin()));//最小金额
            BigDecimal max = new BigDecimal(Double.toString(mSaleOrder.getMax()));//最大金额
            if(input.compareTo(min) == -1 || input.compareTo(max) == 1){
                ToastUtil.showShort("金额超过限额!");
                return;
            }
            totalPay = Float.valueOf(input.stripTrailingZeros().toPlainString());//
            BigDecimal num = input.divide(a1,2, BigDecimal.ROUND_HALF_UP);//计算交易数量
            mNumber.setText(num.stripTrailingZeros().toPlainString());//设置数量 去除末尾的0
            mActualPayment.setText("￥"+s.toString());//设置实付款
        }else if(type_buy == TYPE_NUMBER) {//如果按数量购买
            float input = Float.parseFloat(s.toString());
            if(input<1 || input >Float.parseFloat(mSaleOrder.getNum())){
                ToastUtil.showShort("数量超过限额！");
                return;
            }
            mNumber.setText(s.toString());
            //计算实付款
            BigDecimal a1 = new BigDecimal(Double.toString(mSaleOrder.getPrice()));//单价
            BigDecimal b1 = new BigDecimal(s.toString());//数量
            BigDecimal payMoney = a1.multiply(b1);//应付金额
            totalPay = Float.valueOf(payMoney.stripTrailingZeros().toPlainString());
//            mActualPayment.setText(payMoney.stripTrailingZeros().toPlainString());
            mActualPayment.setText("￥"+String.format("%.6f", Double.parseDouble(payMoney.stripTrailingZeros().toPlainString())));
        }

        final HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(getActivity()).getString(Constants.TOKEN));
        map.put("store_id", mSaleOrder.getStore_id());
//        map.put("num", mSaleOrder.getNum());
        String _num = mNumber.getText().toString();//得到购买数量
//        if(_num.indexOf(".") != -1){
//            map.put("num", Integer.parseInt(_num.substring(0,_num.indexOf("."))));
//        }else {
//            map.put("num", Integer.parseInt(_num));
//        }
        map.put("num", Float.parseFloat(_num));//不去小数点后面的

//        map.put("price", mSaleOrder.getPrice());
        map.put("price", String.format("%.6f", mSaleOrder.getPrice()));
        map.put("total", String.format("%.6f", Double.parseDouble(totalPay+"")));
        //加密
        String json = SystemUtils.getJson(map);
        //下单
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        //
        HttpManager.getInstance().getApiServer().buyChain(body)
                .compose(RxUtils.<TransactionBuyRsBean>changeScheduler())
                .subscribe(new Observer<TransactionBuyRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TransactionBuyRsBean transactionBuyRsBean) {
                       if(transactionBuyRsBean.getError() == 0){
                            ToastUtil.showShort("下单成功");
                            //下单成功后，跳转到付款页面
                            //订单号
                            String order_no = transactionBuyRsBean.getResult().getOrder_no();
                            toPaymentActivity(map,order_no);
                        }else {
                            ToastUtil.showShort(transactionBuyRsBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        String str = e.getMessage();
//                        String s = "";
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    //跳转到下单后的付款页面
    private void toPaymentActivity(HashMap<String, Object> map, String order_no) {
        //得到点击的数据，和 付款金额 传递到下单页面
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(Constants.DATA, mSaleOrder);//把点击的数据传递到 下单页面
        intent.putExtra("map",map);//把封装的数据传入到下一个页面
        intent.putExtra("order_no",order_no);//把订单号传入到下一个页面

        mPw.dismiss();//关闭pw
        SystemUtils.setBackgroundAlpha(getActivity().getWindow(),Constants.NO_SHADOW);//去除阴影
        //跳转到付款页面，买后，数量减少，回到本页面中重新请求新数据
        startActivityForResult(intent,100);
    }

    //点击 购买按钮的回调,显示购买popupwindow
    private void showPwToBuy(View v) {
        //得到点击的卖单数据
        mSaleOrder = (OthersSaleOrderRsBean.ResultBean) v.getTag();
        //弹出我要买(下单)pw
        View pwLayout = View.inflate(getActivity(), R.layout.popup_trans_buy_pay_preview, null);
        mPw = new PopupWindow(pwLayout, ViewGroup.LayoutParams.MATCH_PARENT, 1130);
        mPw.setFocusable(true);
        mPw.showAtLocation(mRlvBuyChain, Gravity.BOTTOM,0,0);
        SystemUtils.setBackgroundAlpha(getActivity().getWindow(), Constants.SHADOW);//显示阴影
        //处理组件
        initViewOfPw(pwLayout);
        //关闭pw，设置没有阴影
        mPw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //设置背景不透明
                SystemUtils.setBackgroundAlpha(getActivity().getWindow(), Constants.NO_SHADOW);
                type_buy = TYPE_MONEY;//关闭pw，恢复默认 按金额购买，下次进入 默认按金额购买
//                mTimer.cancel();//关闭倒计时
//                n = 30;//把倒计时重置到30秒
            }
        });
    }

    private void initViewOfPw(View pwLayout) {
        TextView chainName = pwLayout.findViewById(R.id.txt_chain_name);//币的名字
        TextView unitPrice = pwLayout.findViewById(R.id.txt_unit_price);//单价
        ImageView chainIcon = pwLayout.findViewById(R.id.img_chain_icon);//币的图标
        TextView buyAll = pwLayout.findViewById(R.id.txt_input_end);//全部买入
        //赋值
        chainName.setText(mSaleOrder.getToken_name());
        unitPrice.setText(String.format("%.6f",mSaleOrder.getPrice()));
//        Glide.with(getActivity()).load("").into(chainIcon);//图标没有
        //按金额购买
        mBuyMoney = pwLayout.findViewById(R.id.txt_buy_money);
        //按数量购买
        mBuyNumber = pwLayout.findViewById(R.id.txt_buy_number);
        //输入框
        mInput = pwLayout.findViewById(R.id.et_input);
        //限额
        mQuota = pwLayout.findViewById(R.id.txt_quota);
        //交易数量
        mNumber = pwLayout.findViewById(R.id.txt_trans_number);
        //实付款
        mActualPayment = pwLayout.findViewById(R.id.txt_trans_actual_payment);
        //自动取消
//        mAutoCancel = pwLayout.findViewById(R.id.btn_auto_cancel);
        //赋值
        mQuota.setText("￥"+mSaleOrder.getMin() + " - " + "￥"+ mSaleOrder.getMax());//限额赋值
        //下单
        mPlaceOrder = pwLayout.findViewById(R.id.btn_place_order);
        //添加监听
        mBuyMoney.setOnClickListener(this);//按金额购买
        mBuyNumber.setOnClickListener(this);//按数量购买
//        mAutoCancel.setOnClickListener(this);//取消按钮
        mPlaceOrder.setOnClickListener(this);//下单
        //输入框添加值改变事件
        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s.toString())){
                    mNumber.setText("");//清除 数量
                    mActualPayment.setText("");//清除 实付款
                    return;
                }
                if(type_buy == TYPE_MONEY){//如果按金额购买
                    //计算总金额
                    BigDecimal a1 = new BigDecimal(Double.toString(mSaleOrder.getPrice()));//单价
                    BigDecimal b1 = new BigDecimal(mSaleOrder.getNum());//币的数量
                    BigDecimal input = new BigDecimal(s.toString());//把输入的数据转为 BigDecimal
                    BigDecimal total = a1.multiply(b1);// 单价*数量 得到总价钱
//                    if(input.compareTo(total) == 1){
//                        ToastUtil.showShort("金额超过币值!");
////                        return;
//                    }
                    //得到最大最小金额
                    BigDecimal min = new BigDecimal(Double.toString(mSaleOrder.getMin()));//最小金额
                    BigDecimal max = new BigDecimal(Double.toString(mSaleOrder.getMax()));//最大金额
                    /*if(input.compareTo(min) == -1 || input.compareTo(max) == 1){
                        ToastUtil.showShort("金额超过限额!");
                    }*/

                    //在divide方法中传递第二个参数，定义精确到小数点后几位，否则在不整除的情况下，结果是无限循环小数时，就会抛出以上异常。
                    BigDecimal num = input.divide(a1,2, BigDecimal.ROUND_HALF_UP);//计算交易数量
                    mNumber.setText(num.stripTrailingZeros().toPlainString());//设置数量 去除末尾的0
                    mActualPayment.setText("￥"+s.toString());//设置实付款
                }else if(type_buy == TYPE_NUMBER) {//如果按数量购买
                    float input = Float.parseFloat(s.toString());
                    if(input<1 || input >Float.parseFloat(mSaleOrder.getNum())){
                        ToastUtil.showShort("数量超过限额！");
//                        return;
                    }
                    mNumber.setText(s.toString());
                    //计算实付款
                    BigDecimal a1 = new BigDecimal(Double.toString(mSaleOrder.getPrice()));//单价
                    BigDecimal b1 = new BigDecimal(s.toString());//输入的币数量
                    BigDecimal payMoney = a1.multiply(b1);//应付金额
//                    mActualPayment.setText(payMoney.stripTrailingZeros().toPlainString());
                    mActualPayment.setText("￥"+String.format("%.6f", Double.parseDouble(payMoney.stripTrailingZeros().toPlainString())));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        buyAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type_buy == TYPE_MONEY) {//如果按金额购买
                    mInput.setText(mSaleOrder.getMax()+"");
                }else if(type_buy == TYPE_NUMBER) {//如果按数量购买
                    mInput.setText(String.format("%.2f",Double.parseDouble(mSaleOrder.getNum())));
                }
            }
        });
    }

    /**
     * 处理 我要买和我要卖 的字体颜色和限额的值
     * @param type  0 按金额  1 按数量
     */
    private void dealBuySaleAndFragment(int type) {
        if(type == TYPE_MONEY){//安金额购买
            //切换 我要买  我要卖 的字体大小和颜色
            mBuyMoney.setTextColor(getResources().getColor(R.color.colorTransfer));
            mBuyNumber.setTextColor(getResources().getColor(R.color.colorTitle));
            //修改限额的值 为min--max
            mQuota.setText("￥"+mSaleOrder.getMin() + " - " + "￥" + mSaleOrder.getMax());//限额赋值
        }else {
            mBuyMoney.setTextColor(getResources().getColor(R.color.colorTitle));
            mBuyNumber.setTextColor(getResources().getColor(R.color.colorTransfer));
            //修改限额的值为 1-数量
            double num = Double.parseDouble(mSaleOrder.getNum());
            if(num>1)
                mQuota.setText("1 - " + String.format("%.2f",num));//限额赋值
            else
                mQuota.setText(String.format("%.2f",num));//限额赋值
        }
        setInputHint(type);//修改购买弹出框中的提示信息
    }

    /**
     * 修改购买弹出框中的提示信息
     * @param type
     */
    public void setInputHint(int type){
        String str  = "";
        //得到对应的提示信息
        if(type == TYPE_MONEY){
            str = getActivity().getResources().getString(R.string.transaction_buy_input_hint1);
        }else {
            str = getActivity().getResources().getString(R.string.transaction_buy_input_hint2);
        }
        //设置提示信息属性
        SpannableString ss = new SpannableString(str);
//        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15,true);
        // 附加属性到文本
        ss.setSpan(null, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        mInput.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }


}
