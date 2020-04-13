package com.aoaruche.tw.je.je_fighter_basic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import static com.aoaruche.tw.je.je_fighter_basic.JExMain_Fighter_Wings_of_Hope.Main_HP;
import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.Max_Height;
import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.Max_Width;

public class JExMain_Hp_Progress implements Runnable{

    //圖條 x y 座標
    public  int Draw_Progress_One_x, Draw_Progress_One_y,Draw_Progress_Two_x,Draw_Progress_Two_y,
            Draw_Progress_Three_x,Draw_Progress_Three_y,Draw_Progress_Four_x,Draw_Progress_Four_y;

    //主角機血量
    public  int Main_Fighter_Hp = 0;

    public int Main_Max_Hp=0;

    //要繪製的圖片
    private Bitmap Draw_Progress_One,Draw_Progress_Two,Draw_Progress_Three,Draw_Progress_Four;

    //執行緒是否開啟 (預設開啟)
    private boolean Main_Hp_Progress_Run = true;

    private int Run_Time=5;

    //圖層 one 是最底層
    public JExMain_Hp_Progress(Bitmap Draw_Progress_One, Bitmap Draw_Progress_Two, Bitmap Draw_Progress_Three, Bitmap Draw_Progress_Four, int Main_Max_Hp){
        this.Draw_Progress_One=Draw_Progress_One;
        this.Draw_Progress_Two=Draw_Progress_Two;
        this.Draw_Progress_Three=Draw_Progress_Three;
        this.Draw_Progress_Four=Draw_Progress_Four;
        //One  血條背景圖
        Draw_Progress_One_x=0;
        Draw_Progress_One_y=Max_Height;

        //Two 血條藍色部分
        Draw_Progress_Two_x=0;
        Draw_Progress_Two_y=Max_Height;

        //Three 血條灰色部分
        Draw_Progress_Three_x=Max_Width;
        Draw_Progress_Three_y=Max_Height;

        //Four 血條主圖
        Draw_Progress_Four_x=0;
        Draw_Progress_Four_y=Max_Height;

        this.Main_Max_Hp=Main_Max_Hp;
        Thread Hp = new Thread(this);
        Hp.start();

    }

    void Update_X(int Hp_Pre,int Hp_Count){

        int Get_Max_Width=Max_Width;

        int Memory_Max_Width=Max_Width;

        int Max_Width_Pre_count =(Draw_Progress_Two_x*100);//計算百分比用

        // int Now_Pre =(Max_Width_Pre_count/Memory_Max_Width); //百分比 =  原本數值x100 在除與原數值

        int Blue_Width_New_Locate=Max_Width;

        int Gray_Width_New_Locate=0;

        if(Hp_Pre>=1&&Hp_Pre<=100){

            for(int g = 100-Hp_Pre;g<100;g++){
                Blue_Width_New_Locate-=(Memory_Max_Width/100);
                Gray_Width_New_Locate+=(Memory_Max_Width/100);
            }
            Blue_Width_New_Locate=0-Blue_Width_New_Locate;

            if(Hp_Pre==100){
                Blue_Width_New_Locate=0;
                Gray_Width_New_Locate=Max_Width;
            }

        }else if(Hp_Pre==0&&Hp_Count!=0){

            for(int w = 0;w<100;w++){

                Blue_Width_New_Locate-=(Memory_Max_Width/100);
                Gray_Width_New_Locate+=(Memory_Max_Width/100);
            }
            Blue_Width_New_Locate=0-Blue_Width_New_Locate;
        }else if(Hp_Pre==0&&Hp_Count==0){
            Blue_Width_New_Locate=0;
            Gray_Width_New_Locate=Max_Width;
        }


        Draw_Progress_Two_x=Blue_Width_New_Locate;
        Draw_Progress_Three_x=Gray_Width_New_Locate;

    }

    void Update_Hp(int Now_Hp){

        int Get_Main_Max_Hp=Main_Max_Hp;

        int Memory_Main_Max_Hp=Main_Max_Hp;

        int Main_Max_Hp_Pre_count =(Now_Hp*100);//計算百分比用

        int Hp_count=Main_Fighter_Hp;

        int Now_Pre =(Main_Max_Hp_Pre_count/Memory_Main_Max_Hp); //百分比 =  原本數值x100 在除與原數值

        if(Now_Pre==0&&Hp_count!=0){
            Now_Pre=1;
        }

        Update_X(Now_Pre,Main_HP);
        if(Main_Fighter_Hp==0){
            Main_Hp_Progress_Run=false;
        }
    }

    protected void Draw_himself(Canvas canvas){
        canvas.drawBitmap(this.Draw_Progress_One, Draw_Progress_One_x, Draw_Progress_One_y, null);
        canvas.drawBitmap(this.Draw_Progress_Two, Draw_Progress_Two_x, Draw_Progress_Two_y, null);
        canvas.drawBitmap(this.Draw_Progress_Three, Draw_Progress_Three_x, Draw_Progress_Three_y, null);
        canvas.drawBitmap(this.Draw_Progress_Four, Draw_Progress_Four_x, Draw_Progress_Four_y, null);
    }

    @Override
    public void run() {

        while (Main_Hp_Progress_Run) {


            try {
                Main_Fighter_Hp=Main_HP;
                Update_Hp(Main_Fighter_Hp);
                Thread.sleep(Run_Time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}

