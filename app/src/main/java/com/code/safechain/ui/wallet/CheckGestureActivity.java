package com.code.safechain.ui.wallet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.code.safechain.R;
import com.code.safechain.common.Constants;
import com.code.safechain.ui.my.GestureView;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.ToastUtil;

import java.util.List;

public class CheckGestureActivity extends AppCompatActivity {
    private GestureView mGestureview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_gesture);

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
                String currentPaywd = "";
                for (int i = 0; i < list.size(); i++) {
                    currentPaywd += list.get(i);
                }
                //得到手势密码
                String paywd = SpUtils.getInstance(CheckGestureActivity.this).getString(Constants.PAYWD);
                if(paywd.equals(currentPaywd)){
                    ToastUtil.showShort("验证成功");
                    setResult(RESULT_OK);
                    finish();
                }else {
                    ToastUtil.showShort("不正确，请重试！");
                }
//                if (mList.isEmpty()) {
//                    mList.addAll(list);
//                    Log.i("111", "onFinish: "+mList);
////                    tvGuide.setText(R.string.gesture_set_again);
//                    ToastUtil.showLong("请再次绘制相同解锁图案");
//                } else {
//                    if (isSame(list)) {
//                        ToastUtil.showLong("设置成功");
//                        updatePaywd(list);//修改手势秘密
////                        setResult(RESULT_OK);
////                        finish();
//                    } else {
////                        gestureView.showError();
////                        tvError.setVisibility(View.VISIBLE);
////                        tvError.setText("两次绘制图案不一致 请重新绘制");
//                        ToastUtil.showLong("两次绘制图案不一致 请重新绘制");
//                    }
//                }
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
}
