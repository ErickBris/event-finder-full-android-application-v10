package com.libraries.seekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class MGVerticalSeekBar extends SeekBar {

    protected OnSeekBarChangeListener changeListener;

    public MGVerticalSeekBar(Context context) {
        super(context);
    }

    public MGVerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MGVerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas c) {
        c.rotate(-90);
        c.translate(-getHeight(), 0);
        super.onDraw(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                setSelected(true);
                setPressed(true);
                if (changeListener != null)
                    changeListener.onStartTrackingTouch(this);
                break;

            case MotionEvent.ACTION_UP:

                setSelected(false);
                setPressed(false);
                if (changeListener != null)
                    changeListener.onStopTrackingTouch(this);
                break;

            case MotionEvent.ACTION_MOVE:

                int progress = getMax()
                        - (int) (getMax() * event.getY() / getHeight());
                setProgress(progress);
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                if (changeListener != null)
                    changeListener.onProgressChanged(this, progress, true);
                break;

            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return true;

    }

//    public MGVerticalSeekBar(Context context) {
//        super(context);
//    }
//
//    public MGVerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//    }
//
//    public MGVerticalSeekBar(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(h, w, oldh, oldw);
//    }
//
//    @Override
//    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
//        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
//    }
//
//    protected void onDraw(Canvas c) {
//        c.rotate(-90);
//        c.translate(-getHeight(), 0);
//
//        super.onDraw(c);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (!isEnabled()) {
//            return false;
//        }
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_MOVE:
//            case MotionEvent.ACTION_UP:
//                setProgress(getMax() - (int) (getMax() * event.getY() / getHeight()));
//                onSizeChanged(getWidth(), getHeight(), 0, 0);
//                break;
//
//            case MotionEvent.ACTION_CANCEL:
//                break;
//        }
//        return true;
//    }

    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener mListener) {
        this.changeListener = mListener;
    }
}
