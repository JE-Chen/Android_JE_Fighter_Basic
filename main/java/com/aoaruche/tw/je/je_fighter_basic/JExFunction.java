

package com.aoaruche.tw.je.je_fighter_basic;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.gesture.GestureOverlayView;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;



/**
 * Created by JE on 2017/8/2.
 */

public class JExFunction {

    private static Toast toast;
    private static long lastClickTime;

    public void JExDelay_change_Image(final ImageView image1, final long time,final int drawable) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                image1.setImageResource(drawable);
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }


    //延遲毫秒 並清除圖片x1
    public void JExDelay_Clean_Image(final ImageView image1, final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                image1.setVisibility(View.GONE);
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    //延遲毫秒 並清除圖片x2
    public void JExDelay_Clean_Image(final ImageView image1, final ImageView image2, final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                image1.setVisibility(View.GONE);
                image2.setVisibility(View.GONE);
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    //延遲毫秒 並清除圖片x3
    public void JExDelay_Clean_Image(final ImageView image1, final ImageView image2, final ImageView image3, final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                image1.setVisibility(View.GONE);
                image2.setVisibility(View.GONE);
                image3.setVisibility(View.GONE);
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    //延遲毫秒 並顯示scroview
    public void JExDelay_show_scroview(final ScrollView scro, final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                scro.setVisibility(View.VISIBLE);
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    //延遲並 改變圖片
    public void JExFunction_Delay_change_image(final ImageView image, final long time, final int nextimage) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:

                image.setImageResource(nextimage);
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    //延遲並 改變圖片
    public void JExFunction_Delay_change_imageButton(final ImageButton Imagebutton, final long time, final int nextimage) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:

                Imagebutton.setImageResource(nextimage);
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
    //延遲毫秒 並顯示ImageButton
    public void JExDelay_show_Imagebutton(final ImageButton Imageb, final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                Imageb.setVisibility(View.VISIBLE);
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    //延遲毫秒 並顯示Textview
    public void JExDelay_show_Textview(final TextView Textv, final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                Textv.setVisibility(View.VISIBLE);
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    //延遲毫秒 並顯示ImageView
    public void JExDelay_show_ImageView(final ImageView imv, final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                imv.setVisibility(View.VISIBLE);
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
    //延遲毫秒 並顯示Edittext
    public void JExDelay_show_Edittext(final EditText Edit_text_show, final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                Edit_text_show.setVisibility(View.VISIBLE);
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 300) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    //延遲毫秒 並取消顯示ImageButton
    public void JExDelay_disappear_Imagebutton(final ImageButton Imageb, final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                Imageb.setVisibility(View.GONE);
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //延遲毫秒 並取消顯示ScrollView
    public void JExDelay_disappear_Scrollview(final ScrollView scro, final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                scro.setVisibility(View.GONE);
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
    //延遲毫秒 並顯示GestureOverlayView
    public void JExDelay_show_GestureOverlayView(final GestureOverlayView gest, final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                gest.setVisibility(View.VISIBLE);
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
    //延遲毫秒 並取消顯示GestureOverlayView
    public void JExDelay_disappear_GestureOverlayView(final GestureOverlayView gest, final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                gest.setVisibility(View.INVISIBLE);
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    //延遲毫秒 並取消顯示TextView
    public void JExDelay_disappear_TextView(final TextView text, final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                text.setVisibility(View.INVISIBLE);
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    //延遲毫秒 並顯示劇情Toast x1
    public void JExDelay_Story_Toast(final Toast t1,final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:

                t1.show();

            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
 //延遲並讓按鈕可按
    public void JExDelay_Enable_ImageButton(final ImageButton button,final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:

                button.setEnabled(true);

            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    //延遲並讓手勢可畫
    public void JExDelay_Enable_Gesture_Overlay_View(final GestureOverlayView overlayView,final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:

                overlayView.setEnabled(true);
                overlayView.setVisibility(View.VISIBLE);

            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
    public void JExDelay_change_text(final TextView textView, final long time, final String change_text) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:

                textView.setText(change_text);

            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
    public void scroll_reset(ScrollView scrollView,long scroll_y){
        int scroll_weight = scrollView.getHeight();
        scrollView.scrollTo(0,scroll_weight);
        scrollView.scrollTo(0,0);
        scroll_y=0;
    }
    public void JExstory_time(final long time,final TextView text,final String stext) {

        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String date = sDateFormat.format(new java.util.Date());
text.setText(date+stext);
text.setVisibility(View.VISIBLE);

            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    public  void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }



    public  void show_toast_top(Context context,
                           String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);

        } else {
            toast.setText(content);
        }
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,-20);
        toast.show();
    }

    //是否安裝此檔案
    public boolean have_this_file(Context context,String name) {
        PackageManager manager = context.getPackageManager();
        List<PackageInfo> pkgList = manager.getInstalledPackages(0);
        for (int i = 0; i < pkgList.size(); i++) {
            PackageInfo pI = pkgList.get(i);
            if (pI.packageName.equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    // 自動Scroll_mini
    public void Scroll_thread_mini(final long time, final ScrollView scrollView) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                scrollView.setEnabled(true);
              /*  if (scroll_state == 1) {
                    scrollview_y = 0;
                }
                */
//延遲完後要幹嘛:
                int measuredHeight = scrollView.getChildAt(0).getMeasuredHeight();
                int scrollY = scrollView.getScrollY();
                int height = scrollView.getHeight();

               /* if (measuredHeight > scrollY + height && scroll_state == 0 && scrollview_is_click == 0) {
                    scrollView.scrollTo(0, scrollview_y + 1);
                    scrollview_y += 1;
                    Scroll_thread_mini(time, scrollView);
                    scroll_state = 0;
                }
                if (measuredHeight <= scrollY + height && scroll_state == 0) {
                    scroll_state = 1;
                }
                */
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {

                    Thread.sleep(time);

                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }




}











