package com.code.safechain.ui.wallet;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.WalletAddressConstract;
import com.code.safechain.interfaces.WalletConstract;
import com.code.safechain.model.bean.Chain;
import com.code.safechain.presenter.WalletAddressPresenter;
import com.code.safechain.presenter.WalletPresenter;
import com.code.safechain.ui.wallet.bean.WalletAddressRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SwipeItemLayout;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddressBookActivity extends BaseActivity<WalletAddressConstract.Presenter> implements WalletAddressConstract.View {
    @BindView(R.id.img_chain_icon)
    ImageView mImgChainIcon;
    @BindView(R.id.txt_nodata)
    TextView mTxtNoData;
    @BindView(R.id.rlv_chain_address)
    RecyclerView mChainAddress;

    private ArrayList<WalletAddressRsBean.ResultBean.DataBean> mAddress;
    private SwipeItemLayout.OnSwipeItemTouchListener mOnSwipeItemTouchListener;
    private AddressAdapter mAddressAdapter;
    private WalletHomeRsBean.ResultBean.DataBean mChain;

    @Override
    protected int getLayout() {
        return R.layout.activity_address_book;
    }

    @Override
    protected WalletAddressConstract.Presenter createPresenter() {
        return new WalletAddressPresenter();
    }

    @Override
    protected void initView() {
        //得到当前币
        mChain = (WalletHomeRsBean.ResultBean.DataBean) getIntent().getSerializableExtra(Constants.DATA);

        //初始化地址本列表
        mChainAddress.setLayoutManager(new LinearLayoutManager(this));
        //添加RecycleView左滑，显示 修改，删除按钮
        mChainAddress.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        mAddress = new ArrayList<>();
        mAddressAdapter = new AddressAdapter();
        mChainAddress.setAdapter(mAddressAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        map = SystemUtils.getMap(map);
        presenter.getWalletAddress(map);
    }

    @Override
    public void getWalletAddressReturn(WalletAddressRsBean walletAddressRsBean) {
        List<WalletAddressRsBean.ResultBean.DataBean> datas = walletAddressRsBean.getResult().getData();
        mAddress.clear();//清空
        mAddress.addAll(datas);
        mAddressAdapter.notifyDataSetChanged();

        if(datas.size()>0){
            mImgChainIcon.setVisibility(View.INVISIBLE);
            mTxtNoData.setVisibility(View.INVISIBLE);
            mChainAddress.setVisibility(View.VISIBLE);
        }else {
            mImgChainIcon.setVisibility(View.VISIBLE);
            mTxtNoData.setVisibility(View.VISIBLE);
            mChainAddress.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick({R.id.img_back, R.id.img_address_book_add, R.id.btn_choose_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_address_book_add:
                Intent intent = new Intent(this, AddChainAddressActivity.class);
                intent.putExtra(Constants.DATA,mChain);
                startActivityForResult(intent,100);
                break;
            case R.id.btn_choose_confirm:
                //遍历判断 是否有选中的，如果没有提示 请选中
                addressConfirm();
                break;
        }
    }

    /**
     * 确认选择
     */
    private void addressConfirm() {
        WalletAddressRsBean.ResultBean.DataBean address = null;
        for (int i = 0; i < mAddress.size(); i++) {
            if (mAddress.get(i).isCheck()){
                address = mAddress.get(i);
                break;
            }
        }
        //判断有没有地址本背选中
        if(address == null){
            ToastUtil.showShort(getResources().getString(R.string.wallet_choose_hint));
        }else {
            Intent intent = new Intent();
            intent.putExtra(Constants.DATA,address);
            setResult(100,intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK){
            initData();
        }
    }

    public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(AddressBookActivity.this).
                    inflate(R.layout.item_wallet_chain_address, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final WalletAddressRsBean.ResultBean.DataBean data = mAddress.get(position);

//            Glide.with(AddressBookActivity.this).load(data.getImg()).into(holder.icon);
            holder.name.setText(data.getName());
            holder.address.setText(data.getAddr());

            holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    data.setCheck(isChecked);
                    if ((isChecked)){
                        for (int i = 0; i < mAddress.size(); i++) {
                            WalletAddressRsBean.ResultBean.DataBean chain = mAddress.get(i);
                            if(!data.equals(chain))
                                chain.setCheck(false);
                        }
                    }
                    //刷新适配器  recyclerView正在计算布局或者滚动的时候不允许直接刷新，要在线程中刷新
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            AddressAdapter.this.notifyDataSetChanged();
                        }
                    });
                }
            });
            holder.cb.setChecked(data.isCheck());
        }

        @Override
        public int getItemCount() {
            return mAddress.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
            ImageView icon;
            TextView name;
            TextView address;
            CheckBox cb;
            LinearLayout update;
            LinearLayout delete;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.img_chain_icon);
                name = itemView.findViewById(R.id.txt_chain_name);
                address = itemView.findViewById(R.id.txt_chain_address);
                cb = itemView.findViewById(R.id.cb_checked);

                //删除，修改按钮区域
                update = itemView.findViewById(R.id.ll_update);
                delete = itemView.findViewById(R.id.ll_delete);
                update.setOnClickListener(this);
                delete.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                //点击修改区域 回调此处
                if(v.getId() == R.id.ll_update){
                    Toast.makeText(v.getContext(), "修改  点击的位置为：" + getAdapterPosition(), Toast.LENGTH_SHORT).show();

                }else if(v.getId() == R.id.ll_delete){ //点击删除区域 回调此处
                    mAddress.remove(getAdapterPosition());
                    AddressAdapter.this.notifyDataSetChanged();
                }
            }

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        }
    }
}
