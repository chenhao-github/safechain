package com.code.safechain.ui.main;

import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.code.safechain.R;
import com.code.safechain.ui.consult.ConsultFragment;
import com.code.safechain.ui.ecotope.EcotopeFragment;
import com.code.safechain.ui.my.MyFragment;
import com.code.safechain.ui.transaction.TransactionFragment;
import com.code.safechain.ui.wallet.WalletFragment;
import com.code.safechain.utils.LocalManageUtil;
import com.code.safechain.utils.LoggerUtil;
import com.code.safechain.utils.Sha1;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.security.DigestException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    /**
     * 设置系统语言
     *
     * @param newBase
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalManageUtil.setLocal(newBase));
    }

    /**
     * 设置完语言后，调用此方法 重启app加载设置的语言
     */
    public void reStartApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Process.killProcess(Process.myPid());
    }

}
