package com.aoaruche.tw.je.je_fighter_basic;

import android.graphics.Rect;
import android.util.Log;

import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.Enemy_Type_1;
import static com.aoaruche.tw.je.je_fighter_basic.Main_Fight_Sense.Max_Width;

/**
 * Created by JE-Chen on 2018/4/3.
 */

public class JExEnemy_AI {

    public int Target_X;
    public int Target_Y;
    public JE_Fight_Fighter_Enemy_Factory AI_Owner;
    public JExMain_Fighter_Wings_of_Hope Main_Target;
    public JExCollision Collision;

    //抵達行為

    public Rect Main_Rect;

    //逃避行為
    public boolean Dodge_Flag=false;
    public int[] Dodge={0,0,0};
    public int Dodge_Delay=0;
    public int Dodge_Count=0;
    public int Dodge_Run_Count=0;

    //預設逃避

    public boolean Default_Flag=false;
    public int[] Default={0,0,0};
    public int Default_Delay=0;
    public int Default_Count=0;
    public int Default_Run_Count=0;

    //加速度
    public int Add_Speed_Delay=0;
    public int A;

    //追逐行為
    public int[] Chase={0,0,0};
    public int Chase_Delay=0;
    public int Chase_Count=0;
    public int Chase_Run_Count=0;

    //分離行為
    public boolean Separation=false;
    public int Separation_Fighter_Number_Object;

    //狀態
    public static int State=0;

    //優先權
    public int Priority;
    //虛擬視覺區
    public Rect Virtual_Vision;
    //碰撞偵測
    public JExCollision JExCollision =new JExCollision();

    public JExEnemy_AI(JE_Fight_Fighter_Enemy_Factory AI_Owner, JExMain_Fighter_Wings_of_Hope Main_Target){

    this.Target_X=(Main_Target.Return_X());
    this.Target_Y=(Main_Target.Return_Y());
    this.AI_Owner=AI_Owner;
    this.Main_Target=Main_Target;
    Virtual_Vision = new Rect();
    Virtual_Vision.set(AI_Owner.Object_x-AI_Owner.Object_w,AI_Owner.Object_y,AI_Owner.Object_x+AI_Owner.Object_w,AI_Owner.Object_y+(AI_Owner.Object_h*5));
    Main_Rect=new Rect();
    Main_Rect.set(Main_Target.Return_X()-(Main_Target.Return_W()*3),Main_Target.Return_Y()-(Main_Target.Return_H()*3),
            Main_Target.Return_X()+(Main_Target.Return_W()*3),Main_Target.Return_Y()+(Main_Target.Return_H()*3));
        A = Get_A(1,1);
        Collision=new JExCollision();
    }

    public JExEnemy_AI(JE_Fight_Fighter_Enemy_Factory AI_Owner, JExMain_Fighter_Wings_of_Hope Main_Target,int State){

        this.Target_X=(Main_Target.Return_X());
        this.Target_Y=(Main_Target.Return_Y());
        this.AI_Owner=AI_Owner;
        this.Main_Target=Main_Target;
        Virtual_Vision = new Rect();
        Virtual_Vision.set(AI_Owner.Object_x-AI_Owner.Object_w,AI_Owner.Object_y,AI_Owner.Object_x+AI_Owner.Object_w,AI_Owner.Object_y+(AI_Owner.Object_h*5));
        Main_Rect=new Rect();
        Main_Rect.set(Main_Target.Return_X()-(Main_Target.Return_W()*3),Main_Target.Return_Y()-(Main_Target.Return_H()*3),
                Main_Target.Return_X()+(Main_Target.Return_W()*3),Main_Target.Return_Y()+(Main_Target.Return_H()*3));
        A = Get_A(1,1);
        this.State=State;
        Collision=new JExCollision();
    }


    public void AI_Think(){
        Main_Rect.set(Main_Target.Return_X()-(Main_Target.Return_W()*3),Main_Target.Return_Y()-(Main_Target.Return_H()*3),
                Main_Target.Return_X()+(Main_Target.Return_W()*3),Main_Target.Return_Y()+(Main_Target.Return_H()*3));
        Virtual_Vision.set(AI_Owner.Object_x-AI_Owner.Object_w,AI_Owner.Object_y,AI_Owner.Object_x+AI_Owner.Object_w,AI_Owner.Object_y+(AI_Owner.Object_h*5));

        for (int i=0;i<Enemy_Type_1.size();i++) {
            if (AI_Owner.Get_Number() == Enemy_Type_1.get(i).Get_Number())
                continue;
                if(Collision.Is_Collision_AI(AI_Owner.Object_x, AI_Owner.Object_y, AI_Owner.Object_w, AI_Owner.Object_h,
                        Enemy_Type_1.get(i).Object_x, Enemy_Type_1.get(i).Object_y, Enemy_Type_1.get(i).Object_w, Enemy_Type_1.get(i).Object_h)) {
                    Separation = true;
                    Separation_Fighter_Number_Object = i;
                }
        }
        //用來決定優先順序的 int 數
        int Priority=0;
        Priority=99999;

    //  接近行為 x 軸
    if(AI_Owner.Object_x<=Main_Target.Return_X()&&!Virtual_Vision.contains(Main_Target.Return_X(),Main_Target.Return_Y())
            &&!Virtual_Vision.contains(Main_Target.Return_X_Add_W(),Main_Target.Return_Y_Add_H())&&!AI_Owner.Fighter_Type.equals("BOSS")
            &&Main_Target.Main_HP>100) {

        Priority = 1;

        //接近行為Y軸
    }

    if(AI_Owner.Object_x>=Main_Target.Return_X()&&!Virtual_Vision.contains(Main_Target.Return_X(),Main_Target.Return_Y())
            &&!Virtual_Vision.contains(Main_Target.Return_X_Add_W(),Main_Target.Return_Y_Add_H())&&!AI_Owner.Fighter_Type.equals("BOSS")
            &&Main_Target.Main_HP>100){

        Priority=2;


    }

    if(Virtual_Vision.contains(Main_Target.Return_X(),Main_Target.Return_Y())||Virtual_Vision.contains(Main_Target.Return_X_Add_W(),Main_Target.Return_Y_Add_H())
            &&!AI_Owner.Fighter_Type.equals("BOSS")){

        Priority=3;

    }

    if(AI_Owner.Enemy_HP<=30&&!AI_Owner.Fighter_Type.equals("BOSS")){

        Priority=4;

    }

    if(Main_Target.Main_HP<=100&&!AI_Owner.Fighter_Type.equals("BOSS")){
        if(Virtual_Vision.contains(Main_Target.Return_X(),Main_Target.Return_Y())||Virtual_Vision.contains(Main_Target.Return_X_Add_W(),Main_Target.Return_Y_Add_H()))
        Priority=5;

    }

    if(AI_Owner.Fighter_Type.equals("BOSS")){

        Priority=6;

    }

    if(Separation){

        Priority=7;

    }


        if(State!=0)
            Priority=99999;

    AI_Act(Priority);

    }

    public void AI_Act(int Act) {

        switch (Act) {

            //接近 x軸增加 (跟著玩家移動x軸 如果玩家在後面則碰撞)
            case 1:

                AI_Owner.Object_x += AI_Owner.speed;

                if(AI_Owner.Object_y>Main_Target.Return_Y_Add_H())
                    AI_Owner.Object_y-=AI_Owner.speed;

                Delay_Add_Speed();
                AI_Owner.Collision_Rect.set( AI_Owner.Object_x, AI_Owner.Object_y, AI_Owner.Object_x+ AI_Owner.Object_w, AI_Owner.Object_y+ AI_Owner.Object_h);
                break;
            //接近 x軸減少 (跟著玩家移動x軸 如果玩家在後面則碰撞)
            case 2:

                AI_Owner.Object_x -= AI_Owner.speed;
                if(AI_Owner.Object_y>Main_Target.Return_Y_Add_H())
                    AI_Owner.Object_y-=AI_Owner.speed;

                Delay_Add_Speed();
                AI_Owner.Collision_Rect.set( AI_Owner.Object_x, AI_Owner.Object_y, AI_Owner.Object_x+ AI_Owner.Object_w, AI_Owner.Object_y+ AI_Owner.Object_h);
                break;

                //離開 + 逃避行為 (玩家靠近往反方向跑)
            case 3:

                if(Max_Width-AI_Owner.Object_x<(AI_Owner.Object_w+15)) {
                    Dodge_Flag=false;
                }
                if(0+AI_Owner.Object_x<(AI_Owner.Object_w+15)){
                    Dodge_Flag=true;
                }

                if(Dodge_Delay!=50){
                    Dodge_Delay+=5;
                }else if(Dodge_Count!=3){
                    Dodge_Delay=0;
                    Dodge[Dodge_Count]=Main_Target.Return_X();
                    Dodge_Count+=1;
                }
                if(Dodge_Count==3){

                    if(Dodge[2]>Dodge[0]){

                        if(!Dodge_Flag)
                            AI_Owner.Object_x -= AI_Owner.speed;

                    }else if(Dodge[2]<Dodge[0]){

                        if(Dodge_Flag)
                            AI_Owner.Object_x += AI_Owner.speed;


                    }
                    else if(Dodge[2]==Dodge[0]){

                        if(Dodge_Flag)
                            AI_Owner.Object_x += AI_Owner.speed;
                        if(!Dodge_Flag)
                            AI_Owner.Object_x -= AI_Owner.speed;

                    }
                    Dodge_Run_Count+=1;
                    if(Dodge_Run_Count==100){
                        Dodge_Count=0;
                        Dodge_Run_Count=0;
                    }
                }







                Delay_Add_Speed();
                Virtual_Vision.set(AI_Owner.Object_x-AI_Owner.Object_w,AI_Owner.Object_y,AI_Owner.Object_x+AI_Owner.Object_w,AI_Owner.Object_y+(AI_Owner.Object_h*5));
                AI_Owner.Collision_Rect.set( AI_Owner.Object_x, AI_Owner.Object_y, AI_Owner.Object_x+ AI_Owner.Object_w, AI_Owner.Object_y+ AI_Owner.Object_h);
                break;

                //接近行為 (血量過少 強制撞玩家)
            case 4 :

                if(AI_Owner.Object_x<=Main_Target.Return_X_Reduce_W()){
                    AI_Owner.Object_x += AI_Owner.speed;

                }else if(AI_Owner.Object_x>=Main_Target.Return_X_Add_W()){

                    AI_Owner.Object_x -= AI_Owner.speed;

                }

                if(AI_Owner.Object_y<Main_Target.Return_Y_Reduce_H())
                    AI_Owner.Object_y+=AI_Owner.speed;
                if(AI_Owner.Object_y>Main_Target.Return_Y_Add_H())
                    AI_Owner.Object_y-=AI_Owner.speed;

                Delay_Add_Speed();
                AI_Owner.Collision_Rect.set( AI_Owner.Object_x, AI_Owner.Object_y, AI_Owner.Object_x+ AI_Owner.Object_w, AI_Owner.Object_y+ AI_Owner.Object_h);

                break;

                //追逐行為 (預測玩家位置 如無移動在視覺內則至玩家位置)
            case 5:

                if(Chase_Delay!=50) {
                    Chase_Delay += 5;
                }else if(Chase_Count!=3){
                    Chase_Delay=0;
                   Chase[Chase_Count]=Main_Target.Return_X();
                   Chase_Count+=1;
                }
                if(Chase_Count==3){

                    if(Chase[2]>Chase[0]){

                        if(AI_Owner.Object_x<=(Main_Target.Return_X_Reduce_W()-AI_Owner.Object_w)){
                            AI_Owner.Object_x += AI_Owner.speed;
                        }
                        if(AI_Owner.Object_x>=Main_Target.Return_X_Add_W()+(Main_Target.Return_W())){
                            AI_Owner.Object_x -= AI_Owner.speed;

                        }

                        if(AI_Owner.Object_y<Main_Target.Return_Y_Reduce_H())
                            AI_Owner.Object_y+=AI_Owner.speed;
                        if(AI_Owner.Object_y>Main_Target.Return_Y_Add_H())
                            AI_Owner.Object_y-=AI_Owner.speed;

                        Delay_Add_Speed();
                        AI_Owner.Collision_Rect.set( AI_Owner.Object_x, AI_Owner.Object_y, AI_Owner.Object_x+ AI_Owner.Object_w, AI_Owner.Object_y+ AI_Owner.Object_h);

                    }

                    if(Chase[2]<Chase[0]){

                        if(AI_Owner.Object_x<=(Main_Target.Return_X_Reduce_W()-AI_Owner.Object_w)){

                            AI_Owner.Object_x += AI_Owner.speed;
                        }
                         if(AI_Owner.Object_x>=Main_Target.Return_X_Add_W()){

                            AI_Owner.Object_x -= AI_Owner.speed;

                        }

                        if(AI_Owner.Object_y<Main_Target.Return_Y_Reduce_H())
                            AI_Owner.Object_y+=AI_Owner.speed;
                        if(AI_Owner.Object_y>Main_Target.Return_Y_Add_H())
                            AI_Owner.Object_y-=AI_Owner.speed;

                        Delay_Add_Speed();
                        AI_Owner.Collision_Rect.set( AI_Owner.Object_x, AI_Owner.Object_y, AI_Owner.Object_x+ AI_Owner.Object_w, AI_Owner.Object_y+ AI_Owner.Object_h);

                }
                    if(Chase[2]==Chase[0]){

                        if(AI_Owner.Object_x<=(Main_Target.Return_X_Reduce_W())){
                            AI_Owner.Object_x += AI_Owner.speed;

                        }
                        if(AI_Owner.Object_x>=Main_Target.Return_X_Add_W()){

                            AI_Owner.Object_x -= AI_Owner.speed;

                        }

                        if(AI_Owner.Object_y<Main_Target.Return_Y_Reduce_H())
                            AI_Owner.Object_y+=AI_Owner.speed;
                        if(AI_Owner.Object_y>Main_Target.Return_Y_Add_H())
                            AI_Owner.Object_y-=AI_Owner.speed;

                        Delay_Add_Speed();
                        AI_Owner.Collision_Rect.set( AI_Owner.Object_x, AI_Owner.Object_y, AI_Owner.Object_x+ AI_Owner.Object_w, AI_Owner.Object_y+ AI_Owner.Object_h);

                    }

                    Chase_Run_Count+=1;
                    if(Chase_Run_Count==100){
                        Chase_Count=0;
                        Chase_Run_Count=0;
                    }
                    }

                break;


                //抵達行為 與接近行為類似 但是使用負加速度 並且目標不是衝撞玩家 而是玩家前方區域

            case 6:

                if(AI_Owner.Object_x<=Main_Target.Return_X_Reduce_W()-Main_Target.Return_W()){
                    AI_Owner.Object_x += AI_Owner.speed;

                }else if(AI_Owner.Object_x>=Main_Target.Return_X_Add_W()+Main_Target.Return_W()){

                    AI_Owner.Object_x -= AI_Owner.speed;

                }

                if(AI_Owner.Object_y<Main_Target.Return_Y_Reduce_H()-AI_Owner.Object_h)
                    AI_Owner.Object_y+=AI_Owner.speed;
                if(AI_Owner.Object_y>Main_Target.Return_Y_Add_H()+AI_Owner.Object_h)
                    AI_Owner.Object_y-=AI_Owner.speed;

                Delay_Add_Speed();

                if(Main_Rect.contains(AI_Owner.Object_x,AI_Owner.Object_y)||Main_Rect.contains(AI_Owner.Object_x+AI_Owner.Object_w,AI_Owner.Object_y+AI_Owner.Object_h))
                    Delay_Reduce_Speed();


                AI_Owner.Collision_Rect.set( AI_Owner.Object_x, AI_Owner.Object_y, AI_Owner.Object_x+ AI_Owner.Object_w, AI_Owner.Object_y+ AI_Owner.Object_h);

                break;

                //分離行為 不同個體AI如果重疊則分離

            case 7:

                 AI_Owner.Separation=true;
                 Enemy_Type_1.get(Separation_Fighter_Number_Object).Separation=true;

                 Separation = false;
               break;


                default:

                    if(Max_Width-AI_Owner.Object_x<(AI_Owner.Object_w+15)) {
                        Dodge_Flag=false;
                    }
                    if(0+AI_Owner.Object_x<(AI_Owner.Object_w+15)){
                        Dodge_Flag=true;
                    }

                    if(Dodge_Flag)
                        AI_Owner.Object_x += AI_Owner.speed;
                    if(!Dodge_Flag)
                        AI_Owner.Object_x -= AI_Owner.speed;

                    if(AI_Owner.Object_y>Main_Target.Return_Y_Add_H())
                        AI_Owner.Object_y-=AI_Owner.speed;

                    Delay_Add_Speed();
                    AI_Owner.Collision_Rect.set( AI_Owner.Object_x, AI_Owner.Object_y, AI_Owner.Object_x+ AI_Owner.Object_w, AI_Owner.Object_y+ AI_Owner.Object_h);

                    break;


                }

        }



    public void Delay_Add_Speed(){
        if(Add_Speed_Delay==5000) {
            AI_Owner.speed = Now_Speed(A, AI_Owner.speed, AI_Owner.Max_Speed,AI_Owner.Min_Speed);
            Add_Speed_Delay=0;
        }else {
            Add_Speed_Delay+=5;
        }

    }

    public void Delay_Reduce_Speed(){
        if(Add_Speed_Delay==15000) {
            AI_Owner.speed = Now_Speed(-A, AI_Owner.speed, AI_Owner.Max_Speed,AI_Owner.Min_Speed);
            Add_Speed_Delay=0;
        }else {
            Add_Speed_Delay+=5;
        }

    }

    public int Now_Speed(int A,int Speed,int Max_Speed,int Min_Speed){

        int Return_Speed=0;

        if(A+Speed>Max_Speed){
            Return_Speed=Max_Speed;
        }else {
            Return_Speed=A+Speed;
        }
        if(A+Speed<Min_Speed)
            Return_Speed=Min_Speed;

        return Return_Speed;
    }

    public double Now_Speed(double A,double Speed,double Max_Speed,double Min_Speed){

        double Return_Speed=0;

        if(A+Speed>Max_Speed){
            Return_Speed=Max_Speed;
        }else {
            Return_Speed=A+Speed;
        }
        if(A+Speed<Min_Speed)
            Return_Speed=Min_Speed;
        return Return_Speed;
    }

    public double Get_A(float f,float m){

        float A=(f/m);

        return A;

    }

    public double Get_A(double f,double m){

        double A=(f/m);

        return A;

    }

    public int Get_A(int f,int m){

        int A=(f/m);

        return A;

    }

}
