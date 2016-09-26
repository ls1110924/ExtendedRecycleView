package com.yunxian.extended.recycleview.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * 给使用LinearLayoutManager布局的RecycleView绘制分割线的装饰器
 *
 * @author A Shuai
 * @email ls1110924@163.com
 * @date 2016-08-01 09:28
 */
public class LinearDividerItemDecoration extends RecyclerView.ItemDecoration {

    private final Drawable mDividerDrawable;
    private final Rect mDividerDrawablePadding = new Rect();
    private int mOrientation;

    /**
     * 构造一个垂直布局方向的LinearLayoutManager的RecycleView使用的分割线装饰器
     *
     * @param mDividerDrawable 分割线Drawable对象
     */
    public LinearDividerItemDecoration(Drawable mDividerDrawable) {
        this(mDividerDrawable, VERTICAL);
    }

    /**
     * 构造一个指定方向的RecycleView使用的分割线装饰器
     *
     * @param mDividerDrawable 分割线Drawable对象
     * @param mOrientation     RecycleView在LinearLayoutManager下的布局方向，只能是
     *                         {@link LinearLayoutManager#HORIZONTAL}或{@link LinearLayoutManager#VERTICAL}。
     */
    public LinearDividerItemDecoration(Drawable mDividerDrawable, int mOrientation) {
        super();
        this.mDividerDrawable = mDividerDrawable;
        this.mDividerDrawable.getPadding(mDividerDrawablePadding);
        this.mOrientation = mOrientation;
        checkLayoutOrientation();
    }

    private void checkLayoutOrientation() {
        if (mOrientation != HORIZONTAL && mOrientation != VERTICAL) {
            throw new IllegalArgumentException("invalid orientation:" + mOrientation);
        }
    }

    /**
     * 设置RecycleView在LinearLayoutManager下的布局方向
     *
     * @param mOrientation RecycleView在LinearLayoutManager下的布局方向，只能是
     *                     {@link LinearLayoutManager#HORIZONTAL}或{@link LinearLayoutManager#VERTICAL}。
     * @throws IllegalArgumentException
     */
    public void setOrientationI(int mOrientation) {
        checkLayoutOrientation();
        this.mOrientation = mOrientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        switch (mOrientation) {
            case VERTICAL:
                drawVerticalDivider(c, parent);
                break;
            case HORIZONTAL:
                drawHorizontalDivider(c, parent);
                break;
            default:
                throw new IllegalArgumentException("invalid orientation:" + mOrientation);
        }
    }

    private void drawVerticalDivider(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + mDividerDrawablePadding.left;
        final int right = parent.getWidth() - parent.getPaddingRight() - mDividerDrawablePadding.right;

        int mChildCount = parent.getChildCount();

        //最后一个子视图的下面不再画分割线
        for (int i = 0, size = mChildCount - 1; i < size; i++) {
            View mChildView = parent.getChildAt(i);
            RecyclerView.LayoutParams mChildParams = (RecyclerView.LayoutParams) mChildView.getLayoutParams();
            int top = mChildView.getBottom() + mChildParams.bottomMargin + mDividerDrawablePadding.top;
            int bottom = top + mDividerDrawable.getIntrinsicHeight();
            mDividerDrawable.setBounds(left, top, right, bottom);
            mDividerDrawable.draw(c);
        }
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop() + mDividerDrawablePadding.top;
        int bottom = parent.getHeight() - parent.getPaddingBottom() - mDividerDrawablePadding.bottom;

        int mChildCount = parent.getChildCount();

        for (int i = 0, size = mChildCount - 1; i < size; i++) {
            View mChildView = parent.getChildAt(i);
            RecyclerView.LayoutParams mChildParams = (RecyclerView.LayoutParams) mChildView.getLayoutParams();

            int left = mChildView.getRight() + mChildParams.rightMargin + mDividerDrawablePadding.left;
            int right = left + mDividerDrawable.getIntrinsicWidth();
            mDividerDrawable.setBounds(left, top, right, bottom);
            mDividerDrawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        switch (mOrientation) {
            case VERTICAL:
                outRect.set(0, 0, 0, mDividerDrawablePadding.top + mDividerDrawable.getIntrinsicHeight() + mDividerDrawablePadding.bottom);
                break;
            case HORIZONTAL:
                outRect.set(0, 0, mDividerDrawablePadding.left + mDividerDrawable.getIntrinsicWidth() + mDividerDrawablePadding.right, 0);
                break;
            default:
                throw new IllegalArgumentException("invalid orientation:" + mOrientation);
        }
    }
}
