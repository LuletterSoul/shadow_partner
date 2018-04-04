package com.years.shadow.activity;

import android.os.Bundle;

import com.years.ground_station.R;
//import com.years.ground_station.service.Service_usb;


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
