package com.code.safechain.presenter;

import android.util.Log;

import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.MyPaytypeConstract;
import com.code.safechain.interfaces.RealNameConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.my.bean.GestureRsBean;
import com.code.safechain.ui.my.bean.UploadIconRsBean;
import com.code.safechain.utils.LoggerUtil;
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
public class RealNamePresenter extends BasePresenter<RealNameConstract.View> implements RealNameConstract.Presenter {

    private Disposable mDisposable;

    @Override
    public void updateRealName(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);

        HttpManager.getInstance().getApiServer().updateUser(body)
            .compose(RxUtils.<GestureRsBean>changeScheduler())
            .subscribe(new Observer<GestureRsBean>() {
                @Override
                public void onSubscribe(Disposable d) {
                    mDisposable=d;
                }

                @Override
                public void onNext(GestureRsBean gestureRsBean) {
                    mView.updateRealNameReturn(gestureRsBean);
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

    @Override
    public void uploadCardZIcon(String token,File file) {

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
                            mView.uploadCardZIconReturn(uploadIconRsBean);

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

    @Override
    public void uploadCardBIcon(String token,File file) {
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
                            mView.uploadCardBIconReturn(uploadIconRsBean);
                            String s = "";
                        }

                        @Override
                        public void onError(Throwable e) {
                            LoggerUtil.logI("222","上传错误："+e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

            addSubscribe(mDisposable);
        }
    }


}
