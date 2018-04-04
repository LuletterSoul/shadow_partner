package com.years.shadow.track;

/**
 * Created by zhumingqing on 2017/7/1.
 */


public class Track {
//    typedef struct tagWHEEL_SPEED
//    {
//        int left;  //range from 1 to 1000
//        int right; //range from 1 to 1000
//    }WHEEL_SPEED;

    static double X_COOR=0.33;
    static double Y_COOR=0.33;
    //double x_delta,y_delta;
//    public static Node set_speed(double d0_cur,double d1_cur,double d2_cur,//current position,(m)
//                   double d0_tar,double d1_tar,double d2_tar//target position,(m)
//                   )
//    {
//        Node node = new Node();
//        double x_cur,y_cur;//current location of human
//        double x_tar,y_tar;//target location of human
////        double x_delta,y_delta;// diff of two locations
//        //initialize
//        x_cur = x_tar  = y_cur = y_tar =  0;
////        speed.left= speed.right = 0;
//        //compute current location of human
//        x_cur = -(d1_cur*d1_cur-d0_cur*d0_cur-X_COOR*X_COOR)/(2*X_COOR);
//        y_cur = -(d2_cur*d2_cur-d0_cur*d0_cur-Y_COOR*Y_COOR)/(2*Y_COOR);
//        //compute target location of human
//        x_tar = -(d1_tar*d1_tar-d0_tar*d0_tar-X_COOR*X_COOR)/(2*X_COOR);
//        y_tar = -(d2_tar*d2_tar-d0_tar*d0_tar-Y_COOR*Y_COOR)/(2*Y_COOR);
//        //compute the diff
//        node.x = x_cur;
//        node.y = y_cur;
//        return node;
//
//
//    }

    public static Node get_speed(double d0_cur,double d1_cur,double d2_cur,double x_tar,double y_tar){
        double x_cur,y_cur;//current location of human
        //double x_tar,y_tar;//target location of human
        double x_delta,y_delta;// diff of two locations
        Node node=new Node();
        //initialize
        x_cur = x_tar = x_delta = y_cur = y_tar = y_delta = 0;
        node.left= node.right = 0;
        //compute current location of human
        x_cur = -(d1_cur*d1_cur-d0_cur*d0_cur-X_COOR*X_COOR)/(2*X_COOR);
        y_cur = -(d2_cur*d2_cur-d0_cur*d0_cur-Y_COOR*Y_COOR)/(2*Y_COOR);

        node.x = x_cur;
        node.y = y_cur;

        //compute the diff
        x_delta = x_tar+x_cur;
        y_delta = y_tar+y_cur;


        //compute the speed
       if (x_delta>0.30) {
            node.left = (int) ((x_delta -0.1) * 500);
            node.right = 0;
        }
        else if(x_delta<-0.2) {
            node.left = 0;
            node.right = (int) (-x_delta * 500);
        }
        if (y_delta>1.0)
        {
            node.left += (int)(y_delta*100);
            node.right += (int)(y_delta*100);
        }
//        else speed.left= speed.right = 0;
        //protection
        if (node.left > 500)
            node.left = 500;
        if (node.right > 500)
            node.right = 500;

      /*  //test
        if (x_delta > 0.3){
            speed.left = 500;
            speed.right = 0;
        }
        else if (x_delta < -0.3){
            speed.left = 0;
            speed.right = 500;
        }
        else if (y_delta>0.5){
            speed.left = 300;
            speed.right = 300;
        }
        else if (y_delta < 0.3){
            speed.left = 0;
            speed.right = 0;
        }
*/
        return node;

    }
}
