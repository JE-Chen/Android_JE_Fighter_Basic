package com.aoaruche.tw.je.je_fighter_basic;

/**
 * Created by JE-Chen on 2018/4/1.
 */
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.Max_Height;
import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.Max_Width;

public  class JExMain_Fighter_Wings_of_Hope implements Runnable{

    //主角機 x y 座標
    public static int Main_Object_x, Main_Object_y;
    //主角機的寬高
    public static int Main_Object_w, Main_Object_h;
    //執行緒是否開啟 (預設開啟)
    private boolean Main_Fighter_Runnable_Open = true;
    //主角機血量
    public static int Main_HP = 500;
    //要繪製的圖片
    private Bitmap Draw_Bitmap;
    //畫筆 (不要畫血條就不用了)
    private Paint paint;

    protected int Run_Time=5;

    private JExCollision JExCollision =new JExCollision();

    //
    Rect Main_Rect = new Rect();


    public JExMain_Fighter_Wings_of_Hope(Bitmap Draw, int Hp) {

        this.Main_HP=Hp;

        this.Draw_Bitmap=Draw;

        Bitmap_Parameter();



    }

    public void Bitmap_Parameter() {
        //取得寬高
        Main_Object_h = Draw_Bitmap.getHeight();
        Main_Object_w = Draw_Bitmap.getWidth();

        Main_Object_x=(int)((Max_Width-Main_Object_w)*0.5);
        Main_Object_y= Max_Height-Main_Object_h;

        //設定畫筆的參數
        paint = new Paint();
        paint.setColor(Color.RED); //設定畫筆顏色
        paint.setStrokeWidth(3);     //畫筆的寬度
        Main_Rect.set(Main_Object_x, Main_Object_y, Main_Object_x + Main_Object_w, Main_Object_y + Main_Object_h);
    }

    protected int Return_X(){
        return this.Main_Object_x;
    }

    protected int Return_Y(){
        return this.Main_Object_y;
    }

    protected int Return_W(){
        return this.Main_Object_w;
    }

    protected int Return_H(){
        return this.Main_Object_h;
    }

    protected int Return_X_Add_W(){
        return (this.Main_Object_x+this.Main_Object_w);
    }
    protected int Return_X_Reduce_W(){
        return (this.Main_Object_x-this.Main_Object_w);
    }
    protected int Return_Y_Add_H(){
        return (this.Main_Object_y+this.Main_Object_h);
    }
    protected int Return_Y_Reduce_H(){
        return (this.Main_Object_y-this.Main_Object_h);
    }
    //==== 檢視物件是否仍存在著(活著) ====
    protected boolean Is_Alive() {
        return Main_Fighter_Runnable_Open;
    }

    //將圖畫到 canvas(畫布)上
    protected void Draw_Fighter(Canvas canvas) {

        //在 canvas 上繪出物件本體
        canvas.drawBitmap(this.Draw_Bitmap, Main_Object_x, Main_Object_y, null);

    }

    protected void set_Main_Object_x_and_Main_Object_y(int x, int y){
        if(x>Max_Width-Main_Object_w){
            x=Max_Width-Main_Object_w;
        }
        if(y>Max_Height-Main_Object_h){
            y=Max_Height-Main_Object_h;
        }
     this.Main_Object_x=x;
     this.Main_Object_y=y;
     Main_Rect.set(Main_Object_x, Main_Object_y, Main_Object_x + Main_Object_w, Main_Object_y + Main_Object_h);
    }


    protected boolean Is_Collision(int Object_x,int Object_y,int Object_w,int Object_h){

        return   JExCollision.Is_Collision(Object_x,Object_y,Object_w,Object_h,this.Main_Object_x,this.Main_Object_y,
                this.Main_Object_w,this.Main_Object_h);

    }


    protected boolean Is_Touch(int x, int y){

        boolean main_is_touch=false;

        if(Main_Rect.contains(x,y)){

            main_is_touch=true;

          //  Log.wtf("TAG","IT's True Touch Main");

        }
       return main_is_touch;
    }



    @Override
    public void run() {

        try {

        if(Main_Fighter_Runnable_Open) {
            Thread.sleep(Run_Time); // 預設 5
            if (this.Main_Object_x < 0)
                Main_Object_x = 0;
            if (this.Main_Object_x + this.Main_Object_w > Max_Width)
                Main_Object_x = (Max_Width - this.Main_Object_w);
            if (Main_Object_y + this.Main_Object_h > Max_Height)
                Main_Object_y = Max_Height;
            if (Main_Object_y < 0)
                Main_Object_y = 0;
            }
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    }



