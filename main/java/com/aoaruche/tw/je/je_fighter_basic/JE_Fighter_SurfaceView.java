package com.aoaruche.tw.je.je_fighter_basic;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import static com.aoaruche.tw.je.je_fighter_basic.JExBitmap.getFitSampleBitmap;
import static com.aoaruche.tw.je.je_fighter_basic.JExMain_Fighter_Wings_of_Hope.Main_HP;
import static com.aoaruche.tw.je.je_fighter_basic.JExMain_Fighter_Wings_of_Hope.Main_Object_h;
import static com.aoaruche.tw.je.je_fighter_basic.JExMain_Fighter_Wings_of_Hope.Main_Object_w;
import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.BF;
import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.Enemy_Type_1;
import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.Main;
import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.Max_Height;
import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.Max_Width;

/**
 * Created by JE-Chen on 2018/3/12.
 */

public class JE_Fighter_SurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable{


    public static boolean Runnable_Running=true;
    public  Canvas canvas=null;
    public static Thread Game_Main_Thread;
    public  Surface Main_Surface;
    private Resources res;


    public  Bitmap Draw_Enemy;
    public  Bitmap Draw_Main;
    public  Bitmap Draw_Main_Bullet;
    public  Bitmap Draw_Normal_Enemy_Bullet;
    public  Bitmap Draw_Background;
    public  Bitmap Draw_Progress_Main_HP_1,Draw_Progress_Main_HP_2,Draw_Progress_Main_HP_3,Draw_Progress_Main_HP_4;

    public  SurfaceHolder holder;
    public  boolean Main_Touch=false;



    public JExMain_Hp_Progress Hp_Progress;


    private int Bullet_Time=0;
    private int Enemy_Bullet_Time=0;

    public JE_Fighter_SurfaceView(Context context) {
        super(context);

        //Fixme  正確取得圖片
       // Draw_Thing =getFitSampleBitmap();
        res = getResources();
        Draw_Enemy = BitmapFactory.decodeResource(res, R.drawable.enemy_type_1);
        Draw_Main = BitmapFactory.decodeResource(res, R.drawable.air_01_blue);
        Draw_Main_Bullet=BitmapFactory.decodeResource(res, R.drawable.bullet_length);
        Draw_Background=BitmapFactory.decodeResource(res, R.drawable.back_ground);
        Draw_Normal_Enemy_Bullet=BitmapFactory.decodeResource(res, R.drawable.bullet_round);
        Draw_Progress_Main_HP_1=BitmapFactory.decodeResource(res, R.drawable.system_box_1);
        Draw_Progress_Main_HP_2=BitmapFactory.decodeResource(res, R.drawable.system_box_2);
        Draw_Progress_Main_HP_3=BitmapFactory.decodeResource(res, R.drawable.system_box_3);
        Draw_Progress_Main_HP_4=BitmapFactory.decodeResource(res, R.drawable.system_box_4);
        Draw_Progress_Main_HP_1=Bitmap.createScaledBitmap(Draw_Progress_Main_HP_1,Max_Width,100,true);
        Draw_Progress_Main_HP_2=Bitmap.createScaledBitmap(Draw_Progress_Main_HP_2,Max_Width,100,true);
        Draw_Progress_Main_HP_3=Bitmap.createScaledBitmap(Draw_Progress_Main_HP_3,Max_Width,100,true);
        Draw_Progress_Main_HP_4=Bitmap.createScaledBitmap(Draw_Progress_Main_HP_4,Max_Width,100,true);
        Draw_Background=Bitmap.createScaledBitmap(Draw_Background,Max_Width,Max_Height,true);

        InitialSet();

        getHolder().addCallback(this);

        holder = getHolder();

        Main_Surface=holder.getSurface();

        Game_Main_Thread=new Thread(this);

    }


    //==== 初始設定 ====
    private void InitialSet() {


       Main=new ArrayList<JExMain_Fighter_Wings_of_Hope>();

        Main.clear();

        JExMain_Fighter_Wings_of_Hope one = new JExMain_Fighter_Wings_of_Hope(Draw_Main,1500);

        Main.add(one);

        //建立 Enemy_Type_1 物件陣列實體

        BF = new ArrayList<JExBullet_Factroy>();
        BF.clear();





        Enemy_Type_1 = new ArrayList<JE_Fight_Fighter_Enemy_Factory>();
        Enemy_Type_1.clear();  //先清除 Enemy_Type_1 物件陣列

        //建立 Enemy_Type_1 物件 3 隻
        for(int i=0; i<1; i++) {
            //產生 Enemy_Type_1 實體 au
            JE_Fight_Fighter_Enemy_Factory au = new JE_Fight_Fighter_Enemy_Factory(Draw_Enemy,true,10,"TOP",1,0,3,1,"Normal",i);
            //陸續將 au 放入 Enemy_Type_1 物件陣列中
            Enemy_Type_1.add(au);
        }

        Hp_Progress=new JExMain_Hp_Progress(Draw_Progress_Main_HP_4,Draw_Progress_Main_HP_3,Draw_Progress_Main_HP_2,Draw_Progress_Main_HP_1,Main_HP);
    }


    //==== 加入觸碰事件方法 ====
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(Main!=null) {

            int What_Main = 0;


            for (int i = 0; i < Main.size(); i++) {
                //若該物件還活著，則呼叫  物件的 Draw_Fighter() 方法
                //將物件圖片繪至 canvas 上
                What_Main = i;
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                Main_Touch = Main.get(What_Main).Is_Touch(x, y);
                if(Main_Touch){
                  //  Main.get(0).set_Main_Object_x_and_Main_Object_y(x, y);
                }
              //  Log.wtf("TAG", String.valueOf(Main_Touch));
            }

            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                int movex = (int) event.getRawX();
                int movey = (int) event.getRawY();
               // movex-=  Main.get(0).Return_W();
               // movey-=  Main.get(0).Return_H();
                for (int i = 0; i < Main.size(); i++) {
                    //若該物件還活著，則呼叫  物件的 Draw_Fighter() 方法
                    //將物件圖片繪至 canvas 上
                    if (Main_Touch) {
                        if (Main.get(i).Is_Alive())
                            Main.get(i).set_Main_Object_x_and_Main_Object_y(movex, movey);

                      //  Log.wtf("TAG", "IT's True Touch Main RUN");
                    }
                }
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                Main_Touch = false;
            }
        }
        return true;
    }




    @Override
    public void run() {



            while (Runnable_Running) {
                //將物件顯示到螢幕上
                boolean Enemy_Bullet_Shoot=false;

                try {
                    Thread.sleep(5);
                    //取得並鎖住畫布(canvas)


                    canvas = holder.lockCanvas();

                    //清除畫面
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    //畫出背景
                    canvas.drawBitmap(Draw_Background, 0f, 0f, null);

                    Enemy_Bullet_Time+=5;

                    if(Enemy_Bullet_Time==250){

                         Enemy_Bullet_Shoot=true;

                    }

                    if (!Enemy_Type_1.isEmpty()) {

                        for (int i = 0; i < Enemy_Type_1.size(); i++) {
                            //若該物件還活著，則呼叫  物件的 Draw_Fighter() 方法
                            //將物件圖片繪至 canvas 上

                           if(Enemy_Bullet_Shoot){
                               for (int e = 0; e < Enemy_Type_1.size(); e++) {
                                   JExBullet_Factroy Enemy_Bu_1 = new JExBullet_Factroy(Draw_Normal_Enemy_Bullet, true, "TOP", 2,
                                           0, (Enemy_Type_1.get(e).Object_x + (Enemy_Type_1.get(e).Object_w/2)),
                                           Enemy_Type_1.get(e).Object_y + Enemy_Type_1.get(e).Object_h, "Enemy");
                                   Enemy_Bu_1.Set_X_Y(Enemy_Type_1.get(e).Object_x, Enemy_Type_1.get(e).Object_y);
                                   BF.add(Enemy_Bu_1);
                               }
                               Enemy_Bullet_Shoot=false;
                               Enemy_Bullet_Time=0;
                           }

                            if(!BF.isEmpty()) {
                                for (int a = 0; a < BF.size(); a++) {
                                    if (BF.get(a).Return_Whos_Bullet().equals("Main")) {
                                        if (BF.get(a).Is_Collision(Enemy_Type_1.get(i).Object_x, Enemy_Type_1.get(i).Object_y,
                                                Enemy_Type_1.get(i).Object_w, Enemy_Type_1.get(i).Object_h)) {
                                            Log.wtf("Enemy_Collision", "Main_Bullet");
                                            BF.remove(a);
                                            Enemy_Type_1.get(i).Enemy_HP-=10;
                                        }
                                    }
                                }
                            }
                            if (Enemy_Type_1.get(i).Is_Alive())
                                Enemy_Type_1.get(i).Draw_Fighter(canvas);
                        }

                    }
                    //從 Enemy_Type_1 物件陣列中移除已經停止活動的物件
                    if (!Enemy_Type_1.isEmpty()) {
                        for (int i = 0; i < Enemy_Type_1.size(); i++) {
                            //刪除已經死亡的物件
                            if (!Enemy_Type_1.get(i).Is_Alive()){
                                Enemy_Type_1.remove(i);

                            }
                        }
                    }

                    if(Enemy_Type_1.isEmpty()){

                        for(int i=0; i<1; i++) {
                            //產生 Enemy_Type_1 實體 au
                            JE_Fight_Fighter_Enemy_Factory au = new JE_Fight_Fighter_Enemy_Factory(Draw_Enemy,true,10,"TOP",1,0,3,1,"Normal",i);
                            //陸續將 au 放入 Enemy_Type_1 物件陣列中
                            Enemy_Type_1.add(au);

                        }
                    }

                    if (!Main.isEmpty()) {
                        for (int i = 0; i < Main.size(); i++) {
                            //若該物件還活著，則呼叫  物件的 Draw_Fighter() 方法
                            //將物件圖片繪至 canvas 上
                            if (Main.get(i).Is_Alive()) {
                                Main.get(i).Draw_Fighter(canvas);
                                Hp_Progress.Draw_himself(canvas);
                                int movex = (Main.get(i).Return_X()+((Main.get(i).Return_W()/2)-5));
                                int movey = (Main.get(i).Return_Y()-(Main.get(i).Return_H()/2));
                                if(Bullet_Time!=250) {
                                    Bullet_Time += 5;
                                }else if(Bullet_Time==250) {
                                    JExBullet_Factroy Main_Bu_1 = new JExBullet_Factroy(Draw_Main_Bullet, true, "BOTTOM", 2,
                                            0,Main.get(0).Return_X(),Main.get(0).Return_Y_Add_H(),"Main");
                                    Main_Bu_1.Set_X_Y(movex, movey);
                                    BF.add(Main_Bu_1);

                                    Bullet_Time=0;

                                }
                                if(!BF.isEmpty()) {
                                    for (int a = 0; a < BF.size(); a++) {
                                        if (BF.get(a).Return_Whos_Bullet().equals("Enemy")) {
                                            if (BF.get(a).Is_Collision(Main.get(0).Return_X(), Main.get(0).Return_Y(),
                                                    Main.get(0).Return_W(), Main.get(0).Return_H())) {
                                                Log.wtf("Main_Collision", "Enemy_Bullet");
                                                BF.remove(a);
                                                if(Main.get(0).Main_HP>=100){
                                                    Main.get(0).Main_HP-=10;
                                                }

                                            }
                                        }
                                    }
                                }

                                for (int a = 0; a < Enemy_Type_1.size(); a++) {
                                    Enemy_Type_1.get(a).Is_Collision(movex, movey, Main_Object_w, Main_Object_h);
                                }

                            }
                        }

                    }
                    if (!Main.isEmpty()) {
                        for (int i = 0; i < Main.size(); i++) {
                            //刪除已經死亡的物件
                            if (!Main.get(i).Is_Alive()) Main.remove(i);
                        }
                    }

                    if(!BF.isEmpty()){
                        for (int i=0;i<BF.size();i++){
                            if(BF.get(i).Is_Alive())BF.get(i).Draw_Fighter(canvas);
                        }

                    }
                    if (!BF.isEmpty()) {
                        for (int i = 0; i < BF.size(); i++) {
                            //刪除已經死亡的物件
                            if (!BF.get(i).Is_Alive()){
                                BF.remove(i);
                            }
                          //  Log.wtf("BF_Is_Dead",String.valueOf(BF.get(i)));
                        }
                    }

// Log.wtf("Is_Running?","Is_Running");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        //解鎖畫布(canvas)並顯示到螢幕上

                        holder.unlockCanvasAndPost(canvas);

                    }
                }
            } //while
        }



    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        if (Game_Main_Thread.getState() == Thread.State.NEW) {
            Game_Main_Thread.start();
        }else {
            Game_Main_Thread = new Thread(this);
            Game_Main_Thread.start();
        }

    }
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }




}
