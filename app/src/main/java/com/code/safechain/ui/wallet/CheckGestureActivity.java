package com.code.safechain.ui.wallet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.code.safechain.R;
import com.code.safechain.common.Constants;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.my.GestureView;
import com.code.safechain.ui.my.bean.GestureRsBean;
import com.code.safechain.ui.wallet.bean.ChainInfoRsBean;
import com.code.safechain.utils.RxUtils;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.code.safechain.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class CheckGestureActivity extends AppCompatActivity {
    private GestureView mGestureview;
    private String mCurrentPaywd;

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
                mCurrentPaywd = "";
                for (int i = 0; i < list.size(); i++) {
                    mCurrentPaywd += list.get(i);
                }
                mGestureview.reset();
                //验证手势密码
                checkGesture(mCurrentPaywd);
            }

            @Override
            public void onError() {
//                tvError.setVisibility(View.VISIBLE);
//                tvError.setText("手势密码最少连续四个点");
                ToastUtil.showLong("手势密码最少连续四个点");
            }
        });
    }

    //通过 回传到ChainDetailActivity，再跳转到 TransferActivity页面，TransferActivity操作完后
    // 再回传到回传到ChainDetailActivity 要区分
    //不通过，继续设置
    private void checkGesture(String currentPaywd) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type",5);//手势密码验证
        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        map.put("paywd",currentPaywd);//手势密码验证

        map = SystemUtils.getMap(map);
        HttpManager.getInstance().getApiServer().checkPaywd(map)
                .compose(RxUtils.<GestureRsBean>changeScheduler())
                .subscribe(new Observer<GestureRsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GestureRsBean gestureRsBean) {
                        dealRs(gestureRsBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //处理验证结果
    private void dealRs(GestureRsBean gestureRsBean) {
        if(gestureRsBean.getError() == 0){//验证成功，回传到ChainDetailActivity
            Intent intent = new Intent();
            intent.putExtra(Constants.PAYWD,mCurrentPaywd);
            setResult(200,intent);
            finish();
        }else if(gestureRsBean.getError() == -209){//没有支付密码
            Intent intent = new Intent();
            setResult(300,intent);
            finish();
        } else if(gestureRsBean.getError() == -208) {//支付密码不正确
            ToastUtil.showShort(gestureRsBean.getMessage());
        }
    }
}
