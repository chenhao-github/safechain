package com.code.safechain.interfaces;

/**
 * 定义P层基类接口
 * 满足做View层以接口的形式关联
 * @param <T>
 */
public interface IBasePresenter<T extends IBaseView> {

    //关联View层到P层
    void attachView(T view);
    //解绑View层的关联
    void dettachView();

}
