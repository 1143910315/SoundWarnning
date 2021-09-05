package com.linjiahao.soundwarnning;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.linjiahao.soundwarnning.databinding.FragmentSecondBinding;
import com.linjiahao.soundwarnning.fft.VisualizerFFTView;
import com.linjiahao.soundwarnning.fft.VisualizerView;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private VisualizerView mWaveView;
    private VisualizerFFTView mFFtView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(view1 -> NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment));
        binding.button2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.content, MainActivity2.class);
            //启动
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}