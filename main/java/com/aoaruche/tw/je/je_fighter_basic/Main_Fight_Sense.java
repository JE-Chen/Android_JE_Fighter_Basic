package com.aoaruche.tw.je.je_fighter_basic;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static com.aoaruche.tw.je.je_fighter_basic.JExBullet_Factroy.Enemy_Bullet_Runnable_Open;

import static com.aoaruche.tw.je.je_fighter_basic.JE_Fight_Fighter_Enemy_Factory.Enemy_Runnable_Open;
import static com.aoaruche.tw.je.je_fighter_basic.JE_Fight_Fighter_Enemy_Factory.Fighter_Type;
import static com.aoaruche.tw.je.je_fighter_basic.JE_Fighter_SurfaceView.Game_Main_Thread;
import static com.aoaruche.tw.je.je_fighter_basic.JE_Fighter_SurfaceView.Runnable_Running;
import static com.aoaruche.tw.je.je_fighter_basic.JExMain_Fighter_Wings_of_Hope.Main_HP;
import static com.aoaruche.tw.je.je_fighter_basic.Start_Sense_SurfaceView.Start_Sense_Running;
import static com.aoaruche.tw.je.je_fighter_basic.Start_Sense_SurfaceView.Start_Sense_Thread;

public class Main_Fight_Sense extends AppCompatActivity {

    //  現在的畫面
    public static long Now_Sense_Count = 0L;
    //  是否暫停狀態
    public static long Is_Pause = 0;
    // 讀Bitmap 類別
    public JExBitmap JB = new JExBitmap();
    // 手機的寬度
    public static int Max_Width = 0;
    // 手機的高度 <下>
    public static int Max_Height = 0;


    //AI目前的狀態
    public static int AI_Change_Counter = 0;
    // 用以判斷是否橫頻
    public int what_h;
    // 用以判斷是否橫頻
    public int what_w;
    // 敵機的陣列表
    public static ArrayList<Thread> Enemy_List;
    // 子彈的陣列表
    public static ArrayList<Thread> Bullet_List;

    public static ArrayList<JE_Fight_Fighter_Enemy_Factory> Enemy_Type_1;
    public static ArrayList<JExMain_Fighter_Wings_of_Hope> Main;
    public static ArrayList<JExBullet_Factroy> BF;

    public JExFile JF =new JExFile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Enemy_List = new ArrayList<Thread>();
        Enemy_List.clear();
        Bullet_List = new ArrayList<Thread>();
        Bullet_List.clear();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics monitorsize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(monitorsize);
        Max_Width = monitorsize.widthPixels;
        Max_Height = monitorsize.heightPixels;
        //int Height_Region_1=Max_Height-100;
       // int Height_Region_2=Max_Height+100;
        Max_Height-=100;
        what_h = Max_Height;
        what_w = Max_Width;

        if (what_h > what_w) {

        } else if (what_w > what_h) {
            Max_Height = what_w;
            Max_Width = what_h;
        }

        setContentView(new JE_Fighter_SurfaceView(this));
        //setContentView(new Start_Sense_SurfaceView(this));
        Log.wtf("SUPER", "onCreate");

        JF.Save_File_Json("test",this);
        JF.read_memory_json("test",this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (AI_Change_Counter == 0) {
                Main_HP = 50;
                AI_Change_Counter += 1;
                Log.wtf("AI_Change\t :", String.valueOf(AI_Change_Counter));
            } else if (AI_Change_Counter == 1) {
                // Enemy_HP=100;
                Main_HP = 500;
                Fighter_Type = "BOSS";
                Log.wtf("AI_Change\t :", String.valueOf(AI_Change_Counter));
                AI_Change_Counter += 1;
            } else if (AI_Change_Counter == 2) {
                Fighter_Type = "null";
                AI_Change_Counter = 0;
                Log.wtf("AI_Change\t :", String.valueOf(AI_Change_Counter));
            }

        }
        return false;
    }

    public void onResume() {

        //Start Sense
        Start_Sense_Running = true;

        //Fight_Sense
        Enemy_Bullet_Runnable_Open = true;
        Enemy_Runnable_Open = true;
        Runnable_Running = true;

        if (Is_Pause == 1) {
            Is_Pause = 0;
            if(Game_Main_Thread!=null) {
                if (Game_Main_Thread.getState() == Thread.State.NEW) {
                    Game_Main_Thread.start();
                } else {
                    Game_Main_Thread = new Thread(Game_Main_Thread);
                    Game_Main_Thread.start();
                }
            }
            if(Start_Sense_Thread!=null) {
                if (Start_Sense_Thread.getState() == Thread.State.NEW) {
                    Start_Sense_Thread.start();
                } else {
                    Start_Sense_Thread = new Thread(Start_Sense_Thread);
                    Start_Sense_Thread.start();
                }
            }
            for (int a = 0; a < Enemy_List.size(); a++) {
                if(Enemy_List.get(a)!=null) {
                    if (Enemy_List.get(a).getState() == Thread.State.NEW) {
                        Enemy_List.get(a).start();
                    } else {

                        Thread aw = new Thread(Enemy_List.get(a));
                        aw.start();
                        Enemy_List.set(a, aw);
                    }
                }
            }
            for (int b = 0; b < Bullet_List.size(); b++) {
                if(Bullet_List.get(b)!=null) {
                    if (Bullet_List.get(b).getState() == Thread.State.NEW) {
                        Bullet_List.get(b).start();
                    } else {
                        Thread bw = new Thread(Bullet_List.get(b));
                        bw.start();
                        Bullet_List.set(b, bw);
                    }
                }
            }
        }
        Log.wtf("SUPER", "onResume");
        super.onResume();
    }


    public void onPause() {
        Is_Pause = 1;
        Start_Sense_Running = false;
        Enemy_Bullet_Runnable_Open = false;
        Enemy_Runnable_Open = false;
        Runnable_Running = false;


        Log.wtf("SUPER", "onPause");
        super.onPause();

    }


    public void onStop() {
        Is_Pause = 1;
        Enemy_Bullet_Runnable_Open = false;
        Enemy_Runnable_Open = false;
        Runnable_Running = false;
        Log.wtf("SUPER", "onStop");
        super.onStop();
    }


    public void onDestroy() {
        Log.wtf("SUPER", "onDestroy");
        System.exit(0);
        super.onDestroy();


    }



        }
