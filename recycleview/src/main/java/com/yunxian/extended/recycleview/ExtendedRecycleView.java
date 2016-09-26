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

    // Item可见性变化监听器
    private OnItemVisibilityListener mOnItemVisibilityListener;

    // 本类内部使用的公共监听器
    private final BaseCommonListener mCommonListener = new BaseCommonListener();

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

    private class BaseCommonListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (mVisibleItemAssit.updateVisibleItemPosition()) {
                if (mOnItemVisibilityListener != null) {
                    mOnItemVisibilityListener.onVisibilityChange(mVisibleItemAssit.firstVisibleIndex,
                            mVisibleItemAssit.firstCompletelyVisibleItem, mVisibleItemAssit.lastCompletelyVisibleItem,
                            mVisibleItemAssit.lastVisibleIndex);
                }
            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (mVisibleItemAssit.updateVisibleItemPosition()) {
                if (mOnItemVisibilityListener != null) {
                    mOnItemVisibilityListener.onVisibilityChange(mVisibleItemAssit.firstVisibleIndex,
                            mVisibleItemAssit.firstCompletelyVisibleItem, mVisibleItemAssit.lastCompletelyVisibleItem,
                            mVisibleItemAssit.lastVisibleIndex);
                }
            }
        }

    }

}
