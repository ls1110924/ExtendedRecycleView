package com.yunxian.extended.recycleview.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunxian.extended.recycleview.adapter.BaseRecycleViewAdapter;
import com.yunxian.extended.recycleview.app.R;
import com.yunxian.extended.recycleview.app.model.TodoTaskModel;

import java.util.List;

/**
 * @author A Shuai
 * @email ls1110924@163.com
 * @date 2016/9/26 19:14
 */
public class HorizontalRecycleViewAdapter extends BaseRecycleViewAdapter<RecyclerView.ViewHolder> {

    private final List<TodoTaskModel> mTasksList;

    public HorizontalRecycleViewAdapter(@NonNull Context mContext,
                                        @NonNull List<TodoTaskModel> mTasksList) {
        super(mContext);
        this.mTasksList = mTasksList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = mInflater.inflate(R.layout.item_home_todo_tasks, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolderWithContent(RecyclerView.ViewHolder holder, int position) {
        if (position == mTasksList.size()) {
            return;
        }

        if (!(holder instanceof ViewHolder)) {
            throw new IllegalStateException();
        }

        ViewHolder mViewHolder = (ViewHolder) holder;

        TodoTaskModel mModel = mTasksList.get(position);

        mViewHolder.titleTV.setText(mModel.getTitle());
        mViewHolder.contentTV.setText(mModel.getContent());
    }

    @Override
    public int getItemCount() {
        return mTasksList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTV;
        private final TextView contentTV;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTV = findView(itemView, R.id.todo_task_title);
            contentTV = findView(itemView, R.id.todo_task_content);
        }


        public TextView getTitleTV() {
            return titleTV;
        }

        public TextView getContentTV() {
            return contentTV;
        }

    }

    public static <T extends View> T findView(View mParentView, int id) {
        return (T) mParentView.findViewById(id);
    }

}
