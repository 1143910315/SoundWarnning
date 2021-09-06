package com.linjiahao.soundwarnning.visualizeview;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
        int width = getWidth();
        int height = getHeight();
        Paint mPaint = new Paint();
        mPaint.setColor(Color.argb(0, 255, 0, 0));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.SOLID));
        if (width > height) {
            mPaint.setStrokeWidth(1f * width / mRawAudioBytes.length);
            for (int i = 0; i < mRawAudioBytes.length; i++) {
                canvas.drawLine(1f * width * i / mRawAudioBytes.length, height - 50, 1f * width * i / mRawAudioBytes.length, (float) (height - 50 - mRawAudioBytes[i]), mPaint);
            }
        } else {
            mPaint.setStrokeWidth(1f * height / mRawAudioBytes.length);
            for (int i = 0; i < mRawAudioBytes.length; i++) {
                canvas.drawLine(width + 50, 1f * height * i / mRawAudioBytes.length, (float) (50 + mRawAudioBytes[i]), 1f * height * i / mRawAudioBytes.length, mPaint);
            }
        }
    }
}
