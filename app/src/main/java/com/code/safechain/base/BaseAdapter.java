package com.code.safechain.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter {
    protected Context mContext;
    public List<T> mDatas;
    //条目点击监听
    public OnItemClickListener onItemClickListener;
    //条目组件的点击事件的监听器，在子适配器中配置数据时使用,在子类中使用，所以用受保护的
    protected View.OnClickListener onClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public BaseAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(getLayout(),parent,false);
        final BaseViewHolder holder = new BaseViewHolder(view);
        //条目的点击事件在此添加，onBindViewHolder比此方法更提前执行，在此更合适
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseViewHolder h = (BaseViewHolder)holder;
        T t = mDatas.get(position);
        bindData(h,t);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    //获取条目布局
    public abstract int getLayout();
    //把数据绑定到条目组件上
    public abstract void bindData(BaseViewHolder holder,T data);
    //刷新数据  先清空数据，在添加新的
    public void updataListClearAddMore(List<T> list){
        mDatas.clear();
        mDatas.addAll(list);
        notifyDataSetChanged();
    }
    //累加数据  不清空，把更多的数据追加到集合中
    public void updataListAddMore(List<T> list){
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder{
        SparseArray views ;//通过容器收集组件,用消耗内存换每次的findviewbyid
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            views = new SparseArray();
        }

        public View getViewById(int id){
            View view = (View) views.get(id);
            if(view == null){
                view = itemView.findViewById(id);
                views.append(id,view);
            }
            return view;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(BaseViewHolder holder);
    }


}
