package com.linjiahao.soundwarnning;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;
import com.linjiahao.soundwarnning.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static MainActivity content;
    //private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
//    private VisualizerView mWaveView;
//    private Uri uri;
//    private MediaPlayer mMedia;
//    private VisualizerFFTView mFFtView;
//    private Visualizer mVisualizer;

    private final int REQUEST_CODE_ADDRESS = 100;

    private void checkPermission() {
        int checkCoarse = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int checkCoarseFine = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (checkCoarse == PackageManager.PERMISSION_GRANTED && checkCoarseFine == PackageManager.PERMISSION_GRANTED) {
            //已经授权
        } else {//没有权限
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_ADDRESS);//申请授权

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ADDRESS:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // Permission Denied 权限被拒绝
                    Toast.makeText(this, "权限被禁用", Toast.LENGTH_LONG).show();
                }
                // Permission Granted 授予权限
                //处理授权之后逻辑
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        checkPermission();
        content = this;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setSupportActionBar(binding.toolbar);


        //uri = Uri.parse(AudioPath);  // 解析录音文件路径到uri
//        uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/download/mp3.mp3");  // 解析MP3文件路径到uri
//        mMedia = MediaPlayer.create(this, uri);  // 实例化mMedia对象，并通过uri将资源文件加载到该对象
//        mWaveView = new VisualizerView(this);   // 创建VisualizerView对象
//        mFFtView = new VisualizerFFTView(this);   // 创建VisualizerFFTView对象
//        final int maxCR = Visualizer.getMaxCaptureRate();   // 获取最大采样率
//        mVisualizer = new Visualizer(mMedia.getAudioSessionId());   // 实例化mVisualizer
//        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);   // 设置内容长度为1024
//        mVisualizer.setDataCaptureListener(
//                new Visualizer.OnDataCaptureListener() {
//                    public void onWaveFormDataCapture(Visualizer visualizer,
//                                                      byte[] waveform, int samplingRate) {
//                        mWaveView.updateVisualizer(waveform);  // 更新时域波形数据
//                    }
//
//                    public void onFftDataCapture(Visualizer visualizer,
//                                                 byte[] fft, int samplingRate) {
//                        mFFtView.updateVisualizer(fft);  // 更新频域波形数据
//                    }
//                }, maxCR / 2, true, true);   // 采样速率为512MHz，设置同时获取时域、频域波形数据
    }

    @Override
    protected void onStart() {
        super.onStart();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//
//        binding.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        //return true;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
        return super.onSupportNavigateUp();
    }
}