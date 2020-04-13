/*
 * Copyright (c) 2018. JE-Chen
 */

package com.aoaruche.tw.je.je_fighter_basic;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class JExFile {

    public static String Path = "null";
    public static String File_name = "null";
    public static File SD_Path = null;
    //AES 加密類別
    public  JExAES J_AES=new JExAES();
    //儲存緩衝區大小
    public static final int READ_BLOCK_SIZE = 10000;

    public void Save_File_Json(String Save_File_Name,Context FileContext){
        try {
            FileOutputStream out = FileContext.getApplicationContext().openFileOutput(Save_File_Name, MODE_PRIVATE);
            OutputStreamWriter sw = new OutputStreamWriter(out);

            String data = "{" + "\r\n" +
                    "\"Fighter_Save\"" + ": [" + "\r\n" +
                    "{" + "\r\n" +
                    "\"test\":" + "2" + "," + "\r\n" +
                    "\"test\":" + "2" + "," + "\r\n" +
                    "\"test\":" + "2" + "," + "\r\n" +
                    "\"test\":" + "2" + "," + "\r\n" +
                    "\"test\":" + "2" + "," + "\r\n" +
                    "\"test\":" + "2" + "," + "\r\n" +
                    "\"test\":" + "2" + "\r\n" +
                    "}" + "\r\n" +
                    "]" + "\r\n" +
                    "}";
            data = J_AES.Encrypt(data,"12345678123456781234567812345678");
            Log.d("TTTTTTTTTTTTTT",data);
            sw.write(data);

//開始輸出

            sw.flush();

//關閉輸出

            sw.close();
        }

//宣告 IOException ex

        catch (IOException ex) {

//並讓IOException ex 執行 ex.printStackTrace();

            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Save_File_any(String Save_File_Name,String data,Context FileContext) throws IOException{
        try {
            FileOutputStream out = FileContext.getApplicationContext().openFileOutput(Save_File_Name, MODE_PRIVATE);
            OutputStreamWriter sw = new OutputStreamWriter(out);

            data = J_AES.Encrypt(data,"12345678123456781234567812345678");
            Log.d("TTTTTTTTTTTTTT",data);
            sw.write(data);

//開始輸出

            sw.flush();

//關閉輸出

            sw.close();
        }

//宣告 IOException ex

        catch (IOException ex) {

//並讓IOException ex 執行 ex.printStackTrace();

            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //載入JSON內容
    public void read_memory_json(String Json_File_Name,Context File_Context) {
        try {
            String json_test = load_JSON(Json_File_Name,File_Context);
            if (json_test == null) {
                Log.d("JSON錯誤", "錯誤");
            }
            if (json_test != null) {
                json_test= J_AES.Decrypt(json_test,"12345678123456781234567812345678");
                Log.d("ttttttttttttttttt",json_test);
                JSONObject reader = new JSONObject(json_test);
                if (Json_File_Name.equals("")) {
                    JSONArray json_read_array = reader.getJSONArray("");
                    for (int i = 0; i < json_read_array.length(); i++) {
                        try {
                            JSONObject json_reader = json_read_array.getJSONObject(i);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        } catch(JSONException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //載入JSON所需函數
    public String load_JSON (String Json_File_Name,Context File_Context){
        String json = null;
        try {

            FileInputStream in = File_Context.getApplicationContext().openFileInput(Json_File_Name);
            InputStreamReader sr = new InputStreamReader(in);
            char[] buff = new char[READ_BLOCK_SIZE];
            String readtest = " ";
            int count;
            while ((count = sr.read(buff)) > 0) {
                String s = String.copyValueOf(buff, 0, count);
                readtest += s;
                buff = new char[READ_BLOCK_SIZE];
            }
            sr.close();
            json = readtest;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    public void File_Serach(String Path, String File_name) {

        this.Path = Path;
        this.File_name = File_name;

    }

    public void set_Path(String Path) {

        this.Path = Path;

    }

    public void setFile_name(String File_name) {

        this.File_name = File_name;

    }

    public void set_Path_and_File_name(String Path, String File_name) {

        this.Path = Path;
        this.File_name = File_name;

    }

    public void set_outside_Path(File SD_Path) {

        this.SD_Path = SD_Path;

    }

    public boolean Search_File_inside(String Path, String File_name) {
        this.Path = Path;
        this.File_name = File_name;

        boolean file_exist = false;
        File exist = new File(Path);
        if (exist.getName().equals(File_name)) {
            if (exist.exists()) {
                file_exist = true;
            } else {
                file_exist = false;
            }
        }
        return file_exist;
    }

    public boolean Search_File_outside(File SD_Path, String File_name) {
        this.SD_Path = SD_Path;
        this.File_name = File_name;
        boolean file_exist = false;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            String search_path = (SD_Path.getPath() + File_name);
            File search_file = new File(search_path);
            if (search_file.exists()) {
                file_exist = true;
            } else {
                file_exist = false;
            }
        }
        return file_exist;
    }

    public boolean search_in_or_out_side(String Path, String File_name, File SD_Path) {
        this.Path = Path;
        this.File_name = File_name;
        boolean file_exist = false;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            String search_path = (SD_Path.getPath() + File_name);
            File search_out_file = new File(search_path);
            File search_in_file = new File(Path + File_name);
            if (search_out_file.exists() || search_in_file.exists()) {
                file_exist = true;
            } else {
                file_exist = false;
            }
        }

        return file_exist;
    }
}



