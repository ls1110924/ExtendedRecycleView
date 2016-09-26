package com.yunxian.extended.recycleview.itemdecoration;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 给RecycleView的Item做外留白处理的装饰器
 *
 * @author A Shuai
 * @email lishuai.ls@alibaba-inc.com
 * @date 2016/9/25 16:18
 */
public class MarginItemDecoration extends RecyclerView.ItemDecoration {

    private final Rect mMarginRect = new Rect();

    public MarginItemDecoration(@NonNull Drawable mMarginDrawable) {
        mMarginDrawable.getPadding(mMarginRect);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.set(mMarginRect);
    }
}
