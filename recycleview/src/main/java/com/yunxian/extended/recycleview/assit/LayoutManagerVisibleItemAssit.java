package com.yunxian.extended.recycleview.assit;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * {@link RecyclerView}使用的布局管理器来管理可见项的助手工具类
 * <p>
 * 若item的索引值为-1，表示未找到，无效值
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

    // 这些索引值只可做只读操作，不可做写入操作
    public int firstVisibleIndex;
    public int firstCompletelyVisibleItem;
    public int lastCompletelyVisibleItem;
    public int lastVisibleIndex;

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
            mGridLayoutManager = null;
            mLinearLayoutManager = null;
            mStaggeredGridLayoutManager = null;
        } else if (layout instanceof GridLayoutManager) {
            mLayoutManagerEnum = LayoutManagerEnum.GRID;
            mGridLayoutManager = (GridLayoutManager) layout;
        } else if (layout instanceof LinearLayoutManager) {
            mLayoutManagerEnum = LayoutManagerEnum.LINEAR;
            mLinearLayoutManager = (LinearLayoutManager) layout;
        } else if (layout instanceof StaggeredGridLayoutManager) {
            mLayoutManagerEnum = LayoutManagerEnum.STAGGERED;
            mStaggeredGridLayoutManager = (StaggeredGridLayoutManager) layout;
        } else {
            mLayoutManagerEnum = LayoutManagerEnum.NONE;
            mGridLayoutManager = null;
            mLinearLayoutManager = null;
            mStaggeredGridLayoutManager = null;
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
     * 更新对应布局管理器中Item的可见项索引
     *
     * @return true表示发生了变化，否则表示未发生变化
     */
    public boolean updateVisibleItemPosition() {
        boolean change = false;

        int newIndex = findFirstVisibleItemPosition();
        if (newIndex != -1 && newIndex != firstVisibleIndex) {
            firstVisibleIndex = newIndex;
            change = true;
        }

        newIndex = findFirstCompletelyVisibleItemPosition();
        if (newIndex != -1 && newIndex != firstCompletelyVisibleItem) {
            firstCompletelyVisibleItem = newIndex;
            change = true;
        }

        newIndex = findLastCompletelyVisibleItemPosition();
        if (newIndex != -1 && newIndex != lastCompletelyVisibleItem) {
            lastCompletelyVisibleItem = newIndex;
            change = true;
        }

        newIndex = findLastVisibleItemPosition();
        if (newIndex != -1 && newIndex != lastVisibleIndex) {
            lastVisibleIndex = newIndex;
            change = true;
        }

        return change;
    }

    /**
     * 重置索引计数器
     */
    public void reset() {
        firstVisibleIndex = firstCompletelyVisibleItem = lastCompletelyVisibleItem = lastVisibleIndex = -1;
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
