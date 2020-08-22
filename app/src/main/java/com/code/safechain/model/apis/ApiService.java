package com.code.safechain.model.apis;

import com.code.safechain.ui.login.bean.CountryCodeBean;
import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.ui.login.bean.VerificationRsBean;
import com.code.safechain.ui.main.bean.UserBean;
import com.code.safechain.ui.my.bean.GestureRsBean;
import com.code.safechain.ui.my.bean.UploadIconRsBean;
import com.code.safechain.ui.transaction.bean.MySaleOrderRsBean;
import com.code.safechain.ui.transaction.bean.OrderRsBean;
import com.code.safechain.ui.transaction.bean.PayTypeRsBean;
import com.code.safechain.ui.transaction.bean.OthersSaleOrderRsBean;
import com.code.safechain.ui.transaction.bean.TransactionBuyRsBean;
import com.code.safechain.ui.wallet.bean.AddAddressRsBean;
import com.code.safechain.ui.wallet.bean.ChainInfoRsBean;
import com.code.safechain.ui.wallet.bean.ChainTransactionRsBean;
import com.code.safechain.ui.wallet.bean.TransferRsBean;
import com.code.safechain.ui.wallet.bean.WalletAddressOnlyRsBean;
import com.code.safechain.ui.wallet.bean.WalletAddressRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

public interface ApiService {
    //获取验证码
    @POST("/api/user/code")
    Observable<VerificationRsBean> getVerificationCode(@Body RequestBody requestBody);

    //注册
    @POST("/api/user")
    Observable<RegistRsBean> regist(@Body RequestBody requestBody);

    //修改用户信息
    @PUT("/api/user")
    Observable<GestureRsBean> updateUser(@Body RequestBody requestBody);

    //获得用户信息
    @GET("/api/user")
    Observable<UserBean> getUserData(@QueryMap Map<String, Object> map);

    //登录
    @POST("/api/login")
    Observable<RegistRsBean> login(@Body RequestBody requestBody);

    //钱包首页
    @GET("/api/common")
    Observable<WalletHomeRsBean> getWalletHome(@QueryMap Map<String, Object> map);

    //钱包  币的交易列表 详情
    @GET("/api/tran")
    Observable<ChainTransactionRsBean> getWalletChainTrans(@QueryMap Map<String, Object> map);

    //币详情 非交易列表
    @GET("/api/common")
    Observable<ChainInfoRsBean> getChainInfo(@QueryMap Map<String, Object> map);

    //验证交易密码
    @GET("/api/common")
    Observable<GestureRsBean> checkPaywd(@QueryMap Map<String, Object> map);

    //获取所有钱包地址
    @GET("/api/wallet")
    Observable<WalletAddressRsBean> getWalletAddress(@QueryMap Map<String, Object> map);

    //获取单个币的钱包地址
    @GET("/api/wallet")
    Observable<WalletAddressOnlyRsBean> getWalletAddressOnly(@QueryMap Map<String, Object> map);

    //添加钱包地址
    @POST("/api/wallet")
    Observable<AddAddressRsBean> addAddress(@Body RequestBody requestBody);//添加钱包地址

    //转账
    @POST("/api/tran")
    Observable<TransferRsBean> transfer(@Body RequestBody requestBody);

    //我要买  卖币列表
    @GET("/api/otc")
    Observable<OthersSaleOrderRsBean> getSaleOrderList(@QueryMap Map<String, Object> map);

    //买币
    @POST("/api/buy")
    Observable<TransactionBuyRsBean> buyChain(@Body RequestBody requestBody);//添加钱包地址

    //上传 支付二维码等图片
    @POST("/api/common/img_upload")
    @Multipart
    Observable<UploadIconRsBean> uploadIcon(@Part("token") String token, @Part MultipartBody.Part file);//上传图片

    //添加支付方式
    @POST("/api/common")
    Observable<PayTypeRsBean> addPaytype(@Body RequestBody requestBody);//添加钱包地址

    //我要卖
    @POST("/api/otc")
    Observable<MySaleOrderRsBean> mySaleChain(@Body RequestBody requestBody);

    //订单列表
    @GET("/api/order")
    Observable<OrderRsBean>  getOrders(@QueryMap Map<String, Object> map);

    //国家代码
    @GET("/api/common")
    Observable<CountryCodeBean> getCountryCode(@QueryMap Map<String, Object> map);

}
