package com.code.safechain.model.apis;

import com.code.safechain.ui.login.bean.RegistRsBean;
import com.code.safechain.ui.login.bean.VerificationRsBean;
import com.code.safechain.ui.wallet.bean.AddAddressRsBean;
import com.code.safechain.ui.wallet.bean.ChainTransactionRsBean;
import com.code.safechain.ui.wallet.bean.TransferRsBean;
import com.code.safechain.ui.wallet.bean.WalletAddressRsBean;
import com.code.safechain.ui.wallet.bean.WalletHomeRsBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    //获取验证码
    @POST("/api/user/code")
    Observable<VerificationRsBean> getVerificationCode(@Body RequestBody requestBody);

    //注册
    @POST("/api/user")
    Observable<RegistRsBean> regist(@Body RequestBody requestBody);

    //登录
    @POST("/api/login")
    Observable<RegistRsBean> login(@Body RequestBody requestBody);

    //钱包首页
    @GET("/api/common")
    Observable<WalletHomeRsBean> getWalletHome(@QueryMap Map<String, Object> map);

    //钱包  币的交易列表 详情
    @GET("/api/tran")
    Observable<ChainTransactionRsBean> getWalletChainTrans(@QueryMap Map<String, Object> map);

    //获取钱包地址
    @GET("/api/wallet")
    Observable<WalletAddressRsBean> getWalletAddress(@QueryMap Map<String, Object> map);

    //添加钱包地址
    @POST("/api/wallet")
    Observable<AddAddressRsBean> addAddress(@Body RequestBody requestBody);//添加钱包地址

    //转账
    @POST("/api/tran")
    Observable<TransferRsBean> transfer(@Body RequestBody requestBody);

}
