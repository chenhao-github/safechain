package com.code.safechain.ui.transaction;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.code.safechain.R;
import com.code.safechain.base.BaseFragment;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.TransactionConstract;
import com.code.safechain.model.bean.ChainSaleBean;
import com.code.safechain.presenter.TransactionPresenter;
import com.code.safechain.ui.transaction.adapter.BuyChainSaleAdapter;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @Auther: hchen
 * @Date: 2020/7/22 0022
 * @Description:
 */
public class BuyFragment extends BaseFragment<TransactionConstract.Presenter>
        implements TransactionConstract.View, View.OnClickListener {
    @BindView(R.id.rlv_buy_chain)
    RecyclerView mRlvBuyChain;
    private ArrayList<ChainSaleBean> mChainSaleBeans;
    private BuyChainSaleAdapter mAdapter;
    private PopupWindow mPw;
    private TextView mBuyMoney;
    private TextView mBuyNumber;
    private TextView mPlaceOrder;
    private EditText mInput;
    //购买类型
    private static final int TYPE_MONEY = 0;
    private static final int TYPE_NUMBER = 1;

    @Override
    protected int getLayout() {
        return R.layout.fragment_transaction_buy;
    }

    @Override
    protected TransactionConstract.Presenter createPresenter() {
        return new TransactionPresenter();
    }

    @Override
    protected void initView() {
        mRlvBuyChain.setLayoutManager(new LinearLayoutManager(getActivity()));
        mChainSaleBeans = new ArrayList<>();
        mAdapter = new BuyChainSaleAdapter(getActivity(), mChainSaleBeans);
        mRlvBuyChain.setAdapter(mAdapter);
        mAdapter.setOnClickListener(this);//添加 条目中的组件的点击监听
    }

    @Override
    protected void initData() {
        mChainSaleBeans.add(new ChainSaleBean("焰火二号","1234567.123 USDT","￥300,000-￥400,000","198 | 98%","￥ 7.03"));
        mChainSaleBeans.add(new ChainSaleBean("好火二号","1234567.123 USDT","￥300,000-￥400,000","198 | 98%","￥ 7.03"));
        mChainSaleBeans.add(new ChainSaleBean("看火二号","1234567.123 USDT","￥300,000-￥400,000","198 | 98%","￥ 7.03"));
        mChainSaleBeans.add(new ChainSaleBean("鹏火二号","1234567.123 USDT","￥300,000-￥400,000","198 | 98%","￥ 7.03"));
        mAdapter.notifyDataSetChanged();
    }

    //条目中的 购买 按钮的点击回调
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id ){//点击的是购买按钮
            case R.id.btn_buy:
                showPwToBuy(v);//处理购买
                break;
            case R.id.txt_buy_money://按金额购买
                dealBuySaleAndFragment(TYPE_MONEY);
                break;
            case R.id.txt_buy_number://按数量购买
                dealBuySaleAndFragment(TYPE_NUMBER);
                break;
            case R.id.btn_place_order://下单
                ToastUtil.showShort("下单");
                //得到点击的数据，和 付款金额 传递到下单页面
                ChainSaleBean chainSaleBean = (ChainSaleBean) v.getTag();
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                startActivity(intent);
                break;
        }

    }
    //点击 购买按钮的回调
    private void showPwToBuy(View v) {
        ChainSaleBean chainSaleBean = (ChainSaleBean) v.getTag();//得到点击的数据
        //弹出我要买(下单)pw
        View pwLayout = View.inflate(getActivity(), R.layout.popup_trans_buy_pay_preview, null);
        mPw = new PopupWindow(pwLayout, ViewGroup.LayoutParams.MATCH_PARENT, 1130);
        mPw.setFocusable(true);
        mPw.showAtLocation(mRlvBuyChain, Gravity.BOTTOM,0,0);
        SystemUtils.setBackgroundAlpha(getActivity().getWindow(), Constants.SHADOW);//显示阴影
        //处理组件
        initViewOfPw(pwLayout);
    }

    private void initViewOfPw(View pwLayout) {
        TextView chainName = pwLayout.findViewById(R.id.txt_chain_name);//币的名字
        TextView unitPrice = pwLayout.findViewById(R.id.txt_unit_price);//单价
        ImageView chainIcon = pwLayout.findViewById(R.id.img_chain_icon);//币的图标
        //按金额购买
        mBuyMoney = pwLayout.findViewById(R.id.txt_buy_money);
        //按数量购买
        mBuyNumber = pwLayout.findViewById(R.id.txt_buy_number);
        //输入框
        mInput = pwLayout.findViewById(R.id.et_input);
        TextView quota = pwLayout.findViewById(R.id.txt_quota);//限额
        TextView number = pwLayout.findViewById(R.id.txt_trans_number);//交易数量
        TextView actualPayment = pwLayout.findViewById(R.id.txt_trans_actual_payment);//实付款
        TextView cancel = pwLayout.findViewById(R.id.btn_auto_cancel);//取消
        //下单
        mPlaceOrder = pwLayout.findViewById(R.id.btn_place_order);

        //赋值

        //添加监听
        mBuyMoney.setOnClickListener(this);
        mBuyNumber.setOnClickListener(this);
        mPlaceOrder.setOnClickListener(this);
    }

    /**
     * 处理 我要买和我要卖 的字体颜色
     * @param type  0 按金额  1 按数量
     */
    private void dealBuySaleAndFragment(int type) {
        if(type == TYPE_MONEY){
            //切换 我要买  我要卖 的字体大小和颜色
            mBuyMoney.setTextColor(getResources().getColor(R.color.colorTransfer));
            mBuyNumber.setTextColor(getResources().getColor(R.color.colorTitle));
        }else {
            mBuyMoney.setTextColor(getResources().getColor(R.color.colorTitle));
            mBuyNumber.setTextColor(getResources().getColor(R.color.colorTransfer));
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
