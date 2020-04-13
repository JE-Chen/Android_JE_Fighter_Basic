/*
 * Copyright (c) 2018.JE-Chen
 */

package com.aoaruche.tw.je.je_fighter_basic;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;


    public class JExDownload_file extends AsyncTask<String, Double, Boolean> {

        public static final String TAG = "JExDownload_file";

        public static final int NOTIFICATION_PROGRESS_UPDATE = 0x10;//用於更新下載進度的標記
        public static final int NOTIFICATION_PROGRESS_SUCCEED = 0x11;//表示下载成功
        public static final int NOTIFICATION_PROGRESS_FAILED = 0x12;//表示下载失敗

        //URL
        private String mUrl;
        //activity
        private Context mContext;
        //任务計時器
        private Timer mTimer;
        //定時任務
        private TimerTask mTask;
        //Main Thread's Handler
        private Handler mHandler;
        //所要下载的文件大小
        private long mFileSize;
        //已下载的文件大小
        private long mTotalReadSize;
        //AsyncTaskRunnable實現了Runnable接口，用於更新下載進度的顯示
        private JExAsyncTaskRunnable mRunnable;

        //構造方法,建構子
        public JExDownload_file(Context context, Handler handler) {
            mContext = context;
            mHandler = handler;

            mTimer = new Timer();
            mTask = new TimerTask() {//在run方法中執行定時的任務
                @Override
                public void run() {
                    //size表示下载进度的百分比
                    float size = (float) mTotalReadSize * 100 / (float) mFileSize;
                    //通過AsyncTaskRunnable的setDatas方法下载的進度和狀態（更新中、失敗、成功）
                    mRunnable.setDatas(NOTIFICATION_PROGRESS_UPDATE, size);
                    //更新進度
                    mHandler.post(mRunnable);
                }
            };
            mRunnable = new JExAsyncTaskRunnable(mContext);
        }


        // 執行耗時操作,params[0]為URL，params[1]為文件名（空則null）
        @Override
        protected Boolean doInBackground(String... params) {

            //任務計時器必定要啟動
            mTimer.schedule(mTask, 0, 500);

            try {
                mUrl = params[0];
                //建立連結
                URLConnection connection = new URL(mUrl).openConnection();
                //獲取文件大小
                mFileSize = connection.getContentLength();
                Log.d(TAG, "the count of the url content length is : " + mFileSize);

                //獲得輸入流
                InputStream is = connection.getInputStream();
                //先建立文件夾(如果不存在 )
                File fold = new File(getFolderPath());
                if (!fold.exists()) {
                    fold.mkdirs();
                }

                String fileName = "";
                //判斷文件名，自定義或ＵＲＬ獲得
                if (params[1] != null) {
                    fileName = params[1];
                } else {
                    fileName = getFileName(params[0]);
                }
                //文件输出流 (依照文件夾路徑+傳入的檔案名稱)
                FileOutputStream fos = new FileOutputStream(new File(getFolderPath()
                        + fileName));

                byte[] buff = new byte[1024];
                int len;
                while ((len = is.read(buff)) != -1) {
                    mTotalReadSize += len;
                    fos.write(buff, 0, len);
                }
                fos.flush();
                fos.close();

            } catch (Exception e) {
                //異常，下載失敗
                mRunnable.setDatas(NOTIFICATION_PROGRESS_FAILED, 0);
                //發送下載失敗
                mHandler.post(mRunnable);
                if (mTimer != null && mTask != null) {
                    mTimer.cancel();
                    mTask.cancel();
                }
                e.printStackTrace();
                return false;
            }
            //下载成功
            mRunnable.setDatas(NOTIFICATION_PROGRESS_SUCCEED, 0);
            mHandler.post(mRunnable);
            if (mTimer != null && mTask != null) {
                mTimer.cancel();
                mTask.cancel();
            }
            return true;
        }

        //由URL獲得文件名
        private String getFileName(String string) {
            return string.substring(string.lastIndexOf("/") + 1);
        }

        //下載文件夾路徑
        private String getFolderPath() {
            return "/data/data/com.tw.je.aoaruche/files/";
        }


        // doInBackground方法之前调用，初始化UI
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // 在doInBackground方法之後调用
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
               //成功
            } else {
                //失敗
            }
        }

        @Override
        protected void onProgressUpdate(Double... values) {
            super.onProgressUpdate(values);
        }


    }
