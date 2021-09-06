package com.linjiahao.soundwarnning.recorder;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class AudioRecorder {


    public interface BufferFullListener {
        /**
         * 当缓冲区满时回调
         */
        void onBufferFullCallback();
    }

    private final BufferFullListener listener;
    //一次处理的帧数
    public static final int PERIOD_IN_FRAMES = 12050;
    private int audioSource;
    private int sampleRateInHz;
    private int channelConfig;
    private int audioFormat;
    private int channelCount;
    private short[] buffer1;
    private short[] buffer2;
    private boolean first = true;
    private AudioRecord audioRecord;

    public AudioRecorder(BufferFullListener listener) {
        this.listener = listener;
        //麦克风
        audioSource = MediaRecorder.AudioSource.MIC;
        //44100采样率
        sampleRateInHz = 44100;
        //单声道
        channelConfig = AudioFormat.CHANNEL_IN_MONO;
        //数据格式
        audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    }

    public void startRecord() {
        int bufferSizeInBytes = sampleRateInHz* 2;
        audioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat,
                bufferSizeInBytes);
        setAudioSource(audioRecord.getAudioSource());
        setSampleRateInHz(audioRecord.getSampleRate());
        setChannelConfig(audioRecord.getChannelConfiguration());
        setAudioFormat(audioRecord.getAudioFormat());
        audioRecord.setPositionNotificationPeriod(PERIOD_IN_FRAMES);
        channelCount = audioRecord.getChannelCount();
        buffer1 = new short[PERIOD_IN_FRAMES * channelCount];
        buffer2 = new short[PERIOD_IN_FRAMES * channelCount];
        audioRecord.startRecording();
        audioRecord.setRecordPositionUpdateListener(new AudioRecord.OnRecordPositionUpdateListener() {
            @Override
            public void onMarkerReached(AudioRecord recorder) {

            }

            @Override
            public void onPeriodicNotification(AudioRecord recorder) {
                short[] buffer = getBuffer();
                int read = recorder.read(buffer, 0, PERIOD_IN_FRAMES * channelCount);
                if (read != PERIOD_IN_FRAMES *channelCount) {
                    throw new RuntimeException("运行异常，错误长度的缓冲区或错误的帧标记！");
                }
                listener.onBufferFullCallback();
            }
        });
    }

    private short[] getBuffer() {
        first = !first;
        return first ? buffer1 : buffer2;
    }

    public short[] swapAudioData(short[] emptyBuffer) {
        if (emptyBuffer == null || emptyBuffer.length != buffer1.length) {
            emptyBuffer = new short[buffer1.length];
        }
        short[] temp;
        if (first) {
            temp = buffer1;
            buffer1 = emptyBuffer;
        } else {
            temp = buffer2;
            buffer2 = emptyBuffer;
        }
        return temp;
    }

    public int getAudioSource() {
        return audioSource;
    }

    public void setAudioSource(int audioSource) {
        if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
            throw new RuntimeException("不能在录音时修改录音源！");
        }
        this.audioSource = audioSource;
    }

    public int getSampleRateInHz() {
        return sampleRateInHz;
    }

    public void setSampleRateInHz(int sampleRateInHz) {
        if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
            throw new RuntimeException("不能在录音时修改采样率！");
        }
        this.sampleRateInHz = sampleRateInHz;
    }

    public int getChannelConfig() {
        return channelConfig;
    }

    public int getChannelCount() {
        if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_STOPPED) {
            throw new RuntimeException("暂不支持在开始录音前获取声道数！");
        }
        return channelCount;
    }

    public void setChannelConfig(int channelConfig) {
        if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
            throw new RuntimeException("不能在录音时修改声道配置！");
        }
        this.channelConfig = channelConfig;
    }

    public int getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(int audioFormat) {
        if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
            throw new RuntimeException("不能在录音时修改录音数据格式！");
        }
        this.audioFormat = audioFormat;
    }

}
