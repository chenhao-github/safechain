package com.code.safechain.model;

import android.util.Log;

import com.code.safechain.app.BaseApp;
import com.code.safechain.common.Constants;
import com.code.safechain.model.apis.ApiService;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 封装网络请求的类型
 * Retrofit+Rxjava
 *
 */
public class HttpManager {

    private static volatile HttpManager instance;
    public static HttpManager getInstance(){
        if(instance == null){
            synchronized (HttpManager.class){
                if(instance == null){
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    //网络接口类
    private ApiService apiServer;

    /**
     * 获取一个Retrofit对象
     * @return
     */
    private Retrofit getRetrofit(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient())
                .build();
        return retrofit;
    }

    /**
     * 封装网络请求的Okhttpclient对象
     * @return
     */
    private OkHttpClient okHttpClient(){
        File file = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(file,100*1024*1024);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new HeadersInterceptor())
                .addNetworkInterceptor(new NetWorkInterceptor())
                .cache(cache)
                .build();
        return client;
    }

    /**
     * 获取ApiService网络接口类
     * @return
     */
    public ApiService getApiServer(){
        if(apiServer == null){
            synchronized (ApiService.class){
                if(apiServer == null){
                    apiServer = getRetrofit(Constants.BASE_SHOP_URL).create(ApiService.class);
                }
            }
        }
        return apiServer;
    }

    /**
     * 日志拦截器，打印请求接口的报文信息
     * 提供日志信息帮组优化代码
     */
    static class LoggingInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            //通过系统时间的差打印接口请求的信息
            long startTime = System.nanoTime();
            Request request = chain.request();
            Log.i("Request:",String.format("Sending request %s on %s%n%s",request.url(),chain.connection(),request.headers()));
            Response response = chain.proceed(request);
            long endTime = System.nanoTime();
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            Log.i("Received:",String.format("Received response for %s in %.1fms%n%s json:%s",response.request().url(),
                    (endTime-startTime)/1e6d,response.headers(),responseBody.string()));
            return response;
        }
    }
    /**
     * 请求的修改设置
     */
    static class HeadersInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
                    .addHeader("Client-Type","ANDROID")
                    .addHeader("X-Nideshop-Token", SpUtils.getInstance(BaseApp.baseApp).getString("token"))
                    .build();
            return chain.proceed(request);
        }
    }

    /**
     * 网络拦截器
     */
    static class NetWorkInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if(!SystemUtils.checkNetWork()){
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            //通过判断网络连接是否存在获取本地或者服务器的数据
            if(!SystemUtils.checkNetWork()){
                int maxAge = 0;
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control","public ,max-age="+maxAge).build();
            }else{
                int maxStale = 60*60*24*28; //设置缓存数据的保存时间
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control","public, onlyif-cached, max-stale="+maxStale).build();
            }
        }
    }


}
