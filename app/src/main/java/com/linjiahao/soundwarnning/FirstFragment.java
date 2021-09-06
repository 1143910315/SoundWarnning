package com.linjiahao.soundwarnning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.linjiahao.soundwarnning.databinding.FragmentFirstBinding;
import com.linjiahao.soundwarnning.manage.AudioManage;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private AudioManage audioManage;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            binding = FragmentFirstBinding.inflate(inflater, container, false);
            audioManage = new AudioManage(binding.view1);
            audioManage.start();
            return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}