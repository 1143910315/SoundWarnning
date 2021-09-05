package com.linjiahao.soundwarnning;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;

import com.linjiahao.soundwarnning.fft.VisualizerFFTView;
import com.linjiahao.soundwarnning.fft.VisualizerView;

public class MainActivity2 extends AppCompatActivity {

    private VisualizerView mWaveView;
    private VisualizerFFTView mFFtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/download/mp3.mp3");  // 解析MP3文件路径到uri
        MediaPlayer mMedia = MediaPlayer.create(this, uri);  // 实例化mMedia对象，并通过uri将资源文件加载到该对象
        mWaveView = new VisualizerView(this);   // 创建VisualizerView对象
        mFFtView = new VisualizerFFTView(this);   // 创建VisualizerFFTView对象
        LinearLayout viewById = findViewById(R.id.linear);
        viewById.addView(mWaveView);
        viewById.addView(mFFtView);
        final int maxCR = Visualizer.getMaxCaptureRate();   // 获取最大采样率
        Visualizer mVisualizer = new Visualizer(mMedia.getAudioSessionId());   // 实例化mVisualizer
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);   // 设置内容长度为1024
        mVisualizer.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener() {
                    public void onWaveFormDataCapture(Visualizer visualizer,
                                                      byte[] waveform, int samplingRate) {
                        mWaveView.updateVisualizer(waveform);  // 更新时域波形数据
                    }

                    public void onFftDataCapture(Visualizer visualizer,
                                                 byte[] fft, int samplingRate) {
                        mFFtView.updateVisualizer(fft);  // 更新频域波形数据
                    }
                }, maxCR / 2, true, true);   // 采样速率为512MHz，设置同时获取时域、频域波形数据

    }
}