package com.example.administrator.promptview.widget;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.administrator.promptview.R;

/**
 * Created by Administrator on 2017/6/15.
 * PromptViewHelp 对象 用来接收Adapter传递的view  设置点击事件
 */

public class PromptViewHelper implements View.OnClickListener {

    private PromptViewManager promptViewManager; //内部类
    private PopupWindow popupWindow;
    private Activity activity;
    private boolean isShow;

    public PromptViewHelper(Activity activity) {
        this.activity = activity;
    }


    /**
     * Adapter中设置
     *
     * @param promptViewManager
     */
    public void setPromptViewManager(PromptViewManager promptViewManager) {
        this.promptViewManager = promptViewManager;

    }

    public void addPrompt(View srcView) {
        srcView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                createPromptView(v);
                return true;
            }
        });
    }

    /**
     * 创建弹出框
     *
     * @param srcView
     */
    private void createPromptView(final View srcView) {
        popupWindow = new PopupWindow();

        if (popupWindow == null)
            popupWindow = new PopupWindow(activity);
        popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //弹出框内容设置
//        final View promptView = promptViewManager.getPromptView();
        final View promptView = View.inflate(activity, R.layout.prompt_layout, null);
        popupWindow.setContentView(promptView);

        Button btn1 = (Button) promptView.findViewById(R.id.btn1);
        Button btn2 = (Button) promptView.findViewById(R.id.btn2);
        Button btn3 = (Button) promptView.findViewById(R.id.btn3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        final int[] location = new int[2];
        promptView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isShow && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    show(srcView, promptView, location);
                }
            }
        });

        srcView.getLocationOnScreen(location);
        show(srcView, promptView, location);
    }

    public void show(View srcView, View promptView, int[] srcViewLocation) {

        int[] xy = promptViewManager.getLocation().calculateLocation.calculate(srcViewLocation, srcView, promptView);


        Log.e("aa", "x = " + xy[0] + "--------y = " + xy[1]);
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        //根据资源ID获取尺寸值
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

//        int titleBarHeight = activity.getActionBar().getHeight();

        popupWindow.showAtLocation(srcView, Gravity.NO_GRAVITY, xy[0], xy[1]);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Toast.makeText(activity, "复制", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn2:
                Toast.makeText(activity, "删除", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn3:
                Toast.makeText(activity, "撤回", Toast.LENGTH_SHORT).show();
                break;
        }

        popupWindow.dismiss();

    }

    /**
     * 为了让上层调用简单，方便，把提示框View封装到一个类中，这个类包括：
     * 初始方法，绑定数据，添加事件等等；基于这样的考虑，首先定义一个抽象类，
     * 然后让具体的实现类来实现相应的方法，我们先来看看这个抽象类。
     */
    public static abstract class PromptViewManager {

        private View promptView;
        protected Activity activity;
        private String[] dataArray;
        private Location location;//显示这个提示框View的时候会要考虑它显示的位置

        //这个实现实在子类中实现的
        public PromptViewManager(Activity activity, String[] dataArray, Location location) {
            this.activity = activity;
            this.dataArray = dataArray;
            this.location = location;
            init();
        }

        public void init() {
            promptView = inflateView();
            bindData(promptView, dataArray);
        }


        public abstract View inflateView();

        public abstract void bindData(View view, String[] dataArray);

        public View getPromptView() {
            return promptView;
        }

        public Location getLocation() {
            return location;
        }

    }
}
