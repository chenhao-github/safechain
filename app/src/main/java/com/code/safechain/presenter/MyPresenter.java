package com.code.safechain.presenter;

import com.code.safechain.base.BasePresenter;
import com.code.safechain.interfaces.MyConstract;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.main.bean.UserBean;
import com.code.safechain.ui.my.bean.UploadIconRsBean;
import com.code.safechain.ui.transaction.bean.OrderRsBean;
import com.code.safechain.utils.RxUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @Auther: hchen
 * @Date: 2020/7/6 0006
 * @Description:
 */
public class MyPresenter extends BasePresenter<MyConstract.View> implements MyConstract.Presenter {
    private Disposable mDisposable;
    //上传头像
    @Override
    public void uploadHeader(String token, File file) {
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

    //更新用户信息：头像和昵称
    @Override
    public void updateUserData(String json) {

    }

    @Override
    public void getUserData(HashMap<String, Object> map) {
        HttpManager.getInstance().getApiServer().getUserData(map)
                .compose(RxUtils.<UserBean>changeScheduler())
                .subscribe(new Observer<UserBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        mView.getUserDataReturn(userBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        String ss = e.getMessage();
                        String s = "";
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        addSubscribe(mDisposable);
    }






}
