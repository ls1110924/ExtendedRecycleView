package com.yunxian.extended.recycleview.assit;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * {@link RecyclerView}使用的布局管理器来管理可见项的助手工具类
 *
 * @author A Shuai
 * @email lishuai.ls@alibaba-inc.com
 * @date 2016/9/26 13:36
 */
public final class LayoutManagerVisibleItemAssit {

    private LayoutManagerEnum mLayoutManagerEnum = LayoutManagerEnum.NONE;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    public LayoutManagerVisibleItemAssit() {
    }

    /**
     * 设置具体的布局管理器
     *
     * @param layout 布局管理器
     */
    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        if (layout == null) {
            mLayoutManagerEnum = LayoutManagerEnum.NONE;
        } else if (layout instanceof GridLayoutManager) {
            mLayoutManagerEnum = LayoutManagerEnum.LINEAR;
        } else if (layout instanceof LinearLayoutManager) {
            mLayoutManagerEnum = LayoutManagerEnum.GRID;
        } else if (layout instanceof StaggeredGridLayoutManager) {
            mLayoutManagerEnum = LayoutManagerEnum.STAGGERED;
        } else {
            mLayoutManagerEnum = LayoutManagerEnum.NONE;
        }
    }

    /**
     * 支持的布局管理器枚举
     */
    private enum LayoutManagerEnum {

        LINEAR, // 线性管理器
        GRID,   // 网格布局管理器
        STAGGERED,    // 瀑布流布局管理器
        NONE;   // 未知或没有布局管理器

    }

    /**
     * 找出第一个可见项的索引
     *
     * @return item的索引，-1表示未找到
     */
    private int findFirstVisibleItemPosition() {
        switch (mLayoutManagerEnum) {
            case LINEAR:
                return mLinearLayoutManager.findFirstVisibleItemPosition();
            case GRID:
                return mGridLayoutManager.findFirstVisibleItemPosition();
            case STAGGERED: {
                int[] visibleArray = mStaggeredGridLayoutManager.findFirstVisibleItemPositions(null);
                if (visibleArray == null || visibleArray.length == 0) {
                    return -1;
                } else {
                    return visibleArray[0];
                }
            }
            case NONE:
            default:
                return -1;
        }
    }

    /**
     * 找出第一个完整可见项对应的索引
     *
     * @return item的索引，-1为未找到
     */
    private int findFirstCompletelyVisibleItemPosition() {
        switch (mLayoutManagerEnum) {
            case LINEAR:
                return mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
            case GRID:
                return mGridLayoutManager.findFirstCompletelyVisibleItemPosition();
            case STAGGERED: {
                int[] visibleArray = mStaggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(null);
                if (visibleArray == null || visibleArray.length == 0) {
                    return -1;
                } else {
                    int minIndex = visibleArray[0];
                    for (int i = 1; i < visibleArray.length; i++) {
                        if (minIndex > visibleArray[i]) {
                            minIndex = visibleArray[i];
                        }
                    }
                    return minIndex;
                }
            }
            case NONE:
            default:
                return -1;
        }
    }

    /**
     * 找出最后一个完整可见项对应的索引
     *
     * @return item的索引，-1表示未找到
     */
    private int findLastCompletelyVisibleItemPosition() {
        switch (mLayoutManagerEnum) {
            case LINEAR:
                return mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
            case GRID:
                return mGridLayoutManager.findLastCompletelyVisibleItemPosition();
            case STAGGERED: {
                int[] visibleArray = mStaggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(null);
                if (visibleArray == null || visibleArray.length == 0) {
                    return -1;
                } else {
                    int maxIndex = visibleArray[0];
                    for (int i = 1; i < visibleArray.length; i++) {
                        if (maxIndex < visibleArray[i]) {
                            maxIndex = visibleArray[i];
                        }
                    }
                    return maxIndex;
                }
            }
            case NONE:
            default:
                return -1;
        }
    }

    /**
     * 找出最后一个可见项对应的索引
     *
     * @return item的索引，-1表示未找到
     */
    private int findLastVisibleItemPosition() {
        switch (mLayoutManagerEnum) {
            case LINEAR:
                return mLinearLayoutManager.findLastVisibleItemPosition();
            case GRID:
                return mGridLayoutManager.findLastVisibleItemPosition();
            case STAGGERED: {
                int[] visibleArray = mStaggeredGridLayoutManager.findLastVisibleItemPositions(null);
                if (visibleArray == null || visibleArray.length == 0) {
                    return -1;
                } else {
                    return visibleArray[0];
                }
            }
            case NONE:
            default:
                return -1;
        }
    }

}
