package com.years.ground_station.activity;

import android.app.Application;
import android.content.Context;

import com.years.ground_station.Booth.BluetoothChatService;

/**
 * Created by zhumingqing on 2017/7/13.
 */

public class BaseApplication extends Application{

    static Context mContext;

    BluetoothChatService bluetoothChatService;

    public BluetoothChatService getBluetooth(){
        return bluetoothChatService;
    }

    public void setBluetoothChatService(BluetoothChatService blue){
        this.bluetoothChatService=blue;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
    public static Context getInstance(){
        return mContext;
    }



}
