package com.code.safechain.presenter;

import android.util.Log;

import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.MyPaytypeConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.my.bean.UploadIconRsBean;
import com.code.safechain.ui.transaction.bean.SetPayTypeRsBean;
import com.code.safechain.utils.RxUtils;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Auther: hchen
 * @Date: 2020/7/8 0008
 * @Description:
 */
public class MyPayTypePresenter extends BasePresenter<MyPaytypeConstract.View> implements MyPaytypeConstract.Presenter {

    private Disposable mDisposable;

    @Override
    public void addPaytype(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);

        HttpManager.getInstance().getApiServer().addPaytype(body)
            .compose(RxUtils.<SetPayTypeRsBean>changeScheduler())
            .subscribe(new Observer<SetPayTypeRsBean>() {
                @Override
                public void onSubscribe(Disposable d) {
                    mDisposable=d;
                }

                @Override
                public void onNext(SetPayTypeRsBean payTypeRsBean) {
                    mView.addPaytypeReturn(payTypeRsBean);
                }

                @Override
                public void onError(Throwable e) {
                    String s1 = e.getMessage();
                    Log.i("111", "onError: ");
                }

                @Override
                public void onComplete() {

                }
            });

        addSubscribe(mDisposable);
    }

    @Override
    public void uploadWechatIcon(String token,File file) {

        MediaType mediaType = MediaType.parse("application/octet-stream");
        if(file.exists()) {
            RequestBody requestBody = RequestBody.create(mediaType, file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

            HttpManager.getInstance().getApiServer().uploadIcon(token,part)
                    .compose(RxUtils.<UploadIconRsBean>changeScheduler())
                    .subscribe(new Observer<UploadIconRsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mDisposable=d;
                        }

                        @Override
                        public void onNext(UploadIconRsBean uploadIconRsBean) {
                            mView.uploadWechatIconReturn(uploadIconRsBean);
                            String s = "";
                        }

                        @Override
                        public void onError(Throwable e) {
                            String str = e.getMessage();
                            String s = "";
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

            addSubscribe(mDisposable);
        }

    }

    @Override
    public void uploadAlipayIcon(String token,File file) {
        MediaType mediaType = MediaType.parse("application/octet-stream");
        if(file.exists()) {
            RequestBody requestBody = RequestBody.create(mediaType, file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

            HttpManager.getInstance().getApiServer().uploadIcon(token,part)
                    .compose(RxUtils.<UploadIconRsBean>changeScheduler())
                    .subscribe(new Observer<UploadIconRsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mDisposable=d;
                        }

                        @Override
                        public void onNext(UploadIconRsBean uploadIconRsBean) {
                            mView.uploadAlipayIconReturn(uploadIconRsBean);
                            String s = "";
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

            addSubscribe(mDisposable);
        }
    }


}
