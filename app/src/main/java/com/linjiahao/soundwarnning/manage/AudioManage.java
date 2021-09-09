package com.linjiahao.soundwarnning.manage;

import com.linjiahao.soundwarnning.fft.Complex;
import com.linjiahao.soundwarnning.fft.FFT;
import com.linjiahao.soundwarnning.fft.FFT2;
import com.linjiahao.soundwarnning.recorder.AudioRecorder;
import com.linjiahao.soundwarnning.visualizeview.AudioVisualizeView;

public class AudioManage {
    public static final int fftLength = 8192;
    private final AudioVisualizeView audioVisualizeView;
    private AudioRecorder audioRecorder;
    private short[] audioDataBuffer;
    private boolean FFTing = false;
    private final Complex[] fft1Data1 = new Complex[fftLength];
    private final Complex[] fft1Data2 = new Complex[fftLength];
    private int fftDataLength = 0;
    double[] fftValueData = new double[fftLength / 2];
    private boolean first = true;
    private final FFT2 fft2Class = new FFT2(fftLength);
    private final double[] fft2Real = new double[fftLength];
    private final double[] fft2Imaginary = new double[fftLength];

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
//                            if (fft1AddData(audioDataBuffer[i],fft1Data1)) {
//                                fft1(fft1Data1,fftValueData);
//                                first = false;
//                                fftValueData=audioVisualizeView.swapFftDataCaptureData(fftValueData);
//                            }
                            if (fft2AddData(audioDataBuffer[i], fft2Real, fft2Imaginary)) {
                                fft2(fft2Real, fft2Imaginary);
                                first = false;
                                fftValueData = audioVisualizeView.swapFftDataCaptureData(fftValueData);
                            }
                        } else {
//                            if (fft1AddData(audioDataBuffer[i],fft1Data2)) {
//                                fft1(fft1Data2,fftValueData);
//                                first = true;
//                                fftValueData=audioVisualizeView.swapFftDataCaptureData(fftValueData);
//                            }
                            if (fft2AddData(audioDataBuffer[i], fft2Real, fft2Imaginary)) {
                                fft2(fft2Real, fft2Imaginary);
                                first = true;
                                fftValueData = audioVisualizeView.swapFftDataCaptureData(fftValueData);
                            }
                        }
                    }
                    FFTing = false;
                }).start();
            }
        });
        audioRecorder.startRecord();
    }

    private boolean fft1AddData(short i, Complex[] fftData) {
        fftData[fftDataLength] = new Complex(i, 0);
        fftDataLength++;
        return fftDataLength == fftData.length;
    }

    private void fft1(Complex[] fftData, double[] fftValueData) {
        fftDataLength = 0;
        Complex[] frequency = FFT.getFFT(fftData);
        for (int j = 0; j < fftLength / 2; j++) {
            fftValueData[j] = frequency[j].getMod();
        }
    }

    private boolean fft2AddData(short i, double[] real, double[] imaginary) {
        real[fftDataLength] = i;
        imaginary[fftDataLength] = 0;
        fftDataLength++;
        return fftDataLength == real.length;
    }

    private void fft2(double[] real, double[] imaginary) {
        fftDataLength = 0;
        fft2Class.fft(real, imaginary);
        for (int i = 0; i < fftLength / 2; i++) {
            fftValueData[i] = Math.sqrt(real[i] * real[i] + imaginary[i] * imaginary[i]);
        }
    }
}
