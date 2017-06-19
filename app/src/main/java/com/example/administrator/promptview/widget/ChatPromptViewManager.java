package com.example.administrator.promptview.widget;

import android.app.Activity;
import android.view.View;

/**
 * 创建PromptViewManger来管理 长按弹出事件
 */
public class ChatPromptViewManager extends PromptViewHelper.PromptViewManager {

    public ChatPromptViewManager(Activity activity, String[] dataArray, Location location) {
        super(activity, dataArray, location);
    }

    public ChatPromptViewManager(Activity activity) {
        this(activity, new String[]{"复制", "删除"}, Location.TOP_LEFT);
    }

    public ChatPromptViewManager(Activity activity, Location location) {
        this(activity, new String[]{"复制", "删除", "撤回"}, location);
    }


    @Override
    public View inflateView() {
        return new PromptView(activity);
    }

    @Override
    public void bindData(View view, String[] dataArray) {
        if (view instanceof PromptView) {
            PromptView promptView = (PromptView) view;
            promptView.setContentArray(dataArray);
        }
    }
}
