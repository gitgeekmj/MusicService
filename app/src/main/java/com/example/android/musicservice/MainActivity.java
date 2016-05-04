package com.example.android.musicservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startMusicService;
    private Button stopMusicService;
    private Button pauseMusic;
    private Button unbindMusic;
    private MyService.PlayMusicBinder playMusicBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            playMusicBinder = (MyService.PlayMusicBinder) service;
            playMusicBinder.initMediaPlayer();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startMusicService = (Button) findViewById(R.id.start_bind_service);
        stopMusicService = (Button) findViewById(R.id.stop_music);
        stopMusicService.setOnClickListener(this);
        startMusicService.setOnClickListener(this);
        pauseMusic = (Button) findViewById(R.id.pause_music);
        pauseMusic.setOnClickListener(this);
        unbindMusic = (Button) findViewById(R.id.unbind_service);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_bind_service:
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent);
                bindService(startIntent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.stop_music:
                Intent stopMusic = new Intent(this, MyService.class);
                unbindService(connection);
                stopService(stopMusic);
                break;
            case R.id.pause_music:
                playMusicBinder.pauseMusic();
                break;
            case R.id.unbind_service:
                unbindService(connection);
                break;
            default:
                break;
        }
    }
}
