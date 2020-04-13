package com.aoaruche.tw.je.je_fighter_basic;

import android.util.Log;

public class JExCollision {

    //矩形碰撞
    public boolean Is_Collision(int Object_x,int Object_y,int Object_w,int Object_h,
    int Collision_x,int Collision_y,int Collision_w,int Collision_h){

        boolean Collision = false;

        if(Object_x<(Collision_x+Collision_w)&&Object_y<(Collision_y+Collision_h)
           &&(Collision_x<Object_x+Object_w)&&Collision_y<(Object_y+Object_h)){

            // 寬
            double w =(Object_x+Object_w)-Collision_x;
            //'長
            double h =(Object_y+Object_h)-Collision_y;
            // 重疊面積超過20%
            if(w*h/(Collision_w*Collision_h)>=0.20) {
                Collision = true;

            }
        }

        return Collision;
    }

    public boolean Is_Collision_AI(int Object_x,int Object_y,int Object_w,int Object_h,
                                int Collision_x,int Collision_y,int Collision_w,int Collision_h){

        boolean Collision = false;

        if(Object_x<(Collision_x+Collision_w)&&Object_y<(Collision_y+Collision_h)
                &&(Collision_x<Object_x+Object_w)&&Collision_y<(Object_y+Object_h)){


                Collision = true;

        }

        return Collision;
    }

}
