package com.emad.cctv.mymusicmachine;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class ActivityHandler extends Handler {
    private Main2Activity mActivity;

    public ActivityHandler(Main2Activity activity) {
        mActivity = activity;
    }

    @Override
    public void handleMessage(Message msg) {

        switch (msg.arg1){
            case 1:
                mActivity.setButtonText("Pause");
                mActivity.isPlaying=true;
                Log.i("TAG","is Playing");
                break;
            case 2:
                mActivity.setButtonText("Play");
                mActivity.isPlaying=false;
                Log.i("TAG","is not Playing");
                break;
        }


    }
}
