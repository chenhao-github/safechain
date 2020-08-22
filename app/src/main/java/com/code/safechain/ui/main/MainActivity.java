package com.code.safechain.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.code.safechain.R;
import com.code.safechain.app.BaseApp;
import com.code.safechain.common.Constants;
import com.code.safechain.model.HttpManager;
import com.code.safechain.ui.consult.ConsultFragment;
import com.code.safechain.ui.ecotope.EcotopeFragment;
import com.code.safechain.ui.main.bean.UserBean;
import com.code.safechain.ui.my.MyFragment;
import com.code.safechain.ui.transaction.TransactionFragment;
import com.code.safechain.ui.wallet.WalletFragment;
import com.code.safechain.ui.wallet.bean.ChainInfoRsBean;
import com.code.safechain.utils.LocalManageUtil;
import com.code.safechain.utils.LoggerUtil;
import com.code.safechain.utils.RxUtils;
import com.code.safechain.utils.Sha1;
import com.code.safechain.utils.SpUtils;
import com.code.safechain.utils.SystemUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.security.DigestException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tab)
    TabLayout mTab;
    //记录当前显示的Fragment的索引/类型
    public int mLastFragmentPosition ;
    private ArrayList<Fragment> fragments;
    private FragmentManager mFm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFm = getSupportFragmentManager();
        initTab();
        initFragment();
        switchFragment(0);//刚进入系统默认显示 钱包
        requestPermiss();//处理动态权限
        getUserInfo();
    }

    private void initTab() {
        //添加tab页
        mTab.addTab(mTab.newTab()
                .setText(getResources().getString(R.string.title_wallet))
                .setIcon(getResources().getDrawable(R.drawable.ic_wallet_black)));
        mTab.addTab(mTab.newTab()
                .setText(getResources().getString(R.string.title_transaction))
                .setIcon(getResources().getDrawable(R.drawable.ic_transaction_black)));
        mTab.addTab(mTab.newTab()
                .setText(getResources().getString(R.string.title_ecotope))
                .setIcon(getResources().getDrawable(R.drawable.ic_ecotope_black)));
        mTab.addTab(mTab.newTab()
                .setText(getResources().getString(R.string.title_consult))
                .setIcon(getResources().getDrawable(R.drawable.ic_consult_black)));
        mTab.addTab(mTab.newTab()
                .setText(getResources().getString(R.string.title_my))
                .setIcon(getResources().getDrawable(R.drawable.ic_my_black)));
        //tab添加tab切换监听器
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchFragment(tab.getPosition());//点击tab页，切换fragment
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        //添加fragment对象
        fragments.add(new WalletFragment());
        fragments.add(new TransactionFragment());
        fragments.add(new EcotopeFragment());
        fragments.add(new ConsultFragment());
        fragments.add(new MyFragment());
    }
    /**
     * 切换Fragment
     * 显示一个，隐藏上一个
     * @param type
     */
    private void switchFragment(int type) {
        FragmentTransaction tr = mFm.beginTransaction();
        Fragment showFragment = fragments.get(type);
        Fragment hideFragment = fragments.get(mLastFragmentPosition);
        if (!showFragment.isAdded()){
            //一个Fragment只能添加一次，否则会崩
            tr.add(R.id.fragment_contain,showFragment);
        }
        //隐藏上一个
        //这次显示的fragment，就是下一次要隐藏的Fragment
        tr.hide(hideFragment).show(showFragment).commit();
        //记录当前的Fragment索引，方便下次隐藏
        mLastFragmentPosition = type;
    }

    //申请权限
    private void requestPermiss() {
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> permissionsList = new ArrayList<>();
            String[] permissions = {
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            };

            for (String perm : permissions) {
                if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(perm)) {
                    permissionsList.add(perm);// 进入到这里代表没有权限.
                }
            }
            if (permissionsList.isEmpty()) {
                return;
            } else {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 0);
            }
        }
    }

    private void getUserInfo() {
        //封装数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", SpUtils.getInstance(this).getString(Constants.TOKEN));
        map = SystemUtils.getMap(map);
        HttpManager.getInstance().getApiServer().getUserData(map)
                .compose(RxUtils.<UserBean>changeScheduler())
                .subscribe(new Observer<UserBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        BaseApp.userBean = userBean;//保存用户信息
                    }

                    @Override
                    public void onError(Throwable e) {
//                        String s = e.getMessage();
//                        String a = "";
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    /**
     * 设置系统语言
     *
     * @param newBase
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalManageUtil.setLocal(newBase));
    }



//    protected void onSaveInstanceState(Bundle outState) {
//        //在FragmentActivity保存所有Fragment状态前把Fragment从FragmentManager中移除掉。
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        for (int i = 0; i < fragments.size(); i++) {
//            transaction.remove(fragments.get(i));
//        }
//        transaction.commitAllowingStateLoss();
//        super.onSaveInstanceState(outState);
//    }

}
