package com.aoaruche.tw.je.je_fighter_basic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.Enemy_List;
import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.Main;
import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.Max_Height;
import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.Max_Width;
import static com.aoaruche.tw.je.je_fighter_basic.Start_Sense_SurfaceView.Start_Sense_Main_Array_List;
import static com.aoaruche.tw.je.je_fighter_basic.Start_Sense_SurfaceView.Start_Sense_Main_Bullet_List;

/**
 * Created by JE-Chen on 2018/3/11.
 */

public class JE_Fight_Fighter_Enemy_Factory implements Runnable {

    //執行緒是否開啟 (預設開啟)
     public static boolean Enemy_Runnable_Open = true;

    public  boolean Enemy_Hp_Not_Zero=true;

    //物件的X,Y座標
    public int Object_x, Object_y;
    //要前進的方向
    public String Direction="TOP";
    //前進的速度
    public int speed;

    public int Max_Speed;

    public int Min_Speed;

    public static String Fighter_Type="null";

    public int Number = 0;

    //步數 (是否要衝到底)
    public int step=0;
    //是否衝到底?
    public boolean Run_away=true;
    //血量
    public  int Enemy_HP = 100;
    //目前的血條值
    public int Current_HP;
    //物件的寬高
    public int Object_w, Object_h;
    //要繪製的圖片
    public Bitmap Draw_Bitmap;
    //畫筆 (不要畫血條就不用了)
    public Paint paint;

    public int Run_Time=5;

    public static boolean Have_AI=false;

    public JExCollision JExCollision =new JExCollision();

    public boolean Separation=false;

    public JExEnemy_AI I_Owner_AI;

    Rect Collision_Rect = new Rect();


    public JE_Fight_Fighter_Enemy_Factory(Bitmap Draw_Bm, boolean Run_Away,int HP,String Direction,int speed,int step,int Max_Speed,int Min_Speed,String Fighter_Type,int Number) {

        //設定要畫的  Bitmap 圖 圖 圖 ?
        this.Draw_Bitmap = Draw_Bm;
        //設定是否跑到底
        this.Run_away=Run_Away;
        //設定血量
        this.Enemy_HP=HP;
        //設定方向
        this.Direction=Direction;
        //設定速度
        this.speed=speed;

        this.Max_Speed=Max_Speed;

        this.Min_Speed=Min_Speed;

        this.Fighter_Type=Fighter_Type;

        this.Number=Number;

        //設定步數
        this.step=step;
        
        //Bitmap圖的參數
        Bitmap_Parameter();

            if(Main!=null) {
                I_Owner_AI = new JExEnemy_AI(this, Main.get(0));
                Have_AI = true;
            }
            if(Start_Sense_Main_Array_List!=null) {
                I_Owner_AI = new JExEnemy_AI(this, Start_Sense_Main_Array_List.get(0), 1);
                Have_AI = true;
            }
            this.step=((Math.abs(Object_y)+Object_h));
            Run_away=false;

       Thread Add_Thread = new Thread(this);
        Add_Thread.start();
        Enemy_List.add(Add_Thread);


    }

    public void Bitmap_Parameter() {
        //取得寬高
        Object_h = Draw_Bitmap.getHeight();
        Object_w = Draw_Bitmap.getWidth();
        // 血條值 <可以不用做  {除了Boss}  >
        Current_HP = Enemy_HP;

        if(Direction.equals("TOP")) {
            Object_x = (int) (Math.random() * (Max_Width - Object_w));
            Object_y = 0- (Object_h);
            Log.wtf("JE_Fighter_Enemy_Factory",String.valueOf(Object_y));
        }

        //設定畫筆的參數
        paint = new Paint();
        paint.setColor(Color.RED); //設定畫筆顏色
        paint.setStrokeWidth(3);     //畫筆的寬度

        Collision_Rect.set(Object_x,Object_y,Object_x+Object_w,Object_y+Object_h);
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

    protected boolean Have_AI(){

        return this.Have_AI;
    }

    protected boolean Is_Collision(int Object_x,int Object_y,int Object_w,int Object_h){

      return  JExCollision.Is_Collision(Object_x,Object_y,Object_w,Object_h,this.Object_x,this.Object_y,this.Object_w,this.Object_h);

    }

    protected void Position_Change() {

        //按照移動的方向來改變物件的座標位置
        if (Direction.equals("TOP")) {
            if(step==0&&Run_away==true) {
                // y 值增加
                Object_y += speed;
                if (Object_y >= Max_Height||Enemy_HP<=0) {
                    Enemy_Hp_Not_Zero=false;
                    Enemy_List.remove(this);
                    Log.wtf("Enemy_List","Enemy_List Remove\t"+String.valueOf(this));
                }
            }
            else if(step>0){
                Object_y += speed;
                step-=1;
                if (Object_y >= Max_Height||Enemy_HP<=0) {
                    Enemy_Hp_Not_Zero=false;
                    Enemy_List.remove(this);
                    Log.wtf("Enemy_List","Enemy_List Remove\t"+String.valueOf(this));
                }
            }

        }
        //判斷是否超出螢幕範圍(碰到邊界下)


        Collision_Rect.set(Object_x,Object_y,Object_x+Object_w,Object_y+Object_h);
    }


    //==== 檢視物件是否仍存在著(活著) ====
    protected boolean Is_Alive() {
        return Enemy_Hp_Not_Zero;
    }

    protected int Get_Number(){
        return this.Number;
    }

    @Override
    public void run() {

        // 執行緒的工作內容 不斷改變物件的座標值，如此才能讓物件在螢幕中移動



            while (Enemy_Runnable_Open&&Enemy_Hp_Not_Zero) {

                Position_Change(); //改變物件座標的運算函式
                if(Have_AI) {
                    I_Owner_AI.AI_Think();
                }

                if(this.Object_x<0)
                    Object_x=0;
                if(this.Object_x+this.Object_w>Max_Width)
                    Object_x=(Max_Width-this.Object_w);
                if(Enemy_HP<=0)
                    Enemy_Hp_Not_Zero=false;
                try {


                    Thread.sleep(Run_Time); // 預設 5

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        }


}



