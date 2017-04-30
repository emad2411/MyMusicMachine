package com.emad.cctv.mymusicmachine;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    private boolean mBound=false;
    public boolean isPlaying=false;
    private Messenger mServiceMessenger;
    private Messenger mActivityMessenger=new Messenger(new ActivityHandler(this));
    private Button mProcessPlayButton;
    private ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mServiceMessenger =new Messenger(iBinder);
            mBound=true;
            Message message=Message.obtain();
            message.arg1=2;
            message.replyTo=mActivityMessenger;
            try {
                mServiceMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
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
        setContentView(R.layout.activity_main2);

        mProcessPlayButton = (Button) findViewById(R.id.process);

        mProcessPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Main2Activity.this,ProcessPlayService.class);
                startService(intent);
                Message message=Message.obtain();

                if (isPlaying){
                    setButtonText("Play");
                    message.arg1=1;
                    isPlaying=false;

                }else {
                    setButtonText("Pause");
                    message.arg1=0;
                    isPlaying=true;
                }
                try {
                    mServiceMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    void setButtonText(String text){
        mProcessPlayButton.setText(text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(this,ProcessPlayService.class);
        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mServiceConnection);
        mBound=false;
    }
}
