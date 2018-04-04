package com.years.shadow.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
//
//import com.years.ground_station.bean.BlueDeviceCommand;
//import com.years.ground_station.bean.CanMsg;
import com.years.shadow.booth.BluetoothChatService;
//import com.years.ground_station.presenter.MainPresenter;


/**
 * Created by zhumingqing on 2017/7/13.
 */

public class ShadowService extends Service{

    private static final String TGA=ShadowService.class.getSimpleName();
    public static int x = 0;
    private BluetoothChatService bluetoothChatService;
//    MainPresenter mainpresenter=new MainPresenter();
//    Timer timer=new Timer();

//    public byte[] Data4 = new byte[]{
//            0x00, (byte)
//            0xAA,
//            //BlueDeviceCommand.APP_BLE_CH,
//            //BlueDeviceCommand.APP_HEARTBEAT,
//            0x02,
//            0x05,
//            (byte) 0x00,
//            (byte) 0x00,
//            (byte) (AbstractUSBHIDService.right/256),
//            (byte) (AbstractUSBHIDService.right%256),
//            (byte) 0x00,
//            (byte) 0x00,
//            (byte) (AbstractUSBHIDService.left/256),
//            (byte) (AbstractUSBHIDService.left%256),
//            8,
//            BlueDeviceCommand.EXTENDED_IDENTIFIER,
//            BlueDeviceCommand.DATA_FRAME};
//

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();
//        mainpresenter.attachView(this);

//        timer.schedule(new TimerTask() {
//        @Override
//        public void run() {
//            Message message=new Message();
//            message.what=1;
//            handler.sendEmptyMessage(message.what);
//        }
//    },1000,300);
        Log.i(TGA," service start");
}

//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            int msgId = msg.what;
//            switch (msgId) {
//                case 1:
//                    Log.i(TGA," send message");
//                    x++;
//
//                    Data4[6]=(byte) (AbstractUSBHIDService.right/256);
//                    Data4[7]=(byte) (AbstractUSBHIDService.right%256);
//                    Data4[10]=(byte) (AbstractUSBHIDService.left/256);
//                    Data4[11]=(byte) (AbstractUSBHIDService.left/256);
//
//                    mainpresenter.sendData(Data4);
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    @Override
    public void onDestroy() {
        Log.i(TGA, "onDestroy: service stop");
//        timer.cancel();
//        mainpresenter.detachView();
        super.onDestroy();
    }

//    @Override
//    public void showLoading(String msg) {
//
//    }
//
//    @Override
//    public void hideLoading() {
//
//    }
//
//    @Override
//    public void showMsg(String errorMsg) {
//
//    }
//
//    @Override
//    public void setConnectSuccess(String name, String address) {
//
//    }
//
//    @Override
//    public void onDisconnect() {
//
//    }
//
//    @Override
//    public void onDataGet(CanMsg canMsg) {
//
//    }
}
