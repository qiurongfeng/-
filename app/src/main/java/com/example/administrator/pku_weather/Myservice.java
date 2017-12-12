package com.example.administrator.pku_weather;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/12/6.
 */

public class Myservice extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("myservice","Myservice->onbind");
        return null;

    }
    @Override
    public  void onCreate(){
        super.onCreate();
        Log.d("myservice","Myservice->create");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("myservice","Myservice->startcommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("myservice","Myservice->deatroy");
    }
}