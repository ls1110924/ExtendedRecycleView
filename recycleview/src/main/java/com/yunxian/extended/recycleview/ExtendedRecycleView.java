package com.yunxian.extended.recycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.yunxian.extended.recycleview.assit.LayoutManagerVisibleItemAssit;

/**
 * 扩展功能的RecycleView
 *
 * @author A Shuai
 * @email lishuai.ls@alibaba-inc.com
 * @date 2016/9/25 22:56
 */
public class ExtendedRecycleView extends RecyclerView {

    // 可见项助手工具类
    private final LayoutManagerVisibleItemAssit mVisibleItemAssit = new LayoutManagerVisibleItemAssit();

    private Adapter mAdapter;

    // Item可见性变化监听器
    private OnItemVisibilityListener mOnItemVisibilityListener;

    // 本类内部使用的公共监听器
    private final BaseCommonListener mCommonListener = new BaseCommonListener();

    // Adapter数据源观察者
    private final ExtendedAdapterDataObserver mObserver = new ExtendedAdapterDataObserver();

    public ExtendedRecycleView(Context context) {
        super(context);
    }

    public ExtendedRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendedRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        mVisibleItemAssit.setLayoutManager(layout);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        // 务必先调用父类的方法，保证父类的执行先于子类
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mObserver);
        }
        this.mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerAdapterDataObserver(mObserver);
        }
    }

    /**
     * 设置Item可见性监听器
     *
     * @param mOnItemVisibilityListener Item可见性监听器
     */
    public void setOnItemVisibilityListener(OnItemVisibilityListener mOnItemVisibilityListener) {
        if (mOnItemVisibilityListener == null) {
            removeOnScrollListener(mCommonListener);
        } else {
            addOnScrollListener(mCommonListener);
            mVisibleItemAssit.reset();
        }
        this.mOnItemVisibilityListener = mOnItemVisibilityListener;
    }

    /**
     * Item可见性发生变化时的监听器
     */
    public interface OnItemVisibilityListener {

        /**
         * 当Item的可见性发生变化时触发
         *
         * @param firstVisibleIndex          第一个可见项的索引
         * @param firstCompletelyVisibleItem 第一个完整可见项的索引
         * @param lastCompletelyVisibleItem  最后一个完整可见项的索引
         * @param lastVisibleIndex           最后一个可见项的索引
         */
        void onVisibilityChange(int firstVisibleIndex, int firstCompletelyVisibleItem,
                                int lastCompletelyVisibleItem, int lastVisibleIndex);

    }

    /**
     * 调用可见性变化监听器
     *
     * @param force 是否强制更新
     */
    private void callVisiblityChange(boolean force) {
        if (mOnItemVisibilityListener == null) {
            return;
        }

        // Item的可见性性是否发生变化
        boolean change = mVisibleItemAssit.updateVisibleItemPosition();

        // 如果强制更新或者发生了变化，则进行回调
        if (force || change) {
            mOnItemVisibilityListener.onVisibilityChange(mVisibleItemAssit.firstVisibleIndex,
                    mVisibleItemAssit.firstCompletelyVisibleItem, mVisibleItemAssit.lastCompletelyVisibleItem,
                    mVisibleItemAssit.lastVisibleIndex);
        }
    }

    private class BaseCommonListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            callVisiblityChange(false);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            callVisiblityChange(false);
        }

    }

    private class ExtendedAdapterDataObserver extends AdapterDataObserver {

        @Override
        public void onChanged() {
            super.onChanged();

            callVisiblityChange(true);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);

            callVisiblityChange(true);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);

            callVisiblityChange(true);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);

            callVisiblityChange(true);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);

            callVisiblityChange(true);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);

            callVisiblityChange(true);
        }
    }

}
