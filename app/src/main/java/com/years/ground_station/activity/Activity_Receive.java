package com.years.ground_station.activity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.years.ground_station.R;
import com.years.ground_station.adapter.EventUtil;
//import com.years.ground_station.service.Service_usb;
import com.years.ground_station.widget.sp.SharedPreferencesSave;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

//import static com.years.ground_station.R.id.tv_receive;

public class Activity_Receive extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
