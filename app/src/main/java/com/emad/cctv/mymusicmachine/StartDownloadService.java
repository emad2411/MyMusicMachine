package com.emad.cctv.mymusicmachine;


import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class StartDownloadService extends IntentService {



    public StartDownloadService() {
        super("StartDownloadService");
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String song=intent.getStringExtra(MainActivity.TAG);

        if (song !=null){
            downloadSong(song);
            Toast.makeText(this,  song + " has been downloaded", Toast.LENGTH_SHORT).show();
        }

    }


    public void downloadSong(String song) {

        Long downloadTime = System.currentTimeMillis() + 10000;
        while (System.currentTimeMillis() < downloadTime) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.i("TAG", song + " has been downloaded");

    }
}
