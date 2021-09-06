package com.linjiahao.soundwarnning.visualizeview;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

public class SingleVisualizeView extends AudioVisualizeView {


    public SingleVisualizeView(Context context) {
        this(context, null);
    }

    public SingleVisualizeView(Context context,
                               @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SingleVisualizeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SingleVisualizeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
//        Paint pp = new Paint();
//        pp.setColor(Color.GREEN);
//        pp.setStyle(Paint.Style.FILL);
//        canvas.drawRect(new Rect(0,0,getWidth(),getHeight()),pp);
        if (mRawAudioBytes == null) {
            return;
        }
        int width = getWidth();
        int height = getHeight();
        Paint mPaint = new Paint();
        mPaint.setColor(Color.argb(255, 255, 0, 0));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.SOLID));
        int block = 30;
        int length = mRawAudioBytes.length / block;
        if (width > height) {
            mPaint.setStrokeWidth(1f * width / length);
            for (int i = 0; i < length; i++) {
                float startX = 1f * width * i / length;
                int startY = height - 50;
                float stopX = 1f * width * i / length;
                float stopY = (float) (height - 50 - mRawAudioBytes[i * block]);
                stopY = stopY / 100;
                canvas.drawLine(startX, startY, stopX, stopY, mPaint);
            }
        } else {
            mPaint.setStrokeWidth(1f * height / length);
            for (int i = 0; i < length; i++) {
                float startX = 10;
                float startY = 1f * height * i / length;
                float stopX = (float) (10 + mRawAudioBytes[i * block]);
                stopX = stopX / 100;
                float stopY = 1f * height * i / length;
                canvas.drawLine(startX, startY, stopX, stopY, mPaint);
            }
        }
    }
}
