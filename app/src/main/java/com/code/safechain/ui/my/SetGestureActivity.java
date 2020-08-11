package com.code.safechain.ui.my;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.code.safechain.R;
import com.code.safechain.base.BaseActivity;
import com.code.safechain.common.Constants;
import com.code.safechain.interfaces.GestureConstract;
import com.code.safechain.presenter.GesturePresenter;
import com.code.safechain.ui.my.bean.GestureRsBean;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetGestureActivity extends BaseActivity<GestureConstract.Presenter> implements GestureConstract.View {
    private GestureView mGestureview;
    private ArrayList<Integer> mList;
    private String mPaywd="";

    @Override
    protected int getLayout() {
        return R.layout.activity_set_gesture;
    }

    @Override
    protected GestureConstract.Presenter createPresenter() {
        return new GesturePresenter();
    }

    protected void initView() {
        mList = new ArrayList<Integer>();
        //得到组件
        mGestureview = (GestureView) findViewById(R.id.gestureview);
        //添加监听
        mGestureview.setListener(new GestureView.GestureListener() {
            @Override
            public void onStart() {
//                tvError.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onDraw(int index) {

            }

            @Override
            public void onFinish(List<Integer> list) {
                if (mList.isEmpty()) {
                    mList.addAll(list);
                    Log.i("111", "onFinish: "+mList);
//                    tvGuide.setText(R.string.gesture_set_again);
                    ToastUtil.showLong("请再次绘制相同解锁图案");
                } else {
                    if (isSame(list)) {
                        ToastUtil.showLong("设置成功");
                        updatePaywd(list);//修改手势秘密
//                        setResult(RESULT_OK);
//                        finish();
                    } else {
//                        gestureView.showError();
//                        tvError.setVisibility(View.VISIBLE);
//                        tvError.setText("两次绘制图案不一致 请重新绘制");
                        ToastUtil.showLong("两次绘制图案不一致 请重新绘制");
                    }
                }
                mGestureview.reset();
            }

            @Override
            public void onError() {
//                tvError.setVisibility(View.VISIBLE);
//                tvError.setText("手势密码最少连续四个点");
                ToastUtil.showLong("手势密码最少连续四个点");
            }
        });

    }

    private void updatePaywd(List<Integer> list) {
        //封装数据到Map
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        map.put("paywd", mPaywd);
        //加密
        String json = SystemUtils.getJson(map);
        presenter.sendPaywd(json);
    }

    @Override
    public void sendPaywdReturn(GestureRsBean gestureRsBean) {
        //保存手势秘密到本地
        SpUtils.getInstance(this).setValue(Constants.PAYWD,mPaywd);
        setResult(RESULT_OK);
        finish();//设置成功后关闭页面

    }

    @Override
    protected void initData() {

    }

    /**
     * 判断两次绘制是否一致
     */
    private boolean isSame(List<Integer> list) {
        if (mList.size() != list.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            mPaywd += mList.get(i);//把集合转换为字符串
            if (!mList.get(i).equals(list.get(i))) {
                return false;
            }
        }
        return true;
    }
}
