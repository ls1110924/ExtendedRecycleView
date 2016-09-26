package com.yunxian.extended.recycleview.app;

import android.content.Intent;
import android.view.View;

import com.yunxian.extended.recycleview.app.utils.BaseFragmentActivity;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onFindViews() {
        findViewById(R.id.hori_btn).setOnClickListener(mBaseCommonListener);
    }

    @Override
    protected void onBindContent() {

    }

    @Override
    protected boolean onBackKeyDown() {
        finish();
        return true;
    }

    @Override
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.hori_btn: {
                Intent mIntent = new Intent(this, HorizontalActivity.class);
                startActivity(mIntent);
                break;
            }
            default:
                super.onViewClick(v);
                break;
        }
    }
}
