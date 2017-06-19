package com.example.administrator.promptview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.promptview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */

public class PromptView extends View {


    private Paint mPaint;
    private int mSingleWidth = 120, mSingleHeight = 88;
    private int mLineWidth = 1; //两个选项间的线
    private String[] mContentArray = null;

    //文字Rect的集合
    private List<Rect> textRectList = new ArrayList<>();
    //集合用来存储每一个选项的Rect
    private List<RectF> rectFList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public PromptView(Context context) {
        super(context);
//        setBackgroundResource(R.drawable.popup);

        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = mSingleWidth * mContentArray.length + ((mContentArray.length - 1) * mLineWidth);
        setMeasuredDimension(width, mSingleHeight);
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(30);
    }

    /**
     * 绘制整个PromptView
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //ContentArray是外界传递过来要绘制的内容
        if (mContentArray == null) return;
        for (int i = 0; i < mContentArray.length; i++) {
            drawPromptRect(canvas, i);
        }
    }

    /**
     * 遍历外界传过来的ContentArray  绘制每一个Rect
     * 1. 绘制Rect
     * 2. 绘制文本
     * 3.
     *
     * @param canvas
     * @param i
     */
    private void drawPromptRect(Canvas canvas, int i) {
        mPaint.setColor(Color.TRANSPARENT);
        RectF tipRectF = new RectF();
        tipRectF.left = mSingleWidth * i + i * mLineWidth;
        tipRectF.top = 0;
        tipRectF.right = tipRectF.left + mSingleWidth;
        tipRectF.bottom = mSingleHeight;
        canvas.drawRect(tipRectF, mPaint);

        rectFList.add(tipRectF);

        //绘制文本内容
        mPaint.setColor(Color.WHITE);
        canvas.drawText(mContentArray[i], (tipRectF.right - tipRectF.left - textRectList.get(i).width()) / 2 + tipRectF.left, getFontBaseLine(), mPaint);

        if (i == mContentArray.length - 1) return;
        //绘制白线
        RectF lineRectf = new RectF();
        lineRectf.left = tipRectF.right;
        lineRectf.top = 20;
        lineRectf.right = lineRectf.left + mLineWidth;
        lineRectf.bottom = mSingleHeight - 20;
        canvas.drawRect(lineRectf, mPaint);
    }

    private float getFontBaseLine() {
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        return (getMeasuredHeight()) / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
    }


    /**
     * 外界提供方法 传递要绘制的选项 -- 比如 撤回 复制 粘贴
     *
     * @param contentArray
     */
    public void setContentArray(String[] contentArray) {
        mContentArray = contentArray;
        for (int m = 0; m < mContentArray.length; m++) {
            Rect rect = new Rect();
            mPaint.getTextBounds(mContentArray[m], 0, mContentArray[m].length(), rect);
            textRectList.add(rect);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                for (int n = 0; n < rectFList.size(); n++) {
                    RectF rect = rectFList.get(n);
                    if (event.getX() > rect.left && event.getX() < rect.right && event.getY() > rect.top && event.getY() < rect.bottom && onItemClickListener != null) {
                        onItemClickListener.onItemClick(n);
                        break;
                    }
                }
                break;
        }
        return true;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
