package com.years.ground_station.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vise.baseble.ViseBluetooth;
import com.vise.baseble.model.BluetoothLeDevice;
import com.years.ground_station.Booth.BluetoothChat;
import com.years.ground_station.Booth.RemoteCtl;
import com.years.ground_station.R;
import com.years.ground_station.USBHIDTerminal;
//import com.years.ground_station.bean.BlueDeviceCommand;
//import com.years.ground_station.bean.CanMsg;
//import com.years.ground_station.presenter.MainPresenter;

//import com.years.ground_station.service.Service_usb;


public class Activity_Home extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    private RelativeLayout relat_scan, relat_Receive,relat_Frame,relat_Set,relat_Curve,relat_State,
              relat_Control,relat_Calibration,relat_PID,relat_Connection,relat_Other;
    private TextView tv_ScanResult;
    private TextView txtFollow,txtSearch;

//    final byte[] remotecontroldata = new byte[]{
//            0x00, (byte)
//            0xAA,
//            0x02,
//            0x04,
//            (byte) 0x01,
//            (byte) 0xff,
//            (byte) 0xff,
//            (byte) 0xff,
//            (byte) 0xff,
//            (byte) 0xff,
//            (byte) 0xff,
//            (byte) 0xff,
//            8,
//            BlueDeviceCommand.EXTENDED_IDENTIFIER,
//            BlueDeviceCommand.DATA_FRAME};
//    public byte[] Data4 = new byte[]{
//            0x00, (byte)
//            0xAA,
//            //BlueDeviceCommand.APP_BLE_CH,
//            //BlueDeviceCommand.APP_HEARTBEAT,
//            0x02,
//            0x05,
//            (byte) 0x00,
//            (byte) 0x00,
//            (byte) 0x00,
//            (byte) 0x00,
//            (byte) 0x00,
//            (byte) 0x00,
//            (byte) 0x00,
//            (byte) 0x00,
//            8,
//            BlueDeviceCommand.EXTENDED_IDENTIFIER,
//            BlueDeviceCommand.DATA_FRAME};

//    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        mainPresenter=new MainPresenter();
//        mainPresenter.attachView(this);
        initView();
        initListener();
    }

    private void initView()
    {
        txtFollow = (TextView) findViewById(R.id.txtFollow);
        txtSearch = (TextView) findViewById(R.id.txtSearch);
        relat_scan= (RelativeLayout) findViewById(R.id.relat_scan);
        tv_ScanResult= (TextView) findViewById(R.id.tv_scanresult);
        relat_Receive= (RelativeLayout) findViewById(R.id.relat_receive);
        relat_Frame= (RelativeLayout) findViewById(R.id.relat_frame);
        relat_Set= (RelativeLayout) findViewById(R.id.relat_set);
        relat_Curve= (RelativeLayout) findViewById(R.id.relat_curve);
        relat_State= (RelativeLayout) findViewById(R.id.relat_state);
        relat_Control= (RelativeLayout) findViewById(R.id.relat_control);
        relat_Calibration= (RelativeLayout) findViewById(R.id.relat_calibration);
        relat_PID= (RelativeLayout) findViewById(R.id.relat_pid);
        relat_Connection= (RelativeLayout) findViewById(R.id.relat_connection);
        relat_Other= (RelativeLayout) findViewById(R.id.relat_other);


    }

    private void initListener(){
        relat_scan.setOnClickListener(this);
        relat_Receive.setOnClickListener(this);
        relat_Frame.setOnClickListener(this);
        relat_Set.setOnClickListener(this);
        relat_Curve.setOnClickListener(this);
        relat_State.setOnClickListener(this);
        relat_Control.setOnClickListener(this);
        relat_Calibration.setOnClickListener(this);
        relat_PID.setOnClickListener(this);
        relat_Connection.setOnClickListener(this);
        relat_Other.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.relat_scan:
                startActivity(new Intent(this, BluetoothChat.class));
//                mainPresenter.disconnect();
//                startActivityForResult(new Intent(this, Activity_Scan.class),1001);
                break;
            case R.id.relat_receive:
//                if (txtSearch.getText().equals("自动寻主"))
//                {
//                    txtSearch.setText("停止自动寻主");
//                    mainPresenter.sendData(remotecontroldata);
//                    Intent intent = new Intent();
//                    intent.setAction("startreceive");
//                    Activity_Home.this.sendOrderedBroadcast(intent,null);
//                    startService(new Intent(Activity_Home.this,ShadowService.class));
//                }
//                else if (txtSearch.getText().equals("停止自动寻主"))
//                {
//                    txtSearch.setText("自动寻主");
//                    Intent intent = new Intent();
//                    intent.setAction("stopreceive");
//                    Activity_Home.this.sendOrderedBroadcast(intent,null);
//                    stopService(new Intent(Activity_Home.this,ShadowService.class));
//                    mainPresenter.sendData(Data4);
//                    mainPresenter.sendData(Data4);
//                    mainPresenter.sendData(Data4);

//                }
//                startActivity(new Intent(this,Activity_Receive.class));
                break;
            case R.id.relat_frame:
                Log.i("oncreate:","remote control mode");
                Intent intent =new Intent(Activity_Home.this, USBHIDTerminal.class);
                startActivity(intent);
                break;
            case R.id.relat_set:
//                Intent intent_set=new Intent(Activity_Home.this,Activity_Set.class);
//                startActivity(intent_set);
                break;
            case R.id.relat_curve:
//                if (txtFollow.getText().equals("自动跟随"))
//                {
////                    Intent intent1=new Intent(Activity_Home.this,Activity_Curve.class);
//                    txtFollow.setText("停止自动跟随");
//                }
//               else if (txtFollow.getText().equals("停止自动跟随"))
//                {
//                    txtFollow.setText("自动跟随");
//                }
//                startActivity(new Intent(this,Activity_Curve.class));
                break;
            case R.id.relat_state:
                startActivity(new Intent(this,Activity_Curve.class));
                break;
            case R.id.relat_control:
                  startActivity(new Intent(this, RemoteCtl.class));
//                Log.i("oncreate:","remote control mode");
//                mainPresenter.sendData(remotecontroldata);
//                Intent intent2 =new Intent(Activity_Home.this,Activity_Remote.class);
//                startActivity(intent2);
                break;
            case R.id.relat_calibration:
                startActivity(new Intent(Activity_Home.this,Activityshow.class));
                break;
            case R.id.relat_pid:
                break;
            case R.id.relat_connection:
                break;
            case R.id.relat_other:
                startActivity(new Intent(Activity_Home.this,Activity_hid.class));
                break;
        }
    }
//    private void serviceStart(int mode)
//    {
//        switch (mode)
//        {
//            case 0:
//                startService(new Intent(this, Service_ble2.class));
//                break;
//            case 1:
//                startService(new Intent(this, Service_ble4.class));
//                break;
//            case 2:
//                Intent service_usb=new Intent(this,Service_usb.class);
//                startService(service_usb);
//                break;
//            case 3:
//                startService(new Intent(this, Service_udp.class));
//                break;
//            case 4:
//                startService(new Intent(this, Service_tcp.class));
//                break;
//            case 5:
//                startService(new Intent(this, Service_Test.class));
//                break;
//        }
//    }
//    private void serviceStop(int mode)
//    {
//        switch (mode)
//        {
//            case 0:
//                stopService(new Intent(this, Service_ble2.class));
//                break;
//            case 1:
//                stopService(new Intent(this, Service_ble4.class));
//                break;
//            case 2:
//                stopService(new Intent(this, Service_usb.class));
//                break;
//            case 3:
//                stopService(new Intent(this, Service_udp.class));
//                break;
//            case 4:
//                stopService(new Intent(this, Service_tcp.class));
//                break;
//            case 5:
//                stopService(new Intent(this,Service_Test.class));
//        }
//    }

    @Override
    protected void onRestart() {
        System.out.println("======Activity_Home  Restart====");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK)
            exitDialog();
        return super.onKeyDown(keyCode, event);
    }
    private void exitDialog()
    {
        Dialog dialog=new AlertDialog.Builder(this).setTitle("退出手机调试助手？").setMessage("确定退出么 小伙儿？").
                setPositiveButton("退出",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Activity_Home.this.finish();
                    }
                }).setNegativeButton("取消",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        //serviceStop(Link_Mode);
      //  timer.cancel();
        //EventBus.getDefault().unregister(this);
        ViseBluetooth.getInstance().disconnect();
        ViseBluetooth.getInstance().clear();
//        mainPresenter.detachView();
        super.onDestroy();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            BluetoothLeDevice bluetoothLeDevice = data.getParcelableExtra("device");
//            mainPresenter.connectDevice(bluetoothLeDevice);
        }
    }
//    @Override
//    public void showLoading(String msg) {
//
//    }

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        mOrContent.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
//        mCanContent.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
