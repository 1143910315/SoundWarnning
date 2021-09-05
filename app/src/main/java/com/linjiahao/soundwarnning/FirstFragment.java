package com.linjiahao.soundwarnning;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.linjiahao.soundwarnning.databinding.FragmentFirstBinding;
import com.linjiahao.soundwarnning.fft.URecorder;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(view1 -> NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment));
        binding.button.setOnClickListener(v -> {
            URecorder uRecorder = new URecorder(MainActivity.content, Environment.getExternalStorageDirectory().getPath() + "/download/mp3.mp3");
            uRecorder.start();
            new Handler().postDelayed(() -> {
                Toast.makeText(MainActivity.content, "录音完成", Toast.LENGTH_LONG).show();
                uRecorder.stop();
            }, 10 * 1000);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}