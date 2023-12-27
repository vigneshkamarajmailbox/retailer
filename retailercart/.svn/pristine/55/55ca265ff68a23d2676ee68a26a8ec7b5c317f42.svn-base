package com.botree.retailerssfa.util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.botree.retailerssfa.db.FileAccessUtil;
import com.botree.retailerssfa.support.Globals;

import java.util.Timer;
import java.util.TimerTask;

public class Usbservice extends Service {

    Context con;
    public static final int NOTIFY = 1000;  //interval between two services(Here Service run every 5 Minute)
    private Handler mHandler = new Handler();   //run on another Thread to avoid crash
    private Timer mTimer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }

    @Override
    public void onCreate() {

        con = this;
        if (mTimer != null) // Cancel if already existed
            mTimer.cancel();
        else
            mTimer = new Timer();   //recreate new
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, NOTIFY);   //Schedule task
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();    //For Cancel Timer
        Toast.makeText(this, "Service is Destroyed", Toast.LENGTH_SHORT).show();
    }

    //class TimeDisplay for handling tasks
    class TimeDisplay extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent("USBUpdates");

                    if (FileAccessUtil.isUsbConnected(con)) {
                        intent.putExtra("UsbStatus", "connected");
                    } else {
                        intent.putExtra("UsbStatus", "disconnected");
                    }
                    if(Globals.isFileExists()){
                        intent.putExtra("FileStatus","NotDeleted");
                    }else{
                        intent.putExtra("FileStatus","Deleted");

                    }
                    LocalBroadcastManager.getInstance(con).sendBroadcast(intent);

                }
            });
        }
    }
}
