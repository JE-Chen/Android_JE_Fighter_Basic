package com.aoaruche.tw.je.je_fighter_basic;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import static com.aoaruche.tw.je.je_fighter_basic.JExMain_Fighter_Wings_of_Hope.Main_Object_h;
import static com.aoaruche.tw.je.je_fighter_basic.JExMain_Fighter_Wings_of_Hope.Main_Object_w;

public class Start_Sense_SurfaceView extends SurfaceView implements Runnable,SurfaceHolder.Callback {
    private  SurfaceHolder holder;
    private Surface Start_Sense_holder;
    public static Thread Start_Sense_Thread;
    public static boolean Start_Sense_Running=true;
    private Canvas canvas=null;
    private Resources res;

    public static  ArrayList<JExMain_Fighter_Wings_of_Hope> Start_Sense_Main_Array_List;
    public static  ArrayList<JE_Fight_Fighter_Enemy_Factory> Start_Sense_Enemy_Array_List;
    public static  ArrayList<JExBullet_Factroy> Start_Sense_Main_Bullet_List;

    private int Bullet_Time=0;
    private int Enemy_Bullet_Time=0;
    private boolean Enemy_Bullet_Shoot=false;

    private Bitmap Start_Sense_Background;
    private Bitmap Start_Sense_Main;
    private Bitmap Start_Sense_Enemy;
    private Bitmap Start_Sense_Bullet;

    public Start_Sense_SurfaceView(Context context) {
        super(context);
        res = getResources();
        getHolder().addCallback(this);
        this.holder=getHolder();
        Start_Sense_holder=holder.getSurface();
        Start_Sense_Thread=new Thread(this);
        Init_Start_Sense();
    }

    protected void Init_Start_Sense(){

        Start_Sense_Background=BitmapFactory.decodeResource(res, R.drawable.back_ground);
        Start_Sense_Main=BitmapFactory.decodeResource(res, R.drawable.air_01_blue);
        Start_Sense_Enemy=BitmapFactory.decodeResource(res, R.drawable.enemy_type_1);
        Start_Sense_Bullet=BitmapFactory.decodeResource(res, R.drawable.bullet_length);

        Start_Sense_Main_Array_List=new ArrayList<JExMain_Fighter_Wings_of_Hope>();
        Start_Sense_Main_Array_List.clear();
        JExMain_Fighter_Wings_of_Hope Start_Sense_Main_Show = new JExMain_Fighter_Wings_of_Hope(Start_Sense_Main,1000);
        Start_Sense_Main_Array_List.add(Start_Sense_Main_Show);

        Start_Sense_Enemy_Array_List=new ArrayList<JE_Fight_Fighter_Enemy_Factory>();
        Start_Sense_Enemy_Array_List.clear();

        for(int i=0;i<1;i++) {
            JE_Fight_Fighter_Enemy_Factory Start_Sense_Enemy_Show =
            new JE_Fight_Fighter_Enemy_Factory
            (Start_Sense_Enemy, true, 10, "TOP",
            1, 0, 3, 1, "Normal",i);
            Start_Sense_Enemy_Array_List.add(Start_Sense_Enemy_Show);
        }

        Start_Sense_Main_Bullet_List=new ArrayList<JExBullet_Factroy>();
        Start_Sense_Main_Bullet_List.clear();





    }

    @Override
    public void run() {

        while (Start_Sense_Running){

            boolean Enemy_Bullet_Shoot=false;

            try {
                Thread.sleep(5);
                //取得並鎖住畫布(canvas)


                canvas = holder.lockCanvas();

                //清除畫面
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                //畫出背景
                canvas.drawBitmap(Start_Sense_Background, 0f, 0f, null);

                Enemy_Bullet_Time+=5;

                if(Enemy_Bullet_Time==250){

                    Enemy_Bullet_Shoot=true;

                }

                if (!Start_Sense_Enemy_Array_List.isEmpty()) {

                    for (int i = 0; i < Start_Sense_Enemy_Array_List.size(); i++) {
                        //若該物件還活著，則呼叫  物件的 Draw_Fighter() 方法
                        //將物件圖片繪至 canvas 上

                        if(Enemy_Bullet_Shoot){
                            for (int e = 0; e < Start_Sense_Enemy_Array_List.size(); e++) {
                                JExBullet_Factroy Enemy_Bu_1 = new JExBullet_Factroy(Start_Sense_Bullet, true, "TOP", 2,
                                        0, (Start_Sense_Enemy_Array_List.get(e).Object_x + (Start_Sense_Enemy_Array_List.get(e).Object_w/2)),
                                        Start_Sense_Enemy_Array_List.get(e).Object_y + Start_Sense_Enemy_Array_List.get(e).Object_h, "Enemy");
                                Enemy_Bu_1.Set_X_Y(Start_Sense_Enemy_Array_List.get(e).Object_x, Start_Sense_Enemy_Array_List.get(e).Object_y);
                                Start_Sense_Main_Bullet_List.add(Enemy_Bu_1);
                            }
                            Enemy_Bullet_Shoot=false;
                            Enemy_Bullet_Time=0;
                        }

                        if(!Start_Sense_Main_Bullet_List.isEmpty()) {
                            for (int a = 0; a < Start_Sense_Main_Bullet_List.size(); a++) {
                                if (Start_Sense_Main_Bullet_List.get(a).Return_Whos_Bullet().equals("Main")) {
                                    if (Start_Sense_Main_Bullet_List.get(a).Is_Collision(Start_Sense_Enemy_Array_List.get(i).Object_x, Start_Sense_Enemy_Array_List.get(i).Object_y,
                                            Start_Sense_Enemy_Array_List.get(i).Object_w, Start_Sense_Enemy_Array_List.get(i).Object_h)) {
                                        Log.wtf("Enemy_Collision", "Main_Bullet");
                                        Start_Sense_Main_Bullet_List.remove(a);
                                        Start_Sense_Enemy_Array_List.get(i).Enemy_HP-=10;
                                    }
                                }
                            }
                        }
                        if (Start_Sense_Enemy_Array_List.get(i).Is_Alive())
                            Start_Sense_Enemy_Array_List.get(i).Draw_Fighter(canvas);
                    }

                }
                //從 Enemy_Type_1 物件陣列中移除已經停止活動的物件
                if (!Start_Sense_Enemy_Array_List.isEmpty()) {
                    for (int i = 0; i < Start_Sense_Enemy_Array_List.size(); i++) {
                        //刪除已經死亡的物件
                        if (!Start_Sense_Enemy_Array_List.get(i).Is_Alive()){
                            Start_Sense_Enemy_Array_List.remove(i);

                        }
                    }
                }

                if(Start_Sense_Enemy_Array_List.isEmpty()){


                    for(int i=0; i<1; i++) {
                        //產生 Enemy_Type_1 實體 au
                        JE_Fight_Fighter_Enemy_Factory au = new JE_Fight_Fighter_Enemy_Factory(Start_Sense_Enemy,true,10,"TOP",1,0,3,1,"Normal",i);
                        //陸續將 au 放入 Enemy_Type_1 物件陣列中
                        Start_Sense_Enemy_Array_List.add(au);

                    }
                }

                if (!Start_Sense_Main_Array_List.isEmpty()) {
                    for (int i = 0; i < Start_Sense_Main_Array_List.size(); i++) {
                        //若該物件還活著，則呼叫  物件的 Draw_Fighter() 方法
                        //將物件圖片繪至 canvas 上
                        if (Start_Sense_Main_Array_List.get(i).Is_Alive()) {
                            Start_Sense_Main_Array_List.get(i).Draw_Fighter(canvas);
                            int movex = (Start_Sense_Main_Array_List.get(i).Return_X()+((Start_Sense_Main_Array_List.get(i).Return_W()/2)-5));
                            int movey = (Start_Sense_Main_Array_List.get(i).Return_Y()-(Start_Sense_Main_Array_List.get(i).Return_H()/2));
                            if(Bullet_Time!=250) {
                                Bullet_Time += 5;
                            }else if(Bullet_Time==250) {
                                JExBullet_Factroy Main_Bu_1 = new JExBullet_Factroy(Start_Sense_Bullet, true, "BOTTOM", 2,
                                        0,Start_Sense_Main_Array_List.get(0).Return_X(),Start_Sense_Main_Array_List.get(0).Return_Y_Add_H(),"Main");
                                Main_Bu_1.Set_X_Y(movex, movey);
                                Start_Sense_Main_Bullet_List.add(Main_Bu_1);

                                Bullet_Time=0;

                            }
                            if(!Start_Sense_Main_Bullet_List.isEmpty()) {
                                for (int a = 0; a < Start_Sense_Main_Bullet_List.size(); a++) {
                                    if (Start_Sense_Main_Bullet_List.get(a).Return_Whos_Bullet().equals("Enemy")) {
                                        if (Start_Sense_Main_Bullet_List.get(a).Is_Collision(Start_Sense_Main_Array_List.get(0).Return_X(), Start_Sense_Main_Array_List.get(0).Return_Y(),
                                                Start_Sense_Main_Array_List.get(0).Return_W(), Start_Sense_Main_Array_List.get(0).Return_H())) {
                                            Log.wtf("Main_Collision", "Enemy_Bullet");
                                            Start_Sense_Main_Bullet_List.remove(a);
                                            if(Start_Sense_Main_Array_List.get(0).Main_HP>=100){
                                                Start_Sense_Main_Array_List.get(0).Main_HP-=10;
                                            }

                                        }
                                    }
                                }
                            }

                            for (int a = 0; a < Start_Sense_Enemy_Array_List.size(); a++) {
                                Start_Sense_Enemy_Array_List.get(a).Is_Collision(movex, movey, Main_Object_w, Main_Object_h);
                            }

                        }
                    }

                }
                if (!Start_Sense_Main_Array_List.isEmpty()) {
                    for (int i = 0; i < Start_Sense_Main_Array_List.size(); i++) {
                        //刪除已經死亡的物件
                        if (!Start_Sense_Main_Array_List.get(i).Is_Alive()) Start_Sense_Main_Array_List.remove(i);
                    }
                }

                if(!Start_Sense_Main_Bullet_List.isEmpty()){
                    for (int i=0;i<Start_Sense_Main_Bullet_List.size();i++){
                        if(Start_Sense_Main_Bullet_List.get(i).Is_Alive())Start_Sense_Main_Bullet_List.get(i).Draw_Fighter(canvas);
                    }

                }
                if (!Start_Sense_Main_Bullet_List.isEmpty()) {
                    for (int i = 0; i < Start_Sense_Main_Bullet_List.size(); i++) {
                        //刪除已經死亡的物件
                        if (!Start_Sense_Main_Bullet_List.get(i).Is_Alive()){
                            Start_Sense_Main_Bullet_List.remove(i);
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
        if (Start_Sense_Thread.getState() == Thread.State.NEW) {
            Start_Sense_Thread.start();
        }else {
            Start_Sense_Thread = new Thread(this);
            Start_Sense_Thread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
