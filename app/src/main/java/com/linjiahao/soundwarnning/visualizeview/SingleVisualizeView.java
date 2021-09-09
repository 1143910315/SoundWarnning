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
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.SOLID));
        int length = mRawAudioBytes.length;
        float scale = 100;
        mPaint.setStrokeWidth(6f);
        if (width > height) {
            int i = Math.max((int) -drawStartX, 0);
            float startX = i + drawStartX;
            for (; i < length && startX < width; i++, startX += 6) {
                float startY = height - 50;
                float stopY = (float) (height - 50 - mRawAudioBytes[i] / scale);
                setColor(i, 44100, length, mPaint);
                canvas.drawLine(startX, startY, startX, stopY, mPaint);
            }
        } else {
            int i = Math.max((int) -drawStartY, 0);
            float startY = i + drawStartY;
            for (; i < length && startY < height; i++, startY += 6) {
                float startX = 50;
                float stopX = (float) (50 + mRawAudioBytes[i] / scale);
                setColor(i, 44100, length, mPaint);
                canvas.drawLine(startX, startY, stopX, startY, mPaint);
            }
        }
    }

    private void setColor(int fIndex, int sampleRate, int fLength, Paint paint) {
        double i = 1.0 * fIndex * sampleRate / fLength * 2;
        if (i > 65 && i < 1100) {
            paint.setColor(Color.YELLOW);
        } else {
            paint.setColor(Color.RED);
        }
    }
}
