package com.emad.cctv.mymusicmachine;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


public class PlayService extends Service {

    MediaPlayer mPlayer;
    public IBinder mBinder=new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TAG","Create");
        mPlayer=MediaPlayer.create(this,R.raw.askme);
    }


    //since we want the song to be played in the background then we need to start the service


    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        Log.i("TAG","Start");
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopSelf();
            }
        });

        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("TAG","Bind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("TAG","Unbind");
        return super.onUnbind(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TAG","Destroy");
        mPlayer.release();
    }
    //here are the play and pause methods frm the user

    public void playSong(){
        mPlayer.start();
    }
    public void pauseSong(){
        mPlayer.pause();
    }

    public boolean isPlaying(){
       return mPlayer.isPlaying();
    }

    //since Binder already Extends IBinder  so
    //we can create LocalBinder Class which extends IBinder Interface

    public class LocalBinder extends Binder{
        //this method for returning an instance of our service in the MainActivity
        public  PlayService getService(){
            return PlayService.this;
        }

    }


}
