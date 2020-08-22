package com.code.safechain.interfaces;

import com.code.safechain.interfaces.IBasePresenter;
import com.code.safechain.interfaces.IBaseView;
import com.code.safechain.ui.main.bean.UserBean;
import com.code.safechain.ui.my.bean.GestureRsBean;
import com.code.safechain.ui.my.bean.UploadIconRsBean;

import java.io.File;
import java.util.HashMap;

import okhttp3.ResponseBody;

/**
 * @Auther: hchen
 * @Date: 2020/7/6 0006
 * @Description:我的 契约接口
 */
public interface MyConstract {

    interface View extends IBaseView{
        void uploadHeaderReturn(UploadIconRsBean uploadIconRsBean);//上传头像的回调   缺少回传值
        void updateUserDataReturn(GestureRsBean gestureRsBean);//修改用户信息 回传
        void getUserDataReturn(UserBean userBean);
    }

    interface Presenter extends IBasePresenter<View>{
        void uploadHeader(String token, File file);//上传头像，可以扩展为实名认证上传身份证正反面
        void updateUserData(String json);//修改用户信息
        void getUserData(HashMap<String, Object> map);
    }
}
