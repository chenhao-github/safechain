package com.code.safechain.ui.transaction;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.code.safechain.R;
import com.code.safechain.base.BaseFragment;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.MySaleChainConstract;
import com.code.safechain.interfaces.TransactionConstract;
import com.code.safechain.presenter.MySaleChainPresenter;
import com.code.safechain.presenter.TransactionPresenter;
import com.code.safechain.ui.transaction.adapter.MySaleChainAdapter;
import com.code.safechain.ui.transaction.adapter.PayTypeAdapter;
import com.code.safechain.ui.transaction.bean.MySaleOrderRsBean;
import com.code.safechain.ui.transaction.bean.OthersSaleOrderRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;
import com.code.safechain.utils.DeviceIdFactory;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Auther: hchen
 * @Date: 2020/7/22 0022
 * @Description:
 */
public class SaleFragment extends BaseFragment<MySaleChainConstract.Presenter>
        implements MySaleChainConstract.View {
    @BindView(R.id.sp_chain_type)//币种选择
    Spinner mSpChainType;
    @BindView(R.id.txt_mini_trade)//最小量
    EditText mTxtMiniTrade;
    @BindView(R.id.txt_maxi_trade)//最大量
    EditText mTxtMaxiTrade;
    @BindView(R.id.txt_total_shipment)//出货总量
    EditText mTxtTotalShipment;
    @BindView(R.id.txt_chain_number)//拥有的此币的数量
    TextView mTxtChainNumber;
    @BindView(R.id.txt_set_price)//价格
    EditText mTxtSetPrice;
    @BindView(R.id.txt_chain_number1)//拥有的此币的数量1
    TextView mTxtChainNumber1;
    @BindView(R.id.et_enter_it)//浮动百分比
    EditText mEtEnterIt;
    @BindView(R.id.txt_floating_price)//浮动价
    TextView mTxtFloatingPrice;
    @BindView(R.id.et_comment)//备注
    EditText mEtComment;

    @BindView(R.id.txt_total_shipment_chain_name)
    TextView mTxtTotalShipmentChainName;
    @BindView(R.id.txt_chain_name)
    TextView mTxtChainName;
    @BindView(R.id.txt_chain_name1)
    TextView mTxtChainName1;

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

    //设置选中的币的信息
    private void setDataOfChain(WalletHomeRsBean.ResultBean.DataBean dataBean) {
        mTxtTotalShipmentChainName.setText("（"+dataBean.getSymbol()+"）");
        mTxtTotalShipment.setText(String.format("%.2f",Double.parseDouble(dataBean.getNum())));
        mTxtChainNumber.setText(String.format("%.2f",Double.parseDouble(dataBean.getNum())));
        mTxtChainName.setText(dataBean.getSymbol());
        mTxtChainNumber1.setText(String.format("%.2f",Double.parseDouble(dataBean.getNum())));
        mTxtChainName1.setText(dataBean.getSymbol());

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
        mDataBeans = new ArrayList<>();
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
        });

        //
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

    @OnClick({R.id.btn_submit, R.id.btn_floating_price, R.id.btn_fixed_price})
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
        }else if (v.getId() == R.id.btn_fixed_price){//一口价
            priceType = 1;
            mBtnFloatingPrice.setBackground(null);
            mBtnFloatingPrice.setTextColor(getActivity().getResources().getColor(R.color.colorMyRight));

            mBtnFixedPrice.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_button_yellow));
            mBtnFixedPrice.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
        }
    }

    private void submit() {
        DecimalFormat df = new DecimalFormat("######0.00");
        //封装数据到Map
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(getActivity()).getString(Constants.TOKEN));
        map.put("token_id",mDataBean.getToken_id());//选中的币的id
        map.put("type",priceType);//一口价 浮动价
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
        //备注
        String vComment = mEtComment.getText().toString();
        if(TextUtils.isEmpty(vComment)){
            ToastUtil.showShort("备注不能为空!");
            return;
        }
        map.put("remark",vComment);
        //挂单数量  总出货量
        String vTotalShipment = mTxtTotalShipment.getText().toString();
        if(TextUtils.isEmpty(vTotalShipment) ){
            ToastUtil.showShort("出货总量不能为空，或不能大于总量");
            return;
        }
//        double vNum = (double)Double.parseDouble(vTotalShipment);//不为空，转换为数字
//        if(vNum > mDataBean.getNum()){//判断出货量  不能为空
//
//        }
        map.put("num",String.format("%.2f",Double.parseDouble(vTotalShipment)));
        //溢价  不可以为空
        String price = mTxtSetPrice.getText().toString();
        if(TextUtils.isEmpty(price)){
            ToastUtil.showShort("溢价不能为空！");
            return;
        }
        map.put("price",String.format("%.2f",Double.parseDouble(price)));
        //浮动百分比  可以为空
        String floatPrice = mTxtFloatingPrice.getText().toString();
        if(!TextUtils.isEmpty(floatPrice)){
            map.put("price_float",String.format("%.2f",Double.parseDouble(floatPrice)));
        }
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
        //加密
        String json= SystemUtils.getJson(map);
        presenter.saleChain(json);//卖币
    }

    //得到我的币资产的返回
    @Override
    public void getWalletHomeReturn(WalletHomeRsBean walletHomeRsBean) {
        mDataBeans.clear();
        mDataBeans.addAll(walletHomeRsBean.getResult().getData());
        mAdapter.notifyDataSetChanged();
        //进入页面获得资产数据，修改 界面中所有的币名,,进入默认是第一个币
        mDataBean = mDataBeans.get(0);//选中的要卖的币
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
