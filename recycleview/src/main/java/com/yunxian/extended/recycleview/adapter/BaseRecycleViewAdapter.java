package com.yunxian.extended.recycleview.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.yunxian.extended.recycleview.R;

/**
 * RecycleView的基础适配器
 *
 * @author A Shuai
 * @email lishuai.ls@alibaba-inc.com
 * @date 2016-07-31 13:35
 */
public abstract class BaseRecycleViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private static final int TAG_KEY = R.id.id_recycleview_item_vh_tagkey;

    protected final Context mContext;
    protected final Resources mResources;
    protected final LayoutInflater mInflater;

    protected OnItemClickListener<VH> mOnItemClickListener;

    private final CommonClickListener mCommonListener = new CommonClickListener();

    public BaseRecycleViewAdapter(@NonNull Context mContext) {
        this.mContext = mContext;
        mResources = this.mContext.getResources();
        mInflater = LayoutInflater.from(this.mContext);
    }

    /**
     * 本方法不再提供覆写。子类需覆写{@link #onBindViewHolderWithContent(RecyclerView.ViewHolder, int)}
     * 方法来绑定填充内容。
     *
     * @param holder   item所绑定的ViewHolder对象
     * @param position item所在的位置
     */
    @Override
    public final void onBindViewHolder(VH holder, int position) {
        onBindViewHolderWithContent(holder, position);

        //绑定监听器
        if (mOnItemClickListener != null) {
            holder.itemView.setTag(TAG_KEY, holder);
            holder.itemView.setOnClickListener(mCommonListener);
            holder.itemView.setOnLongClickListener(mCommonListener);
        }
    }

    /**
     * 子类需覆写本方法来填充视图内容
     *
     * @param holder   item所绑定的ViewHolder对象
     * @param position item所在的位置
     */
    public abstract void onBindViewHolderWithContent(VH holder, int position);

    /**
     * 设置回调监听器
     *
     * @param mOnItemClickListener 监听器对象
     */
    public void setOnItemClickListener(OnItemClickListener<VH> mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * 当RecycleView的一个item被点击时调用的回调接口
     *
     * @param <VH> ViewHolder类型参数
     */
    public interface OnItemClickListener<VH extends RecyclerView.ViewHolder> {

        /**
         * 当RecycleView中的一个子视图被点击时触发的回调方法
         *
         * @param parent   父视图
         * @param view     被点击的子视图
         * @param holder   ViewHolder对象
         * @param position 被点击的子视图索引
         */
        void onItemClick(RecyclerView parent, View view, VH holder, int position);

        /**
         * 当RecycleView中的一个子视图被长按时触发的回调方法
         *
         * @param parent   父视图
         * @param view     被点击的子视图
         * @param holder   ViewHolder对象
         * @param position 被点击的子视图索引
         * @return true表示监听器消费了该事件，否则为false
         */
        boolean onItemLongClick(RecyclerView parent, View view, VH holder, int position);

    }

    private class CommonClickListener implements View.OnClickListener, View.OnLongClickListener {

        @SuppressWarnings("unchecked")
        @Override
        public void onClick(View v) {
            VH holder = (VH) v.getTag(TAG_KEY);
            if (mOnItemClickListener != null) {
                RecyclerView mParent = (RecyclerView) holder.itemView.getParent();
                mOnItemClickListener.onItemClick(mParent, holder.itemView, holder, holder.getLayoutPosition());
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean onLongClick(View v) {
            VH holder = (VH) v.getTag(TAG_KEY);
            if (mOnItemClickListener != null) {
                RecyclerView mParent = (RecyclerView) holder.itemView.getParent();
                return mOnItemClickListener.onItemLongClick(mParent, holder.itemView, holder, holder.getLayoutPosition());
            }
            return false;
        }
    }

}
