package com.linjiahao.soundwarnning.visualizeview;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.linjiahao.soundwarnning.MainActivity;

import java.util.concurrent.locks.Lock;

public abstract class
AudioVisualizeView extends View {
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
                invalidate();
                obj.notifyAll();
            }
        }
    }

    protected double[] mRawAudioBytes;
    private final Object obj = new Object();
    private final Object obj1 = new Object();
    private boolean runtime = false;
    protected float drawStartX = 0;
    protected float drawStartY = 0;
    private float lastX = 0;
    private float lastY = 0;

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
        setOnTouchListener((v, event) -> {
            float x = event.getX();
            float y = event.getY();
            int metaState = event.getActionMasked();
            if (metaState== MotionEvent.ACTION_DOWN) {
                lastX = x;
                lastY = y;
                //Toast.makeText(MainActivity.content, "(" + (int) x + "," + (int) y + ")", Toast.LENGTH_SHORT).show();
            } else if (metaState == MotionEvent.ACTION_MOVE) {
                if (getWidth() > getHeight()) {
                    drawStartX = drawStartX + x - lastX;
                    //Toast.makeText(MainActivity.content, "(" + (int) drawStartX + ")", Toast.LENGTH_SHORT).show();
                } else {
                    drawStartY = drawStartY + y - lastY;
                    //Toast.makeText(MainActivity.content, "(" + (int) drawStartY + ")", Toast.LENGTH_SHORT).show();
                }
                lastX = x;
                lastY = y;
            } else if (metaState == MotionEvent.ACTION_UP) {
                if (x == lastX && y == lastY) {
                    v.performClick();
                }
            }
            //Toast.makeText(MainActivity.content, "" + event.getActionMasked(), Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    public double[] swapFftDataCaptureData(double[] parseData) {
        syncRunnable syncRunnable = new syncRunnable(parseData);
        synchronized (obj1) {
            if (runtime) {
                return parseData;
            } else {
                runtime = true;
            }
        }
        synchronized (obj) {
            post(syncRunnable);
            try {
                obj.wait();
                //postInvalidate();
                synchronized (obj1) {
                    runtime = false;
                    return syncRunnable.temp;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException("wait等待锁异常，异常原因：" + e.getMessage());
            }
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();
//        switch (event.getMetaState()) {
//            case MotionEvent.ACTION_DOWN: {
//                lastX = x;
//                lastY = y;
//                Toast.makeText(MainActivity.content, "(" + (int) x + "," + (int) y + ")", Toast.LENGTH_SHORT).show();
//                break;
//            }
//            case MotionEvent.ACTION_MOVE: {
//                if (getWidth() > getHeight()) {
//                    drawStartX = drawStartX + x - lastX;
//                    Toast.makeText(MainActivity.content, "(" + (int) drawStartX + ")", Toast.LENGTH_SHORT).show();
//                } else {
//                    drawStartY = drawStartY + y - lastY;
//                    Toast.makeText(MainActivity.content, "(" + (int) drawStartY + ")", Toast.LENGTH_SHORT).show();
//                }
//                lastX = x;
//                lastY = y;
//                break;
//            }
//            default:
//                break;
//        }
//        return true;
//    }
}
