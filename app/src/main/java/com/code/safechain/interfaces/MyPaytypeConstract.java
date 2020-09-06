package com.code.safechain.interfaces;

import com.code.safechain.ui.my.bean.UploadIconRsBean;
import com.code.safechain.ui.transaction.bean.SetPayTypeRsBean;

import java.io.File;

/**
 * @Auther: hchen
 * @Date: 2020/7/6 0006
 * @Description:我的 契约接口
 */
public interface MyPaytypeConstract {

    interface View extends IBaseView{
        void addPaytypeReturn(SetPayTypeRsBean payTypeRsBean);//上传支付方式 回传
        void uploadWechatIconReturn(UploadIconRsBean uploadIconRsBean);//上传微信二维码 回传
        void uploadAlipayIconReturn(UploadIconRsBean uploadIconRsBean);//上传支付宝二维码 回传
    }

    interface Presenter extends IBasePresenter<View>{
        void addPaytype(String json);//上传支付方式
        void uploadWechatIcon(String token,File file);//上传微信二维码
        void uploadAlipayIcon(String token,File file);//上传支付宝二维码
    }
}
