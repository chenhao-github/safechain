package com.code.safechain.ui.transaction;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.app.BaseApp;
import com.code.safechain.base.BaseFragment;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.MySaleChainConstract;
import com.code.safechain.interfaces.TransactionConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.presenter.MySaleChainPresenter;
import com.code.safechain.presenter.TransactionPresenter;
import com.code.safechain.ui.main.bean.UserBean;
import com.code.safechain.ui.transaction.adapter.MySaleChainAdapter;
import com.code.safechain.ui.transaction.adapter.PayTypeAdapter;
import com.code.safechain.ui.transaction.bean.GetPayTypeRsBean;
import com.code.safechain.ui.transaction.bean.MySaleOrderRsBean;
import com.code.safechain.ui.transaction.bean.OthersSaleOrderRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;
import com.code.safechain.utils.DeviceIdFactory;
import com.code.safechain.utils.RxUtils;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @Auther: hchen
 * @Date: 2020/7/22 0022
 * @Description:
 */
public class SaleFragment extends BaseFragment<MySaleChainConstract.Presenter>
        implements MySaleChainConstract.View {
//    @BindView(R.id.sp_chain_type)//币种选择
//    Spinner mSpChainType;
    @BindView(R.id.txt_chain_name_top)
    TextView mTxtChainNameTop;
    @BindView(R.id.txt_mini_trade)//最小交易额
    EditText mTxtMiniTrade;
    @BindView(R.id.txt_maxi_trade)//最大交易额
    EditText mTxtMaxiTrade;
    @BindView(R.id.txt_total_shipment)//出货总量-币的数量
    EditText mTxtTotalShipment;
    @BindView(R.id.txt_chain_number)//拥有的此币的数量
    TextView mTxtChainNumber;
//    浮动区域 开始
    @BindView(R.id.rl_setting_premium)
    RelativeLayout mRlSettingPremium;
    @BindView(R.id.rl_optional)
    RelativeLayout mRlOptional;
    @BindView(R.id.rl_floating_price_show)
    RelativeLayout mRlfloatingPriceShow;

    @BindView(R.id.et_price_float_percent)//设置溢价的浮动范围 -10到0
    EditText mEtPriceFloatPercent;
    @BindView(R.id.txt_floating_price)//浮动价
    TextView mTxtFloatingPrice;
    //    浮动区域 结束
    //一口价区域 开始
    @BindView(R.id.rl_fix_price)
    RelativeLayout mRlFixPrice;
    @BindView(R.id.rl_fix_price_hint)
    RelativeLayout mRlFixPriceHint;
    @BindView(R.id.rl_fix_price_end)
    RelativeLayout mRlFixPriceEnd;

    @BindView(R.id.et_set_fix_price)
    EditText mEtSetFixPrice;
    //一口价区域结束

    @BindView(R.id.et_comment)//备注
    EditText mEtComment;

    @BindView(R.id.txt_total_shipment_chain_name)
    TextView mTxtTotalShipmentChainName;
    @BindView(R.id.txt_chain_name)
    TextView mTxtChainName;

    //收款方式
    @BindView(R.id.ck_wechat)
    CheckBox mCkWechat;
    @BindView(R.id.ck_alipay)
    CheckBox mCkAlipay;
    @BindView(R.id.ck_unionpay)
    CheckBox mCkUnionpay;
    //浮动价  一口价
    @BindView(R.id.btn_floating_price)//浮动价
    Button mBtnFloatingPrice;
    @BindView(R.id.btn_fixed_price)//一口价
    Button mBtnFixedPrice;

    private int priceType = 2;//默认浮动价

    private ArrayList<WalletHomeRsBean.ResultBean.DataBean> mDataBeans;
    private MySaleChainAdapter mAdapter;
    private WalletHomeRsBean.ResultBean.DataBean mDataBean;
    private boolean mHavePaytype;//是否有支付方式

    //设置选中的币的信息
    private void setDataOfChain(WalletHomeRsBean.ResultBean.DataBean dataBean) {
        mTxtChainNameTop.setText(dataBean.getSymbol());
        mTxtTotalShipmentChainName.setText("（"+dataBean.getSymbol()+"）");
        mTxtTotalShipment.setText(String.format("%.2f",Double.parseDouble(dataBean.getNum())));
        mTxtChainNumber.setText(String.format("%.2f",Double.parseDouble(dataBean.getNum())));
        mTxtChainName.setText(dataBean.getSymbol());
        //设置溢价 的提示
        mEtPriceFloatPercent.setHint(getResources().getString(R.string.transaction_market_price)+String.format("%.6f",Double.parseDouble(dataBean.getPrice_cny())));
        //设置初始浮动价
        mTxtFloatingPrice.setText(String.format("%.6f",Double.parseDouble(dataBean.getPrice_cny())));
        //设置固定价格的提示信息
        mEtSetFixPrice.setHint(getResources().getString(R.string.transaction_market_price)+String.format("%.6f",Double.parseDouble(dataBean.getPrice_cny())));
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_transaction_sale;
    }

    @Override
    protected MySaleChainConstract.Presenter createPresenter() {
        return new MySaleChainPresenter();
    }

    @Override
    protected void initView() {
        mEtPriceFloatPercent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {//失去焦点是计算浮动价
                if(!hasFocus){
                    String lastPrice = countFloatPrice();//得到浮动价
                    mTxtFloatingPrice.setText(lastPrice);//设置到浮动价view上
                }
            }
        });

        //加载所有的币，通过下拉显示
        /*mDataBeans = new ArrayList<>();
        mAdapter = new MySaleChainAdapter(getActivity(), mDataBeans);
        mSpChainType.setAdapter(mAdapter);
        mSpChainType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDataBean = mDataBeans.get(position);//选中的币
                setDataOfChain(mDataBean);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        //
    }

    private String countFloatPrice() {
        String floatPercent = mEtPriceFloatPercent.getText().toString();
        double precentNum = (double)Double.parseDouble(floatPercent);
        double lastPrice = (100+precentNum)/100*Double.parseDouble(mDataBean.getPrice_cny());
        return String.format("%.6f",lastPrice);
    }

    @Override
    protected void initData() {
        //封装数据到Map
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(getActivity()).getString(Constants.TOKEN));
        map.put("type",1);
        map = SystemUtils.getMap(map);

        presenter.getWalletHome(map);//获取我的币资产


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            //获取支付方式
            getPaytype();
        }
    }

    //获取支付方式
    private void getPaytype() {
        //封装数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(getActivity()).getString(Constants.TOKEN));
        map.put("type", 3);
        map = SystemUtils.getMap(map);
        HttpManager.getInstance().getApiServer().getPaytype(map)
                .compose(RxUtils.<GetPayTypeRsBean>changeScheduler())
                .subscribe(new Observer<GetPayTypeRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetPayTypeRsBean getPayTypeRsBean) {
                        //设置支付方式

                        setPaytype(getPayTypeRsBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        String s = e.getMessage();
                        String a = "";
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //设置支付方式
    private void setPaytype(GetPayTypeRsBean getPayTypeRsBean) {
//        mHavePaytype = false;
        List<GetPayTypeRsBean.ResultBean.PaysBean> pays = getPayTypeRsBean.getResult().getPays();
        if(pays == null || pays.size() == 0){
            ToastUtil.showShort("没有添加支付方式，请添加！");
        }else {
            for (int i = 0; i < pays.size(); i++) {
                GetPayTypeRsBean.ResultBean.PaysBean paysBean = pays.get(i);
                if(paysBean.getType() == 1){//微信支付
                    if(!TextUtils.isEmpty(paysBean.getImg_url())){
                        mHavePaytype = true;
                        mCkWechat.setVisibility(View.VISIBLE);
                    }
                }else if(paysBean.getType() == 2){//支付宝支付
                    if(!TextUtils.isEmpty(paysBean.getImg_url())){
                        mHavePaytype = true;
                        mCkAlipay.setVisibility(View.VISIBLE);
                    }
                }else if(paysBean.getType() == 4){//银联支付
                    if(!TextUtils.isEmpty(paysBean.getBank_no())){
                        mHavePaytype = true;
                        mCkUnionpay.setVisibility(View.VISIBLE);
                    }
                }
            }
            if(!mHavePaytype){
                ToastUtil.showShort("请完善支付方式！");
            }
        }
    }

    @OnClick({R.id.btn_submit, R.id.btn_floating_price, R.id.btn_fixed_price, R.id.txt_total, R.id.rl_mini_trade
        , R.id.rl_maxi_trade})
    public void onViewClicked(View v) {
        if(v.getId() == R.id.btn_submit){
            submit();//提交
//            submit2();//提交

        }else if (v.getId() == R.id.btn_floating_price){//浮动价
            priceType = 2;
            mBtnFloatingPrice.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_button_yellow));
            mBtnFloatingPrice.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));

            mBtnFixedPrice.setBackground(null);
            mBtnFixedPrice.setTextColor(getActivity().getResources().getColor(R.color.colorMyRight));

            //显示浮动区域，隐藏 一口价区域
            mRlSettingPremium.setVisibility(View.VISIBLE);
            mRlOptional.setVisibility(View.VISIBLE);
            mRlfloatingPriceShow.setVisibility(View.VISIBLE);

            mRlFixPrice.setVisibility(View.INVISIBLE);
            mRlFixPriceHint.setVisibility(View.INVISIBLE);
            mRlFixPriceEnd.setVisibility(View.INVISIBLE);

        }else if (v.getId() == R.id.btn_fixed_price){//一口价
            priceType = 1;
            mBtnFloatingPrice.setBackground(null);
            mBtnFloatingPrice.setTextColor(getActivity().getResources().getColor(R.color.colorMyRight));

            mBtnFixedPrice.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_button_yellow));
            mBtnFixedPrice.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));

            //显示一口价区域，隐藏 浮动区域
            mRlSettingPremium.setVisibility(View.INVISIBLE);
            mRlOptional.setVisibility(View.INVISIBLE);
            mRlfloatingPriceShow.setVisibility(View.INVISIBLE);

            mRlFixPrice.setVisibility(View.VISIBLE);
            mRlFixPriceHint.setVisibility(View.VISIBLE);
            mRlFixPriceEnd.setVisibility(View.VISIBLE);

        }else if(v.getId() == R.id.txt_total){
            mTxtTotalShipment.setText(String.format("%.2f",Double.parseDouble(mDataBean.getNum())));
        }else if(v.getId() == R.id.rl_mini_trade){
            //最小值获得焦点
            mTxtMiniTrade.requestFocus();
            //弹出软键盘
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mTxtMiniTrade, 0);
        }else if(v.getId() == R.id.rl_maxi_trade){
            //最大值获得焦点
            mTxtMaxiTrade.requestFocus();
            //弹出软键盘
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mTxtMaxiTrade, 0);
        }
    }

    private void submit() {
        if(!mHavePaytype){
            ToastUtil.showShort("请完善支付方式！");
            return;
        }
//        DecimalFormat df = new DecimalFormat("######0.00");
        //封装数据到Map
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(getActivity()).getString(Constants.TOKEN));
        map.put("token_id",mDataBean.getToken_id());//选中的币的id
        map.put("type",priceType);//一口价 浮动价

        //最小量 不能为空
        String min = mTxtMiniTrade.getText().toString();
        if(TextUtils.isEmpty(min)){
            ToastUtil.showShort("最小交易额不能为空");
            return;
        }
        map.put("min",String.format("%.2f",Double.parseDouble(min)));
        //最大量 不能为空
        String maxi = mTxtMaxiTrade.getText().toString();
        if(TextUtils.isEmpty(maxi)){
            ToastUtil.showShort("最大交易额不能为空");
            return;
        }
        map.put("max",String.format("%.2f",Double.parseDouble(maxi)));

        //总出货量
        String vTotalShipment = mTxtTotalShipment.getText().toString();
        if(TextUtils.isEmpty(vTotalShipment) ){
            ToastUtil.showShort("出货总量不能为空");
            return;
        }
        double vNum = (double)Double.parseDouble(vTotalShipment);//出货总量，转换为数字
        double chainNum = (double)Double.parseDouble(mDataBean.getNum());

        if(vNum > chainNum){//判断出货量  不能为空
            ToastUtil.showShort("出货总量不能大于币的总量！");
            return;
        }
        map.put("num",String.format("%.2f",Double.parseDouble(vTotalShipment)));

        //支付方式
        int payType = 0;
        if(mCkWechat.isChecked())
            payType += 1;
        if(mCkAlipay.isChecked())
            payType += 2;
        if(mCkUnionpay.isChecked())
            payType += 4;
        if(payType == 0){
            ToastUtil.showShort("支付方式不能为空");
            return;
        }
        map.put("pay_type",payType);//支付方式

        if(priceType == 2){//浮动价  需要判断值是否在 -10 和 0之间
            //溢价百分比  不可以为空
            String floatPercent = mEtPriceFloatPercent.getText().toString();
            if(TextUtils.isEmpty(floatPercent)){
                ToastUtil.showShort("溢价范围不能为空！");
                return;
            }
            //判断必须在-10和0之间
            double precentNum = (double)Double.parseDouble(floatPercent);
            if(precentNum>0 || precentNum<-10){
                ToastUtil.showShort("溢价超出范围！");
                return;
            }
//            map.put("price_float",String.format("%.2f",Double.parseDouble(floatPercent)));
            map.put("price_float",String.format("%.2f",precentNum));

            //计算浮动价，并设置
            String lastPrice = countFloatPrice();
            map.put("price",lastPrice);
        }
       /* //浮动价格  可以为空
        String floatPrice = mTxtFloatingPrice.getText().toString();
        if(!TextUtils.isEmpty(floatPrice)){
            map.put("price",String.format("%.2f",Double.parseDouble(floatPrice)));
        }*/
        if(priceType == 1){//一口价，直接使用固定价格的值
            String fixPrice = mEtSetFixPrice.getText().toString();
            if(TextUtils.isEmpty(fixPrice)){//不可为空
                ToastUtil.showShort("固定价格不能为空！");
                return;
            }
            map.put("price",String.format("%.2f",Double.parseDouble(fixPrice)));
        }

        //备注
        String vComment = mEtComment.getText().toString();
        if(TextUtils.isEmpty(vComment)){
            ToastUtil.showShort("备注不能为空!");
            return;
        }
        map.put("remark",vComment);
        //加密
        String json= SystemUtils.getJson(map);
        presenter.saleChain(json);//卖币
    }

    //得到我的币资产的返回
    @Override
    public void getWalletHomeReturn(WalletHomeRsBean walletHomeRsBean) {
//        mDataBeans.clear();
//        mDataBeans.addAll(walletHomeRsBean.getResult().getData());
//        mAdapter.notifyDataSetChanged();
//        mDataBean = mDataBeans.get(0);//选中的要卖的币
        //进入页面获得资产数据，修改 界面中所有的币名,,进入默认是第一个币
        List<WalletHomeRsBean.ResultBean.DataBean> data = walletHomeRsBean.getResult().getData();
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).getSymbol().equals("SEC")){
                mDataBean = data.get(i);
            }
        }
        setDataOfChain(mDataBean);

    }


    //卖币的返回
    @Override
    public void saleChainReturn(MySaleOrderRsBean mySaleOrderRsBean) {
        if(mySaleOrderRsBean.getError() == 0){
            ToastUtil.showShort("卖币成功");
        }
    }

}
