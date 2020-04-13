/*
 * Copyright (c) 2018.JE-Chen
 */

package com.aoaruche.tw.je.je_fighter_basic;

import android.graphics.Bitmap;

public class JExInternet_Data {

    public static int Download_count=0;

    public static int Download_total=2;

    public static int Download_state=0;

    public static int can_get_start=0;

    public int return_Download_state(){return this.Download_state;}

    public void add_Download_state(){this.Download_state+=1;}

    public void set_Download_state(int set){this.Download_state=set;}

    public static  boolean can_download=true;

    public static String[] Download_Array={
            //music

            //picture

    };

    public static String[] check_music={

    };

    public static String[] chech_picture={

    };

    public void JExInternet_Data(){


    }

    public void setDownload_Count(int count){this.Download_count=count;}

    public void addDownload_Count(){this.Download_count+=1;}

    public long Search_Download_Counter(){return this.Download_count;}


}