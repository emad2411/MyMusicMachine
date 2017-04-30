package com.emad.cctv.mymusicmachine;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;


public class ProcessPlayServiceHandler extends Handler {
    private ProcessPlayService mProcessPlayService;

    public ProcessPlayServiceHandler(ProcessPlayService processPlayService) {
        mProcessPlayService = processPlayService;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.arg1){
            case 0:
                Log.i("TAG","play");
                mProcessPlayService.playSong();
                break;
            case 1:
                Log.i("TAG","pause");
                mProcessPlayService.pauseSong();
                break;
            case 2:
                Log.i("TAG","Check if playing");
                int isPlaying= mProcessPlayService.isPlaying()?1:2;
                Message message=Message.obtain();
                message.arg1=isPlaying;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
        }
    }
}
