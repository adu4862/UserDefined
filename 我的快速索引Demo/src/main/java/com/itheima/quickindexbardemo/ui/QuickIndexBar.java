package com.itheima.quickindexbardemo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2016/10/12 0012.
 */

public class QuickIndexBar extends View {

    private static final String[] LETTERS = new String[]{
            "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X",
            "Y", "Z"
    };
    private float cellWidth;
    private float cellHeight;
    private Paint paint;

    public interface OnLetterUpdateListener {
        void updateLetter(String letter);
    }

    private OnLetterUpdateListener mOnLetterUpdateListener;

    public void setmOnLetterUpdateListener(OnLetterUpdateListener mOnLetterUpdateListener) {
        this.mOnLetterUpdateListener = mOnLetterUpdateListener;
    }

    public QuickIndexBar(Context context) {
        this(context, null);
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //创建一个抗锯齿的画笔(enables antialiasing when drawing.)
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置画笔文本加粗
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(30f);
        paint.setColor(Color.WHITE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        cellWidth = getMeasuredWidth() * 1.0f;
        int measuredHeight = getMeasuredHeight();
        cellHeight = measuredHeight * 1.0f / LETTERS.length;
        Log.d(TAG, "onMeasure: " + "cellWidth:" + cellWidth + "cellHeight" + cellHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");

        //super.onDraw(canvas);
        for (int i = 0; i < LETTERS.length; i++) {
            String letter = LETTERS[i];
            //Log.d(TAG, "onDraw: " + letter);
            //得到文本x轴的坐标measureText()返回文字的宽度
            float x = cellWidth * 0.5f - paint.measureText(letter) * 0.5f;

            //通过得到文字的矩形得到文字的高度
            Rect bounds = new Rect();
            paint.getTextBounds(letter, 0, letter.length(), bounds);
            float y = bounds.height() * 0.5f + cellHeight * 0.5f + i * cellHeight;

            //将选中的索引颜色设置为灰色
            paint.setColor(i==lastIndex ? Color.GRAY :Color.WHITE);
            //通过传入的画板canvas画出文字
            canvas.drawText(letter, x, y, paint);
            //Log.d(TAG, "onDraw: " + "x:" + x + "y:" + y);


        }

    }

    private int lastIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y;
        int currentIndex;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y = (int) event.getY();
                //通过触摸的y轴确定按下的索引
                currentIndex = (int) (y / cellHeight);
                if (currentIndex != lastIndex) {//按下
                    if (currentIndex >= 0 && currentIndex < LETTERS.length) {
                        String letter = LETTERS[currentIndex];
                        if (mOnLetterUpdateListener != null) {
                            mOnLetterUpdateListener.updateLetter(letter);
                            Log.d(TAG, "onTouchEvent: letter :"+letter);
                        }
                    }
                    Log.d(TAG, "onTouchEvent: currentIndex :" + currentIndex);
                    lastIndex = currentIndex;
                }


                break;
            case MotionEvent.ACTION_MOVE:
                y = (int) event.getY();
                //通过触摸的y轴确定按下的索引
                currentIndex = (int) (y / cellHeight);
                if (currentIndex != lastIndex) {//按下
                    if (currentIndex >= 0 && currentIndex < LETTERS.length) {
                        String letter = LETTERS[currentIndex];
                        if (mOnLetterUpdateListener != null) {
                            mOnLetterUpdateListener.updateLetter(letter);
                            Log.d(TAG, "onTouchEvent: letter :"+letter);
                        }
                    }
                    Log.d(TAG, "onTouchEvent: currentIndex :" + currentIndex);
                    lastIndex = currentIndex;
                }
                break;
            case MotionEvent.ACTION_UP:
                lastIndex =-1;
                break;

        }
        invalidate();
        return true;

    }
}
