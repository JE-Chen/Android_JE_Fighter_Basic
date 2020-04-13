package com.aoaruche.tw.je.je_fighter_basic;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class JE_Death_Animation implements Runnable {


    protected int Bomb_x,Bomb_y;

    protected boolean Bomb_Is_Running = true;

    protected String Choice_Bomb="";

    public int Change_Time = 150;

    protected Bitmap Death_Show;
    protected Bitmap Death_2;
    protected Bitmap Death_3;
    protected Bitmap Death_4;

    protected int Change_Counter=0;

    public JE_Death_Animation(int Object_x, int Object_y, Bitmap Death_1, String What_Bomb,int Change_Time){

        this.Bomb_x=Object_x;
        this.Bomb_y=Object_y;
        this.Choice_Bomb=What_Bomb;
        this.Change_Time=Change_Time;
        Death_Show=Death_1;


        new Thread(this).start();

    }



    protected void Draw_Bomb(Canvas canvas){
        canvas.drawBitmap(Death_Show,Bomb_x,Bomb_y,null);
    }

    protected boolean Bomb_Is_Alive(){
        return this.Bomb_Is_Running;
    }

    protected void Play_Animation(){
       if(Change_Counter==1)
           Death_Show=Death_2;
       if(Change_Counter==2)
           Death_Show=Death_3;
       if(Change_Counter==3) {
           Death_Show = Death_4;
           Change_Counter = 0;
           Bomb_Is_Running=false;
       }
    }

    @Override
    public void run() {


        while (Bomb_Is_Running) {


            try {

                Thread.sleep(Change_Time);
                Play_Animation();
                Change_Counter+=1;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
