package com.aoaruche.tw.je.je_fighter_basic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.Bullet_List;
import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.Max_Height;

/**
 * Created by JE-Chen on 2018/4/3.
 */

public class JExBullet_Factroy implements Runnable {

    //執行緒是否開啟 (預設開啟)
    public static boolean Enemy_Bullet_Runnable_Open = true;

    private  boolean Disappear=true;

    //物件的X,Y座標
    private int Object_x, Object_y;

    //要前進的方向
    private String Direction="BOTTOM";
    //前進的速度
    private int Speed;
    //步數 (是否要衝到底)
    private int Step=0;
    //是否衝到底?
    private boolean Run_away=true;
    //物件的寬高
    private int Object_w, Object_h;
    //要繪製的圖片
    private Bitmap Draw_Bitmap;
    //畫筆 (不要畫血條就不用了)
    private Paint paint;

    public JExCollision JExCollision = new JExCollision();

    protected String Whos_Bullet="";

    Rect Bullet_Rect = new Rect();

    public JExBullet_Factroy(Bitmap Draw_Bm, boolean Run_Away, String Direction, int Speed, int Step, int object_x, int object_y, String Whos_Bullet) {

        //設定要畫的  Bitmap 圖 圖 圖 ?
        this.Draw_Bitmap = Draw_Bm;
        //設定是否跑到底
        this.Run_away=Run_Away;

        //設定方向
        this.Direction=Direction;
        //設定速度
        this.Speed=Speed;
        //設定步數
        this.Step=Step;

        this.Object_x=object_x;

        this.Object_y=object_y;

        this.Whos_Bullet=Whos_Bullet;
        //Bitmap圖的參數
        Bitmap_Parameter();

        new Thread(this).start();

    }

    public void Bitmap_Parameter() {
        //取得寬高
        Object_h = Draw_Bitmap.getHeight();
        Object_w = Draw_Bitmap.getWidth();


        //設定畫筆的參數
        paint = new Paint();
        paint.setColor(Color.RED); //設定畫筆顏色
        paint.setStrokeWidth(3);     //畫筆的寬度

        Thread Add_Thread = new Thread(this);
        Add_Thread.start();
        Bullet_List.add(Add_Thread);
    }

    public void  Set_X_Y(int Create_X,int Create_Y){

        this.Object_x=Create_X;
        this.Object_y=Create_Y;
        Bullet_Rect.set(Object_x,Object_y,Object_x+Object_w,Object_y+Object_h);

    }

    //==== 檢視物件是否仍存在著(活著) ====
    protected boolean Is_Alive() {
        return Disappear;
    }

    //將圖畫到 canvas(畫布)上
    protected void Draw_Fighter(Canvas canvas) {

        //在 canvas 上繪出物件本體
        canvas.drawBitmap(this.Draw_Bitmap, Object_x, Object_y, null);

        // 只有Boss要顯示
        /*計算應繪出的血條值長度
                 int hpWidth = (int)( ((float)Current_HP/(float)HP) * (float)Object_w );
                  if (hpWidth <= 0) hpWidth = 0;

                 //繪出血條 (血條繪於物件圖片的上方)
              canvas.drawLine(Object_x, Object_y - 5, Object_x + hpWidth, Object_y - 5, paint);
               */
    }

    protected boolean Is_Collision(int Object_x,int Object_y,int Object_w,int Object_h){

      return   JExCollision.Is_Collision(Object_x,Object_y,Object_w,Object_h,this.Object_x,this.Object_y,
                this.Object_w,this.Object_h);

    }

    protected String Return_Whos_Bullet(){return this.Whos_Bullet;
    }



    protected void Position_Change() {

        //按照移動的方向來改變物件的座標位置

        if (Direction.equals("TOP")) {
            if(Step==0&&Run_away==true) {
                // y 值增加
                Object_y += Speed;
                if (Object_y >= Max_Height) {
                    Disappear=false;
                    Bullet_List.remove(this);
                }
            }
            else if(Step>0){
                Object_y += Speed;
                Step-=1;
                if (Object_y > Max_Height) {
                    Disappear=false;
                    Bullet_List.remove(this);
                }
        }
        }
        //判斷是否超出螢幕範圍(碰到邊界下)


        if (Direction.equals("BOTTOM")) {
            if(Step==0&&Run_away==true) {
                // y 值增加
                Object_y -= Speed;
                if (Object_y <=((Max_Height-Max_Height)-(Object_y*2))){
                    Disappear=false;
                    Bullet_List.remove(this);
                //   Log.wtf("Disappear","Is_Close");
                }
            }
        }else if(Step>0){
            Object_y -= Speed;
            Step-=1;
            if (Object_y >= Max_Height) {
                Disappear=false;
                Bullet_List.remove(this);
            }
        }
        //判斷是否超出螢幕範圍(碰到邊界下)

        Bullet_Rect.set(Object_x,Object_y,Object_x+Object_w,Object_y+Object_h);
    }



    @Override
    public void run() {
        // 執行緒的工作內容 不斷改變物件的座標值，如此才能讓物件在螢幕中移動

            while (Enemy_Bullet_Runnable_Open&&Disappear) {

                Position_Change(); //改變物件座標的運算函式

                try {


                    Thread.sleep(5);

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
    }
}
