package com.example.android.musicservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

import java.io.File;

public class MyService extends Service {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private PlayMusicBinder musicBinder = new PlayMusicBinder();

    class PlayMusicBinder extends Binder {
        public void initMediaPlayer() {
            try {
                File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
                mediaPlayer.setDataSource(file.getPath());
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void pauseMusic(){
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        musicBinder.initMediaPlayer();
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
