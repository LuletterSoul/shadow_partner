package com.years.ground_station.Booth;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kongqw.rockerlibrary.view.RockerView;
import com.years.ground_station.R;
import com.years.ground_station.activity.BaseApplication;
import com.years.ground_station.utils.Utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhumingqing on 2017/9/10.
 */

public class RemoteCtl extends Activity {

    private BluetoothChatService mChatService;

    private Timer timer,timer2;

    private byte[] bytes={(byte)0x84,
            (byte)0x00,
            (byte)0x2b,(byte)0x01,(byte)0x03,
            (byte)0xe8,(byte)0x03,(byte)0x00,(byte)0x00};

    private byte[] bytes1={(byte)0x84,
            (byte)0x00,
            (byte)0x1b,(byte)0x01,(byte)0x03,
            (byte)0xe8,(byte)0x03,(byte)0x00,(byte)0x00

    };



    private byte[] leftstop={(byte)0x84,
            (byte)0x00,
            (byte)0x1b,(byte)0x01,(byte)0x03,
            (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00

    };

    private byte[] rightstop={(byte)0x84,
            (byte)0x00,
            (byte)0x2b,(byte)0x01,(byte)0x03,
            (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00

    };

    private byte[] bothstop={(byte)0x84,
            (byte)0x00,
            (byte)0x3b,(byte)0x01,(byte)0x01,
            (byte)0x01,(byte)0x00,(byte)0x00,(byte)0x00

    };

    private byte[]LeftSend=new byte[9];
    private byte[]rightSend=new byte[9];

    private int speed1=1000,speed2=1000;

    private byte[]newbyte=new byte[4];


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remote);



        RockerView rockerView = (RockerView)findViewById(R.id.rubberLeft);
        RockerView rockerView1=(RockerView)findViewById(R.id.rubberRight);

        Button button1=(Button)findViewById(R.id.btnLeftmin);
        Button button2=(Button)findViewById(R.id.btnLeftpuls);
        Button button3=(Button)findViewById(R.id.btnRightmin);
        Button button4=(Button)findViewById(R.id.btnRightplus);
        Button buttonStop=(Button)findViewById(R.id.stop);

        final TextView textView1=(TextView)findViewById(R.id.speedLeft);
        final TextView textView2=(TextView)findViewById(R.id.speedRight);

        mChatService=((BaseApplication)getApplication()).getBluetooth();

        if(mChatService==null){
            textView1.setText("no bluetooth");
            textView2.setText("no bluetooth");

        }

        timer=new Timer();
        timer2=new Timer();

        setTimer();
        setTimer2();

        //letf speed min bytes1

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                speed1=Utils.bytesToInt(bytes1,5);

                if(speed1>50){
                    speed1 -=50;
                }
//
//                newbyte=Utils.intToBytes2(speed1);
//
//                bytes1[5]=newbyte[0];
//                bytes1[6]=newbyte[1];
//                bytes1[7]=newbyte[2];
//                bytes1[8]=newbyte[3];


            }
        });

        //letf speed puls

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                speed1=Utils.bytesToInt(bytes1,5);
                if(speed1<10000){
                    speed1 +=50;
                }

//                newbyte=Utils.intToBytes2(speed1);
//
//                bytes1[5]=newbyte[0];
//                bytes1[6]=newbyte[1];
//                bytes1[7]=newbyte[2];
//                bytes1[8]=newbyte[3];

            }
        });

        //right speed min bytes

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                speed2=Utils.bytesToInt(bytes,5);
                if (speed2>50){
                    speed2 -=50;
                }

//                newbyte=Utils.intToBytes2(speed2);
//
//                bytes[5]=newbyte[0];
//                bytes[6]=newbyte[1];
//                bytes[7]=newbyte[2];
//                bytes[8]=newbyte[3];
            }
        });

        //right speed puls

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                speed2=Utils.bytesToInt(bytes,5);
                 if(speed2<10000) {
                    speed2 += 50;
                }
//
//                newbyte=Utils.intToBytes2(speed2);
//
//                bytes[5]=newbyte[0];
//                bytes[6]=newbyte[1];
//                bytes[7]=newbyte[2];
//                bytes[8]=newbyte[3];

            }
        });



        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.arraycopy(leftstop,0,LeftSend,0,9);
                System.arraycopy(rightstop,0,rightSend,0,9);
            }
        });

        //left

        rockerView.setCallBackMode(RockerView.CallBackMode.CALL_BACK_MODE_STATE_CHANGE);
        rockerView.setOnShakeListener(RockerView.DirectionMode.DIRECTION_2_VERTICAL, new RockerView.OnShakeListener() {
            @Override
            public void onStart() {
                textView1.setText(null);
            }


            @Override
            public void direction(RockerView.Direction direction) {
                if(direction.equals( RockerView.Direction.DIRECTION_UP)){
                    textView1.setText(Integer.toString(speed1));

//
                    newbyte= Utils.intToBytes2(speed1);

                    bytes1[5]=newbyte[0];
                    bytes1[6]=newbyte[1];
                    bytes1[7]=newbyte[2];
                    bytes1[8]=newbyte[3];

                    System.arraycopy(bytes1,0,LeftSend,0,9);

//                   mChatService.write(bytes1);
                }
                else if(direction.equals(RockerView.Direction.DIRECTION_DOWN)){
                      int speed3 = -speed1;


                    newbyte=Utils.intToBytes2(speed3);

                    bytes1[5]=newbyte[0];
                    bytes1[6]=newbyte[1];
                    bytes1[7]=newbyte[2];
                    bytes1[8]=newbyte[3];
                   System.arraycopy(bytes1,0,LeftSend,0,9);

//                    mChatService.write(leftstop);

                }


            }

            @Override
            public void onFinish() {
                System.arraycopy(leftstop,0,LeftSend,0,9);
//                mChatService.write(leftstop);
            }
        });

        //right

        rockerView1.setCallBackMode(RockerView.CallBackMode.CALL_BACK_MODE_STATE_CHANGE);
        rockerView1.setOnShakeListener(RockerView.DirectionMode.DIRECTION_2_VERTICAL, new RockerView.OnShakeListener() {
            @Override
            public void onStart() {
                textView2.setText(null);
            }

            @Override
            public void direction(RockerView.Direction direction) {
//                textView2.setText("方向: "+direction);

                if(direction.equals(RockerView.Direction.DIRECTION_DOWN)){
                    textView2.setText(Integer.toString(speed2));
//
                    newbyte=Utils.intToBytes2(speed2);

                    bytes[5]=newbyte[0];
                    bytes[6]=newbyte[1];
                    bytes[7]=newbyte[2];
                    bytes[8]=newbyte[3];
                    System.arraycopy(bytes,0,rightSend,0,9);
//                    mChatService.write(bytes);
                }
                else if(direction.equals(RockerView.Direction.DIRECTION_UP)){
                    int speed4=-speed2;

                    newbyte=Utils.intToBytes2(speed4);

                    bytes[5]=newbyte[0];
                    bytes[6]=newbyte[1];
                    bytes[7]=newbyte[2];
                    bytes[8]=newbyte[3];
                    System.arraycopy(bytes,0,rightSend,0,9);
//                   mChatService.write(rightstop);
                }

            }

            @Override
            public void onFinish() {
                System.arraycopy(rightstop,0,rightSend,0,9);
//                mChatService.write(rightstop);

            }
        });

//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Message message=new Message();
//                message.what=1;
//                myhandler.sendMessage(message);
//
//            }
//        },50,50);




    }

    private void setTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message=new Message();
                message.what=1;
                myhandler.sendMessage(message);

            }
        },50,50);
    }

    private Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int msgid=msg.what;
            switch (msgid){
                case 1:
                    mChatService.write(LeftSend);
                    break;
                default:
                    break;
            }
        }
    };

    private void setTimer2(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message=new Message();
                message.what=1;
                myhandler2.sendMessage(message);

            }
        },25,50);
    }

    private Handler myhandler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int msgid=msg.what;
            switch (msgid){
                case 1:
                    mChatService.write(rightSend);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
