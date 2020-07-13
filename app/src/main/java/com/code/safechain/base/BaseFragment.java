package com.code.safechain.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.code.safechain.interfaces.IBasePresenter;
import com.code.safechain.interfaces.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends IBasePresenter> extends Fragment implements IBaseView {
    protected P presenter; //关联P层类的实例引用
    protected Context context;  //当前页面的context
    protected Activity activity; //当前页面的Activity

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        activity = getActivity();
        //container,attachToRoot参数设置避免约束布局的时候布局显示不完成
        View view = LayoutInflater.from(context).inflate(getLayout(),container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this,view);
        presenter = createPresenter();
        if(presenter != null){
            presenter.attachView(this);
        }
        initView();
        initData();
    }

    //当前界面的布局
    protected abstract int getLayout();
    //创建P层的方法
    protected abstract P createPresenter();
    //初始化界面和数据
    protected abstract void initView();
    protected abstract void initData();

    @Override
    public void showTips(String msg) {
        Toast.makeText(context, msg , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter != null){
            presenter.dettachView();
        }
        if(unbinder != null){
            unbinder.unbind();
        }
    }
}
