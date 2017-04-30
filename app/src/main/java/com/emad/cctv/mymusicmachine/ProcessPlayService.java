package com.emad.cctv.mymusicmachine;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;


public class ProcessPlayService extends Service {
    MediaPlayer mPlayer;
    Messenger mMessenger=new Messenger(new ProcessPlayServiceHandler(this));


    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer= MediaPlayer.create(this,R.raw.askme);

    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        Notification.Builder notificationBuilder=new Notification.Builder(this);
        notificationBuilder.setSmallIcon(android.R.drawable.ic_media_play);
        Notification notification=notificationBuilder.build();

        startForeground(11,notification);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopSelf();
                stopForeground(true);
            }
        });

        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }



    public void playSong(){
        mPlayer.start();
    }
    public void pauseSong(){
        mPlayer.pause();
    }

    public boolean isPlaying(){
        return mPlayer.isPlaying();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }
}
