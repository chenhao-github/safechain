package com.code.safechain.base;
import com.code.safechain.interfaces.IBasePresenter;
import com.code.safechain.interfaces.IBaseView;
import java.lang.ref.WeakReference;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 抽取P层的基类
 * @param <V>
 */
public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    protected V mView; //当前关联进来的V层的类
    WeakReference<V> weakReference; //采用弱引用关联View
    //rxjava2被压式处理内存
    CompositeDisposable compositeDisposable;
    //关联View
    @Override
    public void attachView(V view) {
        weakReference = new WeakReference(view);
        mView = weakReference.get();
    }

    //取消View关联
    @Override
    public void dettachView() {
        mView = null;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    /**
     * 添加请求数据的对象到被压式管理compositeDisposable
     * @param disposable
     */
    protected void addSubscribe(Disposable disposable){
        if(compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
}
