package com.linjiahao.soundwarnning.manage;

import com.linjiahao.soundwarnning.fft.Complex;
import com.linjiahao.soundwarnning.fft.FFT;
import com.linjiahao.soundwarnning.recorder.AudioRecorder;
import com.linjiahao.soundwarnning.visualizeview.AudioVisualizeView;

public class AudioManage {
    public static final int fftLength = 8192;
    private AudioVisualizeView audioVisualizeView;
    private AudioRecorder audioRecorder;
    private short[] audioDataBuffer;
    private boolean FFTing = false;
    private final Complex[] fftData1 = new Complex[fftLength];
    private final Complex[] fftData2 = new Complex[fftLength];
    private int fftDataLength = 0;
    double[] fftValueData = new double[fftLength / 4];
    private boolean first = true;

    public AudioManage(AudioVisualizeView audioVisualizeView) {
        this.audioVisualizeView = audioVisualizeView;
    }

    public void start() {
        audioRecorder = new AudioRecorder(() -> {
            if (!FFTing) {
                audioDataBuffer = audioRecorder.swapAudioData(audioDataBuffer);
                //如果FFT不能在时限内完成，放弃音频数据
                FFTing = true;
                new Thread(() -> {
                    for (int i = 0; i < audioDataBuffer.length; i += audioRecorder.getChannelCount()) {
                        if (first) {
                            fftData1[fftDataLength] = new Complex(audioDataBuffer[i], 0);
                            fftDataLength++;
                            if (fftDataLength == fftData1.length) {
                                fftDataLength = 0;
                                first = false;
                                Complex[] frequency = FFT.getFFT(fftData1);
                                for (int j = 0; j < fftLength / 4; j++) {
                                    fftValueData[j] = frequency[j].getMod();
                                }
                                fftValueData=audioVisualizeView.swapFftDataCaptureData(fftValueData);
                            }
                        } else {
                            fftData2[fftDataLength] = new Complex(audioDataBuffer[i], 0);
                            fftDataLength++;
                            if (fftDataLength == fftData1.length) {
                                fftDataLength = 0;
                                first = true;
                                Complex[] frequency = FFT.getFFT(fftData2);
                                for (int j = 0; j < fftLength / 4; j++) {
                                    fftValueData[j] = frequency[j].getMod();
                                }
                                fftValueData=audioVisualizeView.swapFftDataCaptureData(fftValueData);
                            }
                        }
                    }
                    FFTing = false;
                }).start();
            }
        });
        audioRecorder.startRecord();
    }
}
