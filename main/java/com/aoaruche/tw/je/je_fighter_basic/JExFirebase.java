/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */

package com.aoaruche.tw.je.je_fighter_basic;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class JExFirebase {
    public JExFirebase(){

    }
    public  String magic_power;
    public  String coin;
    public  String data1;
    public  String data2;
    public  String data3;
    public  String data4;
    public  String data5;
    public  String data6;
    public  String hp_lv;
    public  String need_cash_to_up_hp_lv;
    public  String need_cash_to_up_magic_power;
    public  String skill_points;
    public  String what_region_save;
    public  String money_skill_lv;
    public  String critical_lv;
    public  String healing_lv;
    public  String dodge_lv;
    public  String miracle_of_live_lv;
    public  String great_mage_lv;
    public  String exercise_lv;


    public JExFirebase(String Magic_Power,String hp_lv,String what_region_save,String coin,String UP_Magic_Power,String UP_Hp_Lv,String Skill_Points,
                       String data1, String data2, String data3, String data4, String data5, String data6,
   String money_skill_lv,String critical_lv ,String healing_lv,String dodge_lv,String miracle_of_live_lv,String great_mage_lv,String exercise_lv) {
        this.magic_power =Magic_Power;
        this.hp_lv = hp_lv;
        this.what_region_save = what_region_save;
        this.coin = coin;
        this.need_cash_to_up_magic_power = UP_Magic_Power;
        this.need_cash_to_up_hp_lv = UP_Hp_Lv;
        this.skill_points = Skill_Points;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
        this.data4 = data4;
        this.data5 = data5;
        this.data6 = data6;
        this.money_skill_lv=money_skill_lv;
        this.critical_lv=critical_lv;
        this.healing_lv=healing_lv;
        this.dodge_lv=dodge_lv;
        this.miracle_of_live_lv=miracle_of_live_lv;
        this.great_mage_lv=great_mage_lv;
        this.exercise_lv=exercise_lv;
    }

    public String return_coin(){return this.coin;}
    public String return_data1(){return this.data1;}
    public String return_data2(){return this.data2;}
    public String return_data3(){return this.data3;}
    public String return_data4(){return this.data4;}
    public String return_data5(){return this.data5;}
    public String return_data6(){return this.data6;}
    public String return_hp_lv(){return this.hp_lv;}
    public String return_magic_power(){return this.magic_power;}
    public String return_need_cash_to_up_hp_lv(){return this.need_cash_to_up_hp_lv;}
    public String return_need_cash_to_up_magic_power(){return this.need_cash_to_up_magic_power;}
    public String return_skill_poins(){return this.skill_points;}
    public String return_what_region_save(){return this.what_region_save;}
    public void set_value(int i,String data){
        if(i==0){
            this.coin=data;
        }
        if(i==1){
            this.critical_lv=data;
        }
        if(i==2){
            this.data1=data;
        }
        if(i==3){
            this.data2=data;
        }
        if(i==4){
            this.data3=data;
        }
        if(i==5){
            this.data4=data;
        }
        if(i==6){
            this.data5=data;
        }
        if(i==7){
            this.data6=data;
        }
        if(i==8){
            this.dodge_lv=data;
        }
        if(i==9){
            this.exercise_lv=data;
        }
        if(i==10){
            this.great_mage_lv=data;
        }
        if(i==11){
            this.healing_lv=data;
        }
        if(i==12){
            this.hp_lv=data;
        }
        if(i==13){
            this.magic_power=data;
        }
        if(i==14){
            this.miracle_of_live_lv=data;
        }
        if(i==15){
            this.money_skill_lv=data;
        }
        if(i==16){
            this.need_cash_to_up_hp_lv=data;
        }
        if(i==17){
            this.need_cash_to_up_magic_power=data;
        }
        if(i==18){
            this.skill_points=data;
        }
        if(i==19){
            this.what_region_save=data;
        }
    }


}