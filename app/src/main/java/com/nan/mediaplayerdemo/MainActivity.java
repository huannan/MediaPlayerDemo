package com.nan.mediaplayerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.FileDescriptor;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final String TAG = "huannan";
    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    private Button btnPlay;
    private Button btnPause;
    private Button btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = findViewById(R.id.surface_view);
        btnPlay = findViewById(R.id.btn_play);
        btnPause = findViewById(R.id.btn_pause);
        btnStop = findViewById(R.id.btn_stop);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        mediaPlayer = new MediaPlayer();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.d(TAG, "onClick: play");
                    mediaPlayer.reset();
                    AssetFileDescriptor fileDescriptor = getAssets().openFd("sample.mp4");
                    mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                    mediaPlayer.setDisplay(surfaceHolder);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    Log.e(TAG, "onError e=" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: pause");
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: stop");
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            }
        });

        mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    surfaceView.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }
}