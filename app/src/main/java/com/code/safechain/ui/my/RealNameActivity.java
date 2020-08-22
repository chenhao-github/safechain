package com.code.safechain.ui.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.MyConstract;
import com.code.safechain.interfaces.RealNameConstract;
import com.code.safechain.presenter.MyPresenter;
import com.code.safechain.presenter.RealNamePresenter;
import com.code.safechain.ui.my.bean.GestureRsBean;
import com.code.safechain.ui.my.bean.UploadIconRsBean;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RealNameActivity extends BaseActivity<RealNameConstract.Presenter>
        implements RealNameConstract.View {
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.et_realname)
    EditText mEtRealname;
    @BindView(R.id.et_identity)
    EditText mEtIdentity;
    @BindView(R.id.img_camera_front)
    ImageView mImgCameraFront;
    @BindView(R.id.img_camera_back)
    ImageView mImgCameraBack;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    private File mFile;

    String frontImg;
    String backImg;
    private String mToken;

    @Override
    protected int getLayout() {
        return R.layout.activity_real_name;
    }

    @Override
    protected RealNameConstract.Presenter createPresenter() {
        return new RealNamePresenter();
    }

    @Override
    protected void initView() {
        mToken = SpUtils.getInstance(this).getString(Constants.TOKEN);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.img_back, R.id.img_camera_front, R.id.img_camera_back, R.id.btn_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_camera_front:
                cameraUpload(100);//正面
                break;
            case R.id.img_camera_back:
                cameraUpload(200);//反面
                break;
            case R.id.btn_confirm:
                updateRealName();
                break;
        }
    }

    private void cameraUpload(int requestCode) {
        mFile = new File(Constants.PATH_DATA+"/"+System.currentTimeMillis()+".jpg");
        try {
            mFile.createNewFile();//创建此文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri;
        //版本适配  7.0以下 和 7.0以上的区别
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){//7.0以下
            uri = Uri.fromFile(mFile);
        }else {//7.0以上的区别，用内容提供者处理
            uri = FileProvider.getUriForFile(this,"com.code.safechain.provider",mFile);
        }
        //打开相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);//设置照片的保存位置
        startActivityForResult(intent,requestCode);
    }

    private void updateRealName() {
        String realName = mEtRealname.getText().toString();
        String identity = mEtIdentity.getText().toString();
        if(TextUtils.isEmpty(realName)){
            ToastUtil.showShort("姓名不能为空!");
            return;
        }
        if(!SystemUtils.isIdentity(identity)){
            ToastUtil.showShort("身份证不合法!");
            return;
        }
        if(TextUtils.isEmpty(frontImg)){
            ToastUtil.showShort("请上传身份证正面!");
            return;
        }
        if(TextUtils.isEmpty(backImg)){
            ToastUtil.showShort("请上传身份证反面!");
            return;
        }
        //提交
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", mToken);
        map.put("true_name", realName);
        map.put("card_id", identity);
        map.put("card_z", frontImg);
        map.put("card_b", backImg);
        //加密
        String json = SystemUtils.getJson(map);
        presenter.updateRealName(json);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {//正面的返回
            mImgCameraFront.setImageBitmap(BitmapFactory.decodeFile(mFile.getPath()));//使用图片
            presenter.uploadCardZIcon(mToken,mFile);
        }else if(requestCode == 200 && resultCode == RESULT_OK){//反面的返回
            mImgCameraBack.setImageBitmap(BitmapFactory.decodeFile(mFile.getPath()));//使用图片
            presenter.uploadCardBIcon(mToken,mFile);
        }
    }

    @Override
    public void updateRealNameReturn(GestureRsBean gestureRsBean) {
        if(gestureRsBean.getError() == 0){
            ToastUtil.showShort("认证成功！");
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void uploadCardZIconReturn(UploadIconRsBean uploadIconRsBean) {
        if(uploadIconRsBean.getError() == 0){
            frontImg = uploadIconRsBean.getResult().getImg_url();//获得正面
        }
    }

    @Override
    public void uploadCardBIconReturn(UploadIconRsBean uploadIconRsBean) {
        if(uploadIconRsBean.getError() == 0){
            backImg = uploadIconRsBean.getResult().getImg_url();//获得反面
        }
    }
}
