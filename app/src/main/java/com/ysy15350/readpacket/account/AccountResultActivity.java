package com.ysy15350.readpacket.account;

/**
 * Created by yangshiyou on 2017/9/20.
 */


import android.content.Intent;

import com.ysy15350.readpacket.R;

import org.xutils.view.annotation.ContentView;

import base.BaseActivity;
import common.CommFunAndroid;

/**
 * 账户操作结果提示
 */
@ContentView(R.layout.activity_account_result)
public class AccountResultActivity extends BaseActivity {

    @Override
    public void initView() {

        super.initView();
        setFormHead("提示");

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        if (!CommFunAndroid.isNullOrEmpty(title)) {
            mHolder.setText(R.id.tv_title, title);
        }

        if (!CommFunAndroid.isNullOrEmpty(content)) {
            mHolder.setText(R.id.tv_content, content);
        }

    }
}
