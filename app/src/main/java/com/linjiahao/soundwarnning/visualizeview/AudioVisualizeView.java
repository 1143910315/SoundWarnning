package com.linjiahao.soundwarnning.visualizeview;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.concurrent.locks.Lock;

public abstract class AudioVisualizeView extends View {
    private class syncRunnable implements Runnable {


        private final double[] parseData;
        private double[] temp;

        public syncRunnable(double[] parseData) {
            this.parseData = parseData;
        }

        @Override
        public void run() {
            synchronized (obj) {
                if (mRawAudioBytes == null) {
                    mRawAudioBytes = new double[parseData.length];
                }
                temp = mRawAudioBytes;
                mRawAudioBytes = parseData;
                obj.notifyAll();
            }
        }

        public double[] getTemp() {
            return temp;
        }
    }

    protected double[] mRawAudioBytes;
    private final Object obj = new Object();

    public AudioVisualizeView(Context context) {
        this(context, null);
    }

    public AudioVisualizeView(Context context,
                              @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioVisualizeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AudioVisualizeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public double[] swapFftDataCaptureData(double[] parseData) {
        syncRunnable syncRunnable = new syncRunnable(parseData);
        synchronized (obj) {
            post(syncRunnable);
            try {
                obj.wait();
                postInvalidate();
                return syncRunnable.temp;
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException("wait等待锁异常，异常原因："+e.getMessage());
            }
        }
    }
}
