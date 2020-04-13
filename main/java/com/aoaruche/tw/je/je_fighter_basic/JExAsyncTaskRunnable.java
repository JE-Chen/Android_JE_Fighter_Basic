/*
 * Copyright (c) 2018. JE-Chen
 */

package com.aoaruche.tw.je.je_fighter_basic;
import java.text.DecimalFormat;
import java.util.logging.Handler;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.aoaruche.tw.je.je_fighter_basic.R;

public class JExAsyncTaskRunnable implements Runnable{

    public static final String TAG = "JExAsyncTaskRunnable";
    //Main Thread
    private Context mContext;
    //notification的狀態：更新 or 失败 or 成功
    private int mStatus;
    //notification的下载比例
    private float mSize;
    //管理下拉選單的通知信息
    private NotificationManager mNotificationManager;
    //下拉選單的通知訊息
    private Notification mNotification;
    //下拉選單的通知View
    private RemoteViews mRemoteViews;
    //下拉選單的通知ID
    private static final int NOTIFICATION_ID = 1;

    public static long download_counter=1;

    //设置比例和數據
    public void setDatas(int status , float size) {
        this.mStatus = status;
        this.mSize = size;
    }

    //初始化
    public JExAsyncTaskRunnable(Context context) {
        this.mContext = context;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        //初始化下拉選單的通知信息
        mNotification = new Notification();
        //mNotification.icon = R.drawable.logoaoaruche;//設置下載進度的圖片
        mNotification.tickerText = mContext.getResources().getString(R.string.app_name); //設置下載進度的標題
      //  mRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.download_layout);//對於RemoteView的使用
       // mRemoteViews.setImageViewResource(R.id.id_download_icon, R.drawable.logoaoaruche);
    }

    @Override
    public void run() {    //判斷不同狀態，更新下拉選單的訊息
        switch (mStatus) {
            case JExDownload_file.NOTIFICATION_PROGRESS_FAILED://下載失敗
                mNotificationManager.cancel(NOTIFICATION_ID);
                break;

            case JExDownload_file.NOTIFICATION_PROGRESS_SUCCEED://下载成功
            //    Log.d(TAG, "the Complete of the download " +String.valueOf(download_counter));
              //  mRemoteViews.setTextViewText(R.id.id_download_textview, "正在完成"+"第 "+String.valueOf(download_counter)+"個文件");
               // mRemoteViews.setProgressBar(R.id.id_download_progressbar, (JExInternet_Data.Download_total), (int) download_counter, false);
                mNotification.contentView = mRemoteViews;
                mNotificationManager.notify(NOTIFICATION_ID, mNotification);
                mNotificationManager.cancel(NOTIFICATION_ID);
                if(download_counter==1){
                    download_counter=2;
                }

                 if(JExInternet_Data.Download_state==1){
                     JExInternet_Data.Download_state=2;
                 }
                Log.d(TAG, "the Complete of the download " +String.valueOf(download_counter));
                break;

            case JExDownload_file.NOTIFICATION_PROGRESS_UPDATE://更新中
              //  int show_MB = ((int)mSize % 1024*1024);
              //  mRemoteViews.setTextViewText(R.id.id_download_textview, "正在完成"+"第 "+String.valueOf(download_counter)+"個文件");
             //   mRemoteViews.setProgressBar(R.id.id_download_progressbar, (JExInternet_Data.Download_total), (int) download_counter, false);
                mNotification.contentView = mRemoteViews;
                mNotificationManager.notify(NOTIFICATION_ID, mNotification);

                break;
        }
    }

}