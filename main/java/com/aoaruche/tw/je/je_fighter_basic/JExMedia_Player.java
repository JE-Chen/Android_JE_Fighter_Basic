/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.aoaruche.tw.je.je_fighter_basic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;

import java.io.File;
import java.io.IOException;



/**
 * Created by JE-Chen on 2018/2/16.
 */

public class JExMedia_Player extends Service {

    public boolean sd_is_exist=false;
    public static MediaPlayer start_mp = new MediaPlayer();
    @Override
    public void onCreate(){
        super.onCreate();//繼承onCreate方法
        try{

                start_mp = new MediaPlayer();
                // mp = MediaPlayer.create(this, R.raw.startmusic);
                //  可行
                boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
                if(sdCardExist) {
                    File search_exist = getExternalFilesDir(null);
                    String search_path = (search_exist.getPath() + "title_music.mp3");
                    File out_music = new File(search_path);
                    if (out_music.exists()) {
                        start_mp.setDataSource(search_path);
                        sd_is_exist=true;

                }

                if(sd_is_exist==false) {
                    start_mp.setDataSource("/data/data/com.tw.je.aoaruche/files/title_music.mp3");
                }

                start_mp.prepare();
                start_mp.setLooping(true);
                start_mp.start();
            }

        }catch(IllegalStateException e){
            e.printStackTrace();
            start_mp = new MediaPlayer();
            // mp = MediaPlayer.create(this, R.raw.startmusic);
            //  可行
            boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
            if(sdCardExist) {
                File search_exist= getExternalFilesDir(null);
                String search_path = (search_exist.getPath() + "title_music.mp3");
                File out_music = new File(search_path);
                if (out_music.exists()) {
                    try {
                        start_mp.setDataSource(search_path);
                        sd_is_exist=true;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                }
                if(sd_is_exist==false) {
                    try {
                        start_mp.setDataSource("/data/data/com.tw.je.aoaruche/files/title_music.mp3");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            try {
                start_mp.prepare();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            start_mp.setLooping(true);
            start_mp.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        if(start_mp!=null) {
            start_mp.start();
        }
        boolean isPlaying = false;
        try {
            isPlaying = start_mp.isPlaying();
        }
        catch (IllegalStateException e) {
            start_mp = new MediaPlayer();
            // mp = MediaPlayer.create(this, R.raw.startmusic);
            //  可行
            boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
            if(sdCardExist) {
                File search_exist = getExternalFilesDir(null);
                String search_path = (search_exist.getPath() + "title_music.mp3");
                File out_music = new File(search_path);
                if (out_music.exists()) {
                    try {
                        start_mp.setDataSource(search_path);
                        sd_is_exist=true;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }                if(sd_is_exist==false) {
                try {
                    start_mp.setDataSource("/data/data/com.tw.je.aoaruche/files/title_music.mp3");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            try {
                start_mp.prepare();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            start_mp.setLooping(true);
            start_mp.start();

        }
        if(start_mp!=null) {
            start_mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // TODO Auto-generated method stub
                    // 釋放資源
                    try {

                        mp.release();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy(){//服務停止時停止播放音樂並釋放資源

        try{
            if(start_mp!=null) {
                start_mp.stop();
                start_mp.release();
                start_mp = null;
            }
        }catch(IllegalStateException e){
            e.printStackTrace();
            start_mp = new MediaPlayer();
            // mp = MediaPlayer.create(this, R.raw.startmusic);
            //  可行
            boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
            if(sdCardExist) {
                File search_exist = getExternalFilesDir(null);
                String search_path = (search_exist.getPath() + "title_music.mp3");
                File out_music = new File(search_path);
                if (out_music.exists()) {
                    try {
                        start_mp.setDataSource(search_path);
                        sd_is_exist=true;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                }                if(sd_is_exist==false) {
                try {
                    start_mp.setDataSource("/data/data/com.tw.je.aoaruche/files/title_music.mp3");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            try {
                start_mp.prepare();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            start_mp.setLooping(true);
            start_mp.start();


        }
        super.onDestroy();
    }

}
