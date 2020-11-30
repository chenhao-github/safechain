package com.code.safechain.ui.my;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.MyPaytypeConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.presenter.MyPayTypePresenter;
import com.code.safechain.ui.my.bean.UploadIconRsBean;
import com.code.safechain.ui.transaction.bean.GetPayTypeRsBean;
import com.code.safechain.ui.transaction.bean.SetPayTypeRsBean;
import com.code.safechain.utils.RxUtils;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PayTypeActivity extends BaseActivity<MyPaytypeConstract.Presenter> implements MyPaytypeConstract.View {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    //导航 行
    @BindView(R.id.img_wechat)
    ImageView mImgWechat;
    @BindView(R.id.txt_wechat)
    TextView mTxtWechat;
    @BindView(R.id.img_alipay)
    ImageView mImgAlipay;
    @BindView(R.id.txt_alipay)
    TextView mTxtAlipay;
    @BindView(R.id.img_unionpay)
    ImageView mImgUnionpay;
    @BindView(R.id.txt_unionpay)
    TextView mTxtUnionpay;
    //微信 支付宝 银联 操作区域
    @BindView(R.id.rl_wechat_upload_update)
    RelativeLayout mRlWechatUpload;
    @BindView(R.id.rl_alipay_upload_update)
    RelativeLayout mRlAlipayUpload;
    @BindView(R.id.rl_unionpay_upload_update)
    RelativeLayout mRlUnionpayUpload;
    //微信
    @BindView(R.id.et_wechat_name)//输入框
    EditText mEtWechatName;
    @BindView(R.id.img_wechat_icon)//微信二维码
    ImageView mImgWechatIcon;
    @BindView(R.id.txt_wechat_upload_icon)//上传
    TextView mTxtWechatUpload;
    //支付宝
    @BindView(R.id.et_alipay_name)//输入框
    EditText mEtAlipayName;
    @BindView(R.id.img_alipay_icon)//支付宝二维码
    ImageView mImgAlipayIcon;
    @BindView(R.id.txt_alipay_upload_icon)//上传
    TextView mTxtAlipayUpload;
    //银联
    @BindView(R.id.et_user_name)//持卡人姓名
    EditText mEtUserName;
    @BindView(R.id.sp_bank_name)//银行下拉框
    EditText mSpBankName;
    @BindView(R.id.et_bank_address)//银行地址
    EditText mEtBankAddress;
    @BindView(R.id.et_bank_no)//银行卡号
    EditText mEtBankNo;
    @BindView(R.id.btn_submit)//提交 按钮
    Button mBtnSubmit;

    private ArrayList<String> mBanksList;//银行列表数据
    private ArrayAdapter<String> bankAdapter;//下拉框框适配器

    //支付信息  -微信二维码
    private String mWexinIcon;
    //支付宝二维码
    private String mAlipayIcon;
//    private String mBankName;//银行名字

    private File mFile;//相册中的图片
    private String mToken;


    @Override
    protected int getLayout() {
        return R.layout.activity_pay_type;
    }

    @Override
    protected MyPaytypeConstract.Presenter createPresenter() {
        return new MyPayTypePresenter();
    }

    @Override
    protected void initView() {
        //获取支付方式，如果已有支付信息，则显示
        getPaytype();

    }
    //获取支付方式
    private void getPaytype() {
        //封装数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
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
                        GetPayTypeRsBean.ResultBean result = getPayTypeRsBean.getResult();
                        if(getPayTypeRsBean.getError() == 0 && result != null && result.getPays().size()>0){//有支付方式
                            initPayData(result);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initPayData(GetPayTypeRsBean.ResultBean result) {
        List<GetPayTypeRsBean.ResultBean.PaysBean> pays = result.getPays();//获得支付方式
        for (int i = 0; i < pays.size(); i++) {
            GetPayTypeRsBean.ResultBean.PaysBean paysBean = pays.get(i);
            if(paysBean.getType() == 1){//微信支付
                if(!TextUtils.isEmpty(paysBean.getImg_url())){
                    mEtWechatName.setText(paysBean.getUser_name());
                    Glide.with(this).load(paysBean.getImg_url()).into(mImgWechatIcon);
                    mWexinIcon = paysBean.getImg_url();//设置微信二维码路径
                    mTxtWechatUpload.setText(getResources().getString(R.string.my_paytype_re_upload));
                }
            }else if(paysBean.getType() == 2){//支付宝支付
                if(!TextUtils.isEmpty(paysBean.getImg_url())){
                    mEtAlipayName.setText(paysBean.getUser_name());
                    Glide.with(this).load(paysBean.getImg_url()).into(mImgAlipayIcon);
                    mAlipayIcon = paysBean.getImg_url();//设置支付宝二维码路径
                    mTxtAlipayUpload.setText(getResources().getString(R.string.my_paytype_re_upload));
                }
            }else if(paysBean.getType() == 4){//银联支付
                if(!TextUtils.isEmpty(paysBean.getUser_name()))//设置银行账号名字
                    mEtUserName.setText(paysBean.getUser_name());
                if(!TextUtils.isEmpty(paysBean.getBank_name())){//银行名字不为空，已经设置过，使用设置过的数据
//                    mBankName = paysBean.getBank_name();
//                    mSpBankName.setSelection(mBanksList.indexOf(mBankName),true);
                    mSpBankName.setText(paysBean.getBank_name());
                }
                if(!TextUtils.isEmpty(paysBean.getPay_addr()))//银行地址不为空
                    mEtBankAddress.setText(paysBean.getPay_addr());
                if(!TextUtils.isEmpty(paysBean.getBank_no()))//银行卡号不为空
                    mEtBankNo.setText(paysBean.getBank_no());
            }
        }
    }

    @Override
    protected void initData() {
        //配置银行 下拉框数据
//        initSpinnerCountry();
        mToken = SpUtils.getInstance(this).getString(Constants.TOKEN);
    }
    private void initSpinnerCountry() {
        //创建数据源
//        mBanksList = new ArrayList<>();
//        mBanksList.add("中国银行");
//        mBanksList.add("工商银行");
//        mBanksList.add("建设银行");
//        mBanksList.add("交通银行");
//        mBanksList.add("农业银行");
//        mBanksList.add("招商银行");

//        //定义适配器
//        bankAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mBanksList);
//        //设置下拉列表下拉时的菜单样式
//        bankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //将适配器添加到下拉列表上
//        mSpBankName.setAdapter(bankAdapter);
//        mSpBankName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mBankName = mBanksList.get(position);//得到选中的银行
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        mBankName = mBanksList.get(0);//默认是中国银行
    }

    @Override
    public void addPaytypeReturn(SetPayTypeRsBean payTypeRsBean) {
        if(payTypeRsBean.getError() == 0){
            ToastUtil.showShort("添加成功");
            setResult(RESULT_OK);
            finish();
        }else {
            ToastUtil.showShort(payTypeRsBean.getMessage());
        }
    }

    @Override
    public void uploadWechatIconReturn(UploadIconRsBean uploadIconRsBean) {
        if(uploadIconRsBean.getError() == 0){
            ToastUtil.showShort("上传成功!");
            mBtnSubmit.setEnabled(true);//设置可用
            mWexinIcon = uploadIconRsBean.getResult().getImg_url();//获得微信支付二维码
            Glide.with(this).load(mWexinIcon).into(mImgWechatIcon);
            mTxtWechatUpload.setText(getResources().getString(R.string.my_paytype_re_upload));
        }
    }

    @Override
    public void uploadAlipayIconReturn(UploadIconRsBean uploadIconRsBean) {
        if(uploadIconRsBean.getError() == 0){
            ToastUtil.showShort("上传成功!");
            mBtnSubmit.setEnabled(true);//设置可用
            mAlipayIcon = uploadIconRsBean.getResult().getImg_url();//获得支付宝支付二维码
            Glide.with(this).load(mAlipayIcon).into(mImgAlipayIcon);
            mTxtAlipayUpload.setText(getResources().getString(R.string.my_paytype_re_upload));
        }
    }

    @OnClick({R.id.img_back, R.id.rl_wechat_title, R.id.rl_alipay_title, R.id.rl_unionpay_title, R.id.btn_submit,
    R.id.txt_wechat_upload_icon, R.id.txt_alipay_upload_icon})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
                /*点击 导航 切换 操作区域的显示*/
            case R.id.rl_wechat_title:
                switchPaytype (R.id.rl_wechat_title);
                break;
            case R.id.rl_alipay_title:
                switchPaytype (R.id.rl_alipay_title);
                break;
            case R.id.rl_unionpay_title:
                switchPaytype (R.id.rl_unionpay_title);
                break;
            /*上传按钮*/
            case R.id.txt_wechat_upload_icon: //微信上传
                pickUpload(100);
                break;
            case R.id.txt_alipay_upload_icon: //支付宝上传
                pickUpload(200);
                break;
            case R.id.btn_submit:
                //提交
                submitPaytype();
                break;
        }
    }

    private void submitPaytype() {
        //得到支付方式信息
        //微信名字
        String wexinName = mEtWechatName.getText().toString();
        //支付宝名字
        String alipayName = mEtAlipayName.getText().toString();
        //银行账号名字
        String userName = mEtUserName.getText().toString();
        String bankName = mSpBankName.getText().toString();
        //银行地址
        String bankAdddress = mEtBankAddress.getText().toString();
        //银行卡号
        String bankCarNo = mEtBankNo.getText().toString();
        //封装数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("type",1);
        map.put("token",mToken);
        //微信
        if(!TextUtils.isEmpty(wexinName)){
            map.put("wechat_user_name",wexinName);
        }
        if(!TextUtils.isEmpty(mWexinIcon)){
            map.put("wechat_img_url",mWexinIcon);
        }else {//为空时，如果是重新上传，则提示等待上传成功
            if(mTxtWechatUpload.getText().equals(getResources().getString(R.string.my_paytype_re_upload))){
                ToastUtil.showShort("请等待上传成功再试!");
                return;
            }
        }
        //支付宝
        if(!TextUtils.isEmpty(alipayName)){
            map.put("ali_user_name",alipayName);
        }
        if(!TextUtils.isEmpty(mAlipayIcon)){
            map.put("ali_img_url",mAlipayIcon);
        }else {//为空时，如果是重新上传，则提示等待上传成功
            if(mTxtAlipayUpload.getText().equals(getResources().getString(R.string.my_paytype_re_upload))){
                ToastUtil.showShort("请等待上传成功再试!");
                return;
            }
        }
        //银行卡
        if(!TextUtils.isEmpty(userName)){
            map.put("union_user_name",userName);
        }
        if(!TextUtils.isEmpty(bankName)){
            map.put("bank_name",bankName);
        }

        if(!TextUtils.isEmpty(bankAdddress)){
            map.put("pay_addr",bankAdddress);
        }
        if(!TextUtils.isEmpty(bankCarNo)){
            map.put("bank_no",bankCarNo);
        }

        //计算 pay_type 的值
        int num = 0;
        if(!TextUtils.isEmpty(wexinName) && !TextUtils.isEmpty(mWexinIcon))//微信支付信息是全的
            num += 1;
        if(!TextUtils.isEmpty(alipayName) && !TextUtils.isEmpty(mAlipayIcon))
            num += 2;
        if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(bankCarNo)
                && !TextUtils.isEmpty(bankAdddress) && !TextUtils.isEmpty(bankName))
            num += 4;

        map.put("pay_type",num);
        //加密
        String json = SystemUtils.getJson(map);

        presenter.addPaytype(json);
    }

    private void pickUpload(int requestCode) {
        //打开相册
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent,requestCode);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){//微信上传
            Uri uri = data.getData();//获得此张照片的uri路径
            String scheme = uri.getScheme();//得到类型空间
            String filePath ="" ;
            if(scheme.equals("file")){
                filePath = uri.getPath();//得到相册图片的路径
            }else if(scheme.equals("content")){
                //到内容提供者中读取图片的路径
                filePath = getPathFromContent(uri);
            }
            if(!"".equals(filePath)){//图片的路径不为""空的双引号，可以上传
                mFile = new File(filePath);
                presenter.uploadWechatIcon(mToken,mFile);
                mBtnSubmit.setEnabled(false);//设置不可用
            }
        }
        if(requestCode == 200 && resultCode == RESULT_OK){//支付宝上传
            Uri uri = data.getData();//获得此张照片的uri路径
            String scheme = uri.getScheme();//得到类型空间
            String filePath ="" ;
            if(scheme.equals("file")){
                filePath = uri.getPath();//得到相册图片的路径
            }else if(scheme.equals("content")){
                //到内容提供者中读取图片的路径
                filePath = getPathFromContent(uri);
            }
            if(!"".equals(filePath)){//图片的路径不为""空的双引号，可以上传
                mFile = new File(filePath);
                presenter.uploadAlipayIcon(mToken,mFile);
                mBtnSubmit.setEnabled(false);//设置不可用
            }
        }

    }

    /**
     * 从内容提供者中读取相册的图片
     * @param uri
     * @return
     */
    private String getPathFromContent(Uri uri) {
        if(uri == null)
            return "";
        ContentResolver resolver = getContentResolver();
        String[] pros = {MediaStore.Images.ImageColumns.DATA};
        Cursor cursor = resolver.query(uri, pros, null, null, null);
        String filePath ="";
        if(cursor != null){
            cursor.moveToFirst();//因为只有一张，所以读取第一张即可
            filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
        }
        return filePath;
    }

    private void switchPaytype(int rl_id) {
        if(rl_id == R.id.rl_wechat_title){//微信选中
            //设置微信选中效果
            mImgWechat.setImageResource(R.mipmap.ic_add_paytype_wechat_select);
            mTxtWechat.setTextColor(getResources().getColor(R.color.colorTitle));
            mRlWechatUpload.setVisibility(View.VISIBLE);
            //设置支付宝不选中效果
            mImgAlipay.setImageResource(R.mipmap.ic_add_paytype_alipay_noselect);
            mTxtAlipay.setTextColor(getResources().getColor(R.color.colorMyRight));
            mRlAlipayUpload.setVisibility(View.INVISIBLE);
            //设置银联不选中效果
            mImgUnionpay.setImageResource(R.mipmap.ic_add_paytype_unionpay_noselect);
            mTxtUnionpay.setTextColor(getResources().getColor(R.color.colorMyRight));
            mRlUnionpayUpload.setVisibility(View.INVISIBLE);
        }else if(rl_id == R.id.rl_alipay_title){//支付宝选中
            mImgAlipay.setImageResource(R.mipmap.ic_add_paytype_alipay_select);
            mTxtAlipay.setTextColor(getResources().getColor(R.color.colorTitle));
            mRlAlipayUpload.setVisibility(View.VISIBLE);

            mImgWechat.setImageResource(R.mipmap.ic_add_paytype_wechat_noselect);
            mTxtWechat.setTextColor(getResources().getColor(R.color.colorMyRight));
            mRlWechatUpload.setVisibility(View.INVISIBLE);

            mImgUnionpay.setImageResource(R.mipmap.ic_add_paytype_unionpay_noselect);
            mTxtUnionpay.setTextColor(getResources().getColor(R.color.colorMyRight));
            mRlUnionpayUpload.setVisibility(View.INVISIBLE);
        }else {
            mImgUnionpay.setImageResource(R.mipmap.ic_add_paytype_unionpay_select);
            mTxtUnionpay.setTextColor(getResources().getColor(R.color.colorTitle));
            mRlUnionpayUpload.setVisibility(View.VISIBLE);

            mImgWechat.setImageResource(R.mipmap.ic_add_paytype_wechat_noselect);
            mTxtWechat.setTextColor(getResources().getColor(R.color.colorMyRight));
            mRlWechatUpload.setVisibility(View.INVISIBLE);

            mImgAlipay.setImageResource(R.mipmap.ic_add_paytype_alipay_noselect);
            mTxtAlipay.setTextColor(getResources().getColor(R.color.colorMyRight));
            mRlAlipayUpload.setVisibility(View.INVISIBLE);
        }
    }
}
