package com.code.safechain.interfaces;

import com.code.safechain.interfaces.IBasePresenter;
import com.code.safechain.interfaces.IBaseView;

/**
 * @Auther: hchen
 * @Date: 2020/7/6 0006
 * @Description:我的 契约接口
 */
public interface MyConstract {

    interface View extends IBaseView{
        void uploadHeaderReturn();//上传头像的回调   缺少回传值
    }

    interface Presenter extends IBasePresenter<View>{
        void uploadHeader(String filePath);//上传头像，可以扩展为实名认证上传身份证正反面
    }
}
