package com.code.safechain.interfaces;

import com.code.safechain.ui.my.bean.GestureRsBean;
import com.code.safechain.ui.my.bean.UploadIconRsBean;

import java.io.File;

/**
 * @Auther: hchen
 * @Date: 2020/7/6 0006
 * @Description:我的 契约接口
 */
public interface RealNameConstract {

    interface View extends IBaseView{
        void updateRealNameReturn(GestureRsBean gestureRsBean);//实名认证 回传
        void uploadCardZIconReturn(UploadIconRsBean uploadIconRsBean);//上传身份证正面 回传
        void uploadCardBIconReturn(UploadIconRsBean uploadIconRsBean);//上传身份证背面 回传
    }

    interface Presenter extends IBasePresenter<View>{
        void updateRealName(String json);//上传支付方式
        void uploadCardZIcon(String token, File file);//上传身份证正面
        void uploadCardBIcon(String token, File file);//上传身份证背面
    }
}
