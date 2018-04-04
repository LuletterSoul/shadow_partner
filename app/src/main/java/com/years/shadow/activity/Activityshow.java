package com.years.shadow.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.years.ground_station.R;
import com.years.shadow.core.services.AbstractUSBHIDService;
import com.years.shadow.service.ShadowService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhumingqing on 2017/7/14.
 */

public class Activityshow extends AppCompatActivity {
    TextView textView1,textView2,textView3,textView4,textView5;
    private EditText edtlogText1;
    Timer timer=new Timer();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityshow);

        textView1=(TextView)findViewById(R.id.textView1);
        textView2=(TextView)findViewById(R.id.textView2);
        textView3=(TextView)findViewById(R.id.textView3);
        textView4=(TextView)findViewById(R.id.textView4);
        textView5= (TextView) findViewById(R.id.textView5);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message=new Message();
                message.what=1;
                myhandler.sendMessage(message);
            }
        },1000,300);

    }
    Handler myhandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                Log.i("text", "handleMessage:change ");
                textView1.setText(String.valueOf(AbstractUSBHIDService.x));
                textView2.setText(String.valueOf(AbstractUSBHIDService.y));
                textView3.setText(String.valueOf(AbstractUSBHIDService.left));
                textView4.setText(String.valueOf(AbstractUSBHIDService.right));
                textView5.setText(String.valueOf(ShadowService.x));

//                postInvalidate();
            }

        }

    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
