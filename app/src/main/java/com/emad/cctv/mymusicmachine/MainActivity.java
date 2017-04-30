package com.emad.cctv.mymusicmachine;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG ="TAG" ;
    public PlayService mPlayService;
    private Button mDownloadButton;
    private Button mSongButton;
    private Button mNextPage;
    private boolean mBound;
    private ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //the iBinder object is the returned value from onBind() method in our service
            mBound=true;
            Log.i("TAG","Service connected");
            //we need to get an instance of our service PlayerService so we can play or Pause the song
            PlayService.LocalBinder binder = (PlayService.LocalBinder) iBinder;
            //and here finally we are getting an instance of our service
            mPlayService= binder.getService();
            if (mPlayService.isPlaying()){
                mSongButton.setText("Pause");
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound=false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDownloadButton=(Button) findViewById(R.id.download_button);
        mSongButton=(Button) findViewById(R.id.song_button);
        mNextPage=(Button) findViewById(R.id.next_page);

        mDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"downloading",Toast.LENGTH_LONG).show();

                for (String song:Playlist.songs){
                    Intent intent=new Intent(MainActivity.this,StartDownloadService.class);
                    intent.putExtra(TAG,song);
                    startService(intent);
                }
            }
        });

        mSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //here we can play or Pause the song
                //but first we need to know if we are already bound to the service
                if (mBound){
                    if (mPlayService.isPlaying()){
                        mPlayService.pauseSong();
                        mSongButton.setText("Play");
                    }else {
                        //but here we need the service to be started and keep playing in the background
                        //even if we unbound from the service when we exit the App
//                        Intent intent=new Intent(MainActivity.this,PlayService.class);
                        Intent intent=new Intent(MainActivity.this,PlayService.class);
                        startService(intent);

//                        startService(intent);
                        mPlayService.playSong();
                        mSongButton.setText("Pause");
                    }
                }

            }
        });

        mNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });

    }




    @Override
    protected void onStart() {
        super.onStart();
        Log.i("TAG","Start Invoked");
        Intent playServiceIntent=new Intent(this,PlayService.class);
        bindService(playServiceIntent,mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        Log.i("TAG","Stop Invoked");
        super.onStop();
        if (mBound){
            unbindService(mServiceConnection);
            mBound=false;
        }
    }





}
