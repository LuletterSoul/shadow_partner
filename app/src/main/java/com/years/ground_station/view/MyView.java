package com.years.ground_station.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.years.ground_station.core.services.AbstractUSBHIDService;

/**
 * Created by zhumingqing on 2017/7/9.
 */

/*
height = 1118
width = 720
 */

public class MyView extends View {

    private final static String stringX[]={"-250","-200","-150","-100","-50","0","50","100","150","200","250"};
    private final static String stringY[]={"-500","-450","-400","-350","-300","-250","-200","-150", "-100","-50",
            "0","50","100","150","200","250","300","350","400","450","500"};

    private float Height,Width;              //宽高
    private float OrignX,OrignY;             //原点坐标
    private float PadX,PadY;                 //边距
    private float AverageX=11,AverageY=21;   //坐标均分
    private float DistanceX,DistanceY;         //均分距离
    private float intx ,inty;

    public static float x,y;

    private Paint paint_x,paint_y;
    private Paint paint_line,paint_circle,paint_axis;
    private Paint paint_1;

    private final static int DEFAULT_AXIS_LINE_COLOR =0xff000000;   //坐标轴颜色
    private final static int DEFAULT_AXIS_TEXT_COLOR =0xfff74d30;   //坐标轴字体颜色
    private final static int DEFAULT_AXIS_GRID_COLOR =0xf0157efb;   //网格颜色



    public MyView(Context context, AttributeSet attr) {
        super(context,attr);
        paintInit();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

                    Log.w("thread is run "," sleep 500");

                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    myhandler.sendEmptyMessage(0);
                    Log.i("show in "," handler");
                }
            }
        }).start();

    }



    private void paintInit(){
        paint_axis=new Paint();
        paint_axis.setStrokeWidth(5);
        paint_line=new Paint();
        paint_line.setStrokeWidth(2);
        paint_circle=new Paint();
        paint_circle.setStrokeWidth(20);
        paint_1=new Paint();
        paint_1.setStrokeWidth(3);

        x=360;
        y=559;

    }

    private void getMeasure(){
        if (Height==0||Width==0){
            Height=getHeight();
            Width=getWidth();
            if (Height==0||Width==0)
                return;
        }

        PadX=Width/10;
        PadY=Height/20;
        OrignX=PadX;
        OrignY=Height-PadY;
        DistanceX=(Width-PadX*2)/(AverageX-1);
        DistanceY=(Height-PadY*2)/(AverageY-1);
        intx=Width/2;
        inty=Height/2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        getMeasure();
        paint_axis.setColor(DEFAULT_AXIS_LINE_COLOR);
        canvas.drawLine(0,inty,Width,inty,paint_axis);
        canvas.drawLine(intx,Height,intx,0,paint_axis);
//        canvas.drawLine(OrignX, OrignY, Width, OrignY, paint_axis);                   //X轴
//        canvas.drawLine(OrignX, OrignY, OrignX, 0, paint_axis);                       //Y轴
        float temp;
        int i;
        paint_circle.setColor(DEFAULT_AXIS_TEXT_COLOR);
        paint_circle.setTextSize(sp2px(15));
        paint_line.setColor(DEFAULT_AXIS_GRID_COLOR);
        for (i=1;i<AverageX;i++){                                                     //X轴标注网格
            temp=OrignX+DistanceX*i;
            canvas.drawLine(temp,OrignY,temp,PadY-10,paint_line);
            canvas.drawText(stringX[i], (float) (temp-sp2px(20)*0.5), (float) (OrignY+sp2px(20)*0.75),paint_circle);
        }
        for (i=0;i<AverageY;i++){                                                    //Y轴标注网格
            temp=OrignY-DistanceY*i;
            canvas.drawLine(OrignX,temp,Width-PadX+10,temp,paint_line);
            canvas.drawText(stringY[i], (float) (OrignX-stringY[i].length()*sp2px(20)/2.5), (float) (temp+sp2px(20)*0.25),paint_circle);
        }

        canvas.drawCircle(x,y,15,paint_circle);    //画点

        super.onDraw(canvas);
    }

    private int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

        private int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpVal,getResources().getDisplayMetrics());
    }

    Handler myhandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                x= (float) (AbstractUSBHIDService.x*100)+360;
                y=559-((float)AbstractUSBHIDService.y*100);
//                y=(float)(AbstractUSBHIDService.y*100)+559;
//                Log.i("intx is ", double.toString(x));
//                Log.i("inty is ", double.toString(y));

                MyView.this.invalidate();

//                postInvalidate();
            }

        }

    };

//    public void move(){
//
//        new Thread(movethread).start();
//    }

//    private final Runnable movethread = new Runnable() {
//        @Override
//        public void run() {
//            //change  position of point
//            while(true){
//            try {
//                Thread.sleep(300);
//                if (intx<Width){
//                    intx +=DistanceX;
//                }
//                else {
//                    intx=Width/2;
//                }
//
//                if(inty>0){
//                    inty -=DistanceY;
//                }
//                else {
//                    inty=Height/2;
//                }
//
//                Log.i("intx is ",Float.toString(intx));
//                Log.i("inty is ",Float.toString(inty));
//
//                Log.i(" do sleep ","500");
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//            postInvalidate();}
//
//        }
//    };

}
