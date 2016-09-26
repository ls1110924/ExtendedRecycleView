package com.yunxian.extended.recycleview.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * 给使用LinearLayoutManager布局的RecycleView绘制外留白以及分割线的装饰器。主要实现以下两点
 * <ol><li>其中为ItemView添加外留白</li><li>为ItemView添加对应方向的分割线</li></ol>
 *
 * @author A Shuai
 * @email ls1110924@163.com
 * @date 2016/9/25 20:11
 */
public class LinearMarginDividerItemDecoration extends RecyclerView.ItemDecoration {

    private final Rect mMarginPadding = new Rect();

    private final Drawable mDividerDrawable;
    private final Rect mDividerPadding = new Rect();
    private int mOrientation;

    public LinearMarginDividerItemDecoration(@Nullable Drawable mMarginDrawable,
                                             @Nullable Drawable mDividerDrawable) {
        this(mMarginDrawable, mDividerDrawable, VERTICAL);
    }

    public LinearMarginDividerItemDecoration(@Nullable Drawable mMarginDrawable,
                                             @Nullable Drawable mDividerDrawable, int mOrientation) {
        if (mMarginDrawable != null) {
            mMarginDrawable.getPadding(mMarginPadding);
        }

        this.mDividerDrawable = mDividerDrawable;
        if (mDividerDrawable != null) {
            mDividerDrawable.getPadding(mDividerPadding);
        }

        this.mOrientation = mOrientation;
        checkLayoutOrientation();
    }

    public LinearMarginDividerItemDecoration(@Nullable Rect mMarginPadding,
                                             @Nullable Drawable mDividerDrawable) {
        this(mMarginPadding, mDividerDrawable, VERTICAL);
    }

    public LinearMarginDividerItemDecoration(@Nullable Rect mMarginPadding,
                                             @Nullable Drawable mDividerDrawable, int mOrientation) {
        if (mMarginPadding != null) {
            this.mMarginPadding.set(mMarginPadding);
        }

        this.mDividerDrawable = mDividerDrawable;
        if (mDividerDrawable != null) {
            mDividerDrawable.getPadding(mDividerPadding);
        }

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
        this.mOrientation = mOrientation;
        checkLayoutOrientation();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if (mDividerDrawable == null) {
            return;
        }

        switch (mOrientation) {
            case VERTICAL:
                drawVerticalDivider(c, parent, mDividerDrawable);
                break;
            case HORIZONTAL:
                drawHorizontalDivider(c, parent, mDividerDrawable);
                break;
            default:
                throw new IllegalArgumentException("invalid orientation:" + mOrientation);
        }
    }

    private void drawVerticalDivider(Canvas c, RecyclerView parent, @NonNull Drawable mDividerDrawable) {
        final int left = mMarginPadding.left + parent.getPaddingLeft() + mDividerPadding.left;
        final int right = parent.getWidth() - mMarginPadding.right - parent.getPaddingRight() - mDividerPadding.right;

        int mChildCount = parent.getChildCount();

        //最后一个子视图的下面不再画分割线
        for (int i = 0, size = mChildCount - 1; i < size; i++) {
            View mChildView = parent.getChildAt(i);
            RecyclerView.LayoutParams mChildParams = (RecyclerView.LayoutParams) mChildView.getLayoutParams();
            int top = mChildView.getBottom() + mChildParams.bottomMargin + mMarginPadding.bottom + mDividerPadding.top;
            int bottom = top + mDividerDrawable.getIntrinsicHeight();
            mDividerDrawable.setBounds(left, top, right, bottom);
            mDividerDrawable.draw(c);
        }
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent, @NonNull Drawable mDividerDrawable) {
        int top = mMarginPadding.top + parent.getPaddingTop() + mDividerPadding.top;
        int bottom = parent.getHeight() - mMarginPadding.bottom - parent.getPaddingBottom() - mDividerPadding.bottom;

        int mChildCount = parent.getChildCount();

        for (int i = 0, size = mChildCount - 1; i < size; i++) {
            View mChildView = parent.getChildAt(i);
            RecyclerView.LayoutParams mChildParams = (RecyclerView.LayoutParams) mChildView.getLayoutParams();

            int left = mChildView.getRight() + mChildParams.rightMargin + mMarginPadding.right + mDividerPadding.left;
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
                int mDividerDrawableHeight = mDividerDrawable == null ? 0 : mDividerDrawable.getIntrinsicHeight();
                outRect.set(mMarginPadding.left, mMarginPadding.top, mMarginPadding.right,
                        mMarginPadding.bottom + mDividerPadding.top + mDividerDrawableHeight + mDividerPadding.bottom);
                break;
            case HORIZONTAL:
                int mDividerDrawableWidth = mDividerDrawable == null ? 0 : mDividerDrawable.getIntrinsicWidth();
                outRect.set(mMarginPadding.left, mMarginPadding.top,
                        mMarginPadding.right + mDividerPadding.left + mDividerDrawableWidth + mDividerPadding.right,
                        mMarginPadding.bottom);
                break;
            default:
                throw new IllegalArgumentException("invalid orientation:" + mOrientation);
        }
    }

}
