package com.yunxian.extended.recycleview.app;

import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.yunxian.extended.recycleview.ExtendedRecycleView;
import com.yunxian.extended.recycleview.app.adapter.HorizontalRecycleViewAdapter;
import com.yunxian.extended.recycleview.app.model.TodoTaskModel;
import com.yunxian.extended.recycleview.app.utils.BaseFragmentActivity;
import com.yunxian.extended.recycleview.itemdecoration.LinearMarginDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A Shuai
 * @email lishuai.ls@alibaba-inc.com
 * @date 2016/9/26 17:50
 */
public class HorizontalActivity extends BaseFragmentActivity {

    private static final String TAG = HorizontalActivity.class.getSimpleName();

    private static int count = 0;

    private TextView mTipTextView;

    private ExtendedRecycleView mRecycleView;
    private final List<TodoTaskModel> mModels = new ArrayList<>();
    private HorizontalRecycleViewAdapter mAdapter;

    private final CommonListener mCommonListener = new CommonListener();

    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_horizontal);
    }

    @Override
    protected void onFindViews() {
        mTipTextView = findView(R.id.list_tip);

        mRecycleView = findView(R.id.list);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecycleView.addItemDecoration(new LinearMarginDividerItemDecoration(getResources().getDrawable(R.drawable.recycleview_item_margin), null));

        findViewById(R.id.add_btn).setOnClickListener(mBaseCommonListener);
        findViewById(R.id.remove_btn).setOnClickListener(mBaseCommonListener);
    }

    @Override
    protected void onBindContent() {
        mModels.clear();
        for (count = 0; count < 5; count++) {
            TodoTaskModel mModel = new TodoTaskModel();
            mModel.setTitle("Title---->" + count);
            mModel.setContent("Content---->" + count);
            mModels.add(mModel);
        }
        mAdapter = new HorizontalRecycleViewAdapter(this, mModels);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setOnItemVisibilityListener(mCommonListener);
    }

    @Override
    protected boolean onBackKeyDown() {
        finish();
        return true;
    }

    @Override
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn: {
                TodoTaskModel mModel = new TodoTaskModel();
                mModel.setTitle("Title---->" + count);
                mModel.setContent("Content---->" + count);
                mModels.add(mModel);
                count++;
                mAdapter.notifyItemInserted(mModels.size() - 1);
                break;
            }
            case R.id.remove_btn: {
                if (mModels.size() > 0) {
                    mModels.remove(mModels.size() - 1);
                    mAdapter.notifyItemRemoved(mModels.size());
                }
                break;
            }
            default:
                super.onViewClick(v);
                break;
        }
    }

    private class CommonListener implements ExtendedRecycleView.OnItemVisibilityListener {

        @Override
        public void onVisibilityChange(int firstVisibleIndex, int firstCompletelyVisibleItem, int lastCompletelyVisibleItem, int lastVisibleIndex) {
            mTipTextView.setText(Html.fromHtml(getString(R.string.hori_list_tip, firstCompletelyVisibleItem + 1, mModels.size())));
        }
    }
}
