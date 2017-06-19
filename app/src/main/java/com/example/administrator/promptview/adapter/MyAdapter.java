package com.example.administrator.promptview.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.promptview.Message;
import com.example.administrator.promptview.R;
import com.example.administrator.promptview.widget.ChatPromptViewManager;
import com.example.administrator.promptview.widget.Location;
import com.example.administrator.promptview.widget.PromptViewHelper;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Message> mMessageList = null;
    private Activity mActivity;

    public MyAdapter(Activity activity, List<Message> messageList) {
        mActivity = activity;
        mMessageList = messageList;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new LeftViewHolder(View.inflate(mActivity, R.layout.activity_item_left, null));
        } else {
            return new RightViewHolder(View.inflate(mActivity, R.layout.activity_item_right, null));
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMessageList.get(position).getType();
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        PromptViewHelper pvHelper = new PromptViewHelper(mActivity);
        Message message = mMessageList.get(position);
        if (holder instanceof LeftViewHolder) {
            LeftViewHolder leftViewHolder = (LeftViewHolder) holder;
            leftViewHolder.tv.setText(message.getContent());
            leftViewHolder.tv.setTextColor(Color.parseColor("#101010"));
            leftViewHolder.tv.setBackgroundResource(R.mipmap.receive_message_pressed);
            //Helper 传递一个弹出框管理器 -- 这个管理器定义了左边需要弹出的对话框内容
            pvHelper.setPromptViewManager(new ChatPromptViewManager(mActivity, Location.TOP_CENTER));

        }
        if (holder instanceof RightViewHolder) {
            RightViewHolder rightViewHolder = (RightViewHolder) holder;
            rightViewHolder.tv.setText(message.getContent());
            rightViewHolder.tv.setTextColor(Color.parseColor("#ffffff"));
            rightViewHolder.tv.setBackgroundResource(R.mipmap.send_message_pressed);
            //Helper 传递一个弹出框管理器 -- 这个管理器定义了右边需要弹出的对话框内容
            pvHelper.setPromptViewManager(new ChatPromptViewManager(mActivity, Location.TOP_CENTER));
        }

        /**
         * 将每一个view  传给pvHelper进行点击事件的处理
         */
        pvHelper.addPrompt(holder.itemView.findViewById(R.id.textview_content));

    }

    class LeftViewHolder extends BaseViewHolder {

        TextView tv;

        public LeftViewHolder(View view) {
            super(view);
            tv = (TextView) findViewById(R.id.textview_content);
        }
    }

    class RightViewHolder extends BaseViewHolder {

        TextView tv;

        public RightViewHolder(View view) {
            super(view);
            tv = (TextView) findViewById(R.id.textview_content);
        }
    }
}
