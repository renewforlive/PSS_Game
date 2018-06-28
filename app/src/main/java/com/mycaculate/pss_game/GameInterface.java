package com.mycaculate.pss_game;

import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class GameInterface extends AppCompatActivity implements View.OnClickListener{
    //有四個ImageButton，剪刀石頭布、隨機步數按鈕
    ImageButton btn_paper,btn_sci,btn_stone,btn_rdn;
    //有兩個ImageView，兩個角色
    ImageView myCharacter,hisCharacter;
    //有兩個ImageView，對決時的雙方
    ImageView pic1,pic2;
    //有一個TextView，顯示步數
    TextView showFoot;
    //宣告AlertDialog
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    //宣告我方和對方的剪刀石頭布對應的圖片、雙方最後的決定、隨機步數
    private int pss_id,his_pss_id,my_decision,his_decision,random, foot_rdn;
    //宣告雙方隨機產生的數字1~3
    private int myrandom,hisrandom;
    //隨機步數按鈕，贏、輸、或平手的布林值
    boolean bool_rdn_btn = false,win=false,lose=false,dual=false;
    //角色圖片變動
    private int[] mychar_id = new int[]{R.mipmap.lady_01,R.mipmap.lady_02,R.mipmap.lady_03,R.mipmap.lady_04};
    private int[] hischar_id = new int[]{R.mipmap.man_01,R.mipmap.man_02,R.mipmap.man_03,R.mipmap.man_04};
    //宣告開始的角色圖片index
    private int char_img = 1;
    //宣告整個Layout
    private ConstraintLayout constraintLayout;
    //宣告Constraintset
    private ConstraintSet applyconstraintset;
    //每次移動Y軸
    private int mypos_y = 60, hispos_y = 60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_interface);

        btn_sci = findViewById(R.id.btn_scissor);
        btn_stone = findViewById(R.id.btn_stone);
        btn_paper = findViewById(R.id.btn_paper);
        btn_rdn = findViewById(R.id.btn_rdn);

        btn_sci.setOnClickListener(this);
        btn_stone.setOnClickListener(this);
        btn_paper.setOnClickListener(this);
        btn_rdn.setOnClickListener(this);

        showFoot = findViewById(R.id.showFoot);
        myCharacter = findViewById(R.id.mycharacter);
        hisCharacter=findViewById(R.id.hischaracter);

        constraintLayout = findViewById(R.id.constraintlayout);
        applyconstraintset = new ConstraintSet();
        applyconstraintset.clone(constraintLayout);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_scissor:
                if(bool_rdn_btn == true){
                    my_decision = R.mipmap.sci_img;
                    random = (int)((Math.random() * 3))+1;
                    if (random ==1){
                        his_decision = R.mipmap.sci_img;
                    }
                    if (random ==2){
                        his_decision = R.mipmap.stone_img;
                    }
                    if (random ==3){
                        his_decision = R.mipmap.paper_img;
                    }
                    alertappend();
                    bool_rdn_btn =false;
                }

                break;
            case R.id.btn_stone:
                if(bool_rdn_btn == true){
                    my_decision = R.mipmap.stone_img;
                    random = (int)((Math.random() * 3))+1;
                    if (random ==1){
                        his_decision = R.mipmap.sci_img;
                    }
                    if (random ==2){
                        his_decision = R.mipmap.stone_img;
                    }
                    if (random ==3){
                        his_decision = R.mipmap.paper_img;
                    }
                    alertappend();
                    bool_rdn_btn =false;
                }

                break;
            case R.id.btn_paper:
                if(bool_rdn_btn == true){
                    my_decision = R.mipmap.paper_img;
                    random = (int)((Math.random() * 3))+1;
                    if (random ==1){
                        his_decision = R.mipmap.sci_img;
                    }
                    if (random ==2){
                        his_decision = R.mipmap.stone_img;
                    }
                    if (random ==3){
                        his_decision = R.mipmap.paper_img;
                    }
                    alertappend();
                    bool_rdn_btn =false;
                }

                break;
            case R.id.btn_rdn:
                if(bool_rdn_btn==false){
                    foot_rdn = (int)(Math.random()*3)+1;
                    showFoot.setText(String.valueOf(foot_rdn));
                    bool_rdn_btn = true;
                }
                    break;
        }
    }
    private void alertappend(){
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.fight_area,null);
        pic1 = view.findViewById(R.id.pic1);
        pic2 = view.findViewById(R.id.pic2);

        builder = new AlertDialog.Builder(this);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    //變換圖片十次
                    for (int i=0; i<10; i++){
                        Thread.sleep(100);
                        handler.sendEmptyMessage(1);
                    }
                    //最終決定並關掉AlertDialog
                    handler.sendEmptyMessage(2);
                    Thread.sleep(2000);
                    alertDialog.dismiss();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what==1){
                changePic();
            }
            else if(msg.what==2){
                pic1.setImageResource(my_decision);
                pic2.setImageResource(his_decision);
                Thread.interrupted();
                //判斷輸贏情況
                winnerCondition();
                //執行輸贏後的內容
                winorlose_after();
            }
            else if(msg.what==3){
                myCharacter.setImageResource(mychar_id[char_img]);
                char_img++;
                if(char_img>3){
                    char_img = 0;
                }
                myMoveToPlace();
            }
            else if(msg.what==4){
                hisCharacter.setImageResource(hischar_id[char_img]);
                char_img++;
                if(char_img>3){
                    char_img = 0;
                }
                hisMoveToPlace();
            }
        }
    };
    public void changePic(){
        myrandom = (int)(Math.random()*3)+1;
        hisrandom = (int)(Math.random()*3)+1;
        if (myrandom ==1){
            pss_id= R.mipmap.sci_img;
        }else if(myrandom==2){
            pss_id= R.mipmap.stone_img;
        }
        else{
            pss_id = R.mipmap.paper_img;
        }
        if (hisrandom ==1){
            his_pss_id= R.mipmap.sci_img;
        }else if(hisrandom == 2){
            his_pss_id= R.mipmap.stone_img;
        }
        else{
            his_pss_id = R.mipmap.paper_img;
        }
        pic1.setImageResource(pss_id);
        pic2.setImageResource(his_pss_id);
    }
    public void winnerCondition(){
        if(my_decision==R.mipmap.sci_img && his_decision==R.mipmap.sci_img){
            dual= true;
            Toast.makeText(this,"雙方平手",Toast.LENGTH_LONG).show();
        }
        else if (my_decision==R.mipmap.sci_img && his_decision==R.mipmap.stone_img){
            lose=true;
            Toast.makeText(this,"你輸了",Toast.LENGTH_LONG).show();
        }
        else if (my_decision==R.mipmap.sci_img && his_decision==R.mipmap.paper_img){
            win=true;
            Toast.makeText(this,"你贏了",Toast.LENGTH_LONG).show();
        }
        else if (my_decision==R.mipmap.stone_img && his_decision==R.mipmap.sci_img){
            win=true;
            Toast.makeText(this,"你贏了",Toast.LENGTH_LONG).show();
        }
        else if (my_decision==R.mipmap.stone_img && his_decision==R.mipmap.stone_img){
            dual=true;
            Toast.makeText(this,"雙方平手",Toast.LENGTH_LONG).show();
        }
        else if (my_decision==R.mipmap.stone_img && his_decision==R.mipmap.paper_img){
            lose=true;
            Toast.makeText(this,"你輸了",Toast.LENGTH_LONG).show();
        }
        else if (my_decision==R.mipmap.paper_img && his_decision==R.mipmap.sci_img){
            lose=true;
            Toast.makeText(this,"你輸了",Toast.LENGTH_LONG).show();
        }
        else if (my_decision==R.mipmap.paper_img && his_decision==R.mipmap.stone_img){
            win=true;
            Toast.makeText(this,"你贏了",Toast.LENGTH_LONG).show();
        }
        else if (my_decision==R.mipmap.paper_img && his_decision==R.mipmap.paper_img){
            dual=true;
            Toast.makeText(this,"雙方平手",Toast.LENGTH_LONG).show();
        }
    }
    public void winorlose_after(){
        if(win == true){
            Thread characterMove = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        if(foot_rdn ==1) {
                            Thread.sleep(500);
                            for (int i = 0; i < 6; i++) {
                                Thread.sleep(500);
                                handler.sendEmptyMessage(3);
                            }
                            win =false;
                        }
                        else if(foot_rdn==2) {
                            Thread.sleep(500);
                            for (int i = 0; i < 12; i++) {
                                Thread.sleep(500);
                                handler.sendEmptyMessage(3);
                            }
                            win = false;
                        }
                        else if (foot_rdn==3){
                            Thread.sleep(500);
                            for (int i =0; i< 18; i++){
                                Thread.sleep(500);
                                handler.sendEmptyMessage(3);
                            }
                            win = false;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            characterMove.start();
        }
        if(lose== true){
            Thread characterMove = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        if(foot_rdn ==1) {
                            Thread.sleep(500);
                            for (int i = 0; i < 6; i++) {
                                Thread.sleep(500);
                                handler.sendEmptyMessage(4);
                            }
                            lose =false;
                        }
                        else if(foot_rdn==2) {
                            Thread.sleep(500);
                            for (int i = 0; i < 12; i++) {
                                Thread.sleep(500);
                                handler.sendEmptyMessage(4);
                            }
                            lose = false;
                        }
                        else if (foot_rdn==3){
                            Thread.sleep(500);
                            for (int i =0; i< 18; i++){
                                Thread.sleep(500);
                                handler.sendEmptyMessage(4);
                            }
                            lose = false;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            characterMove.start();
        }

        if(dual==true){

        }

    }
    public void myMoveToPlace(){
        TransitionManager.beginDelayedTransition(constraintLayout);
        mypos_y += 11;
        applyconstraintset.setMargin(R.id.mycharacter,ConstraintSet.BOTTOM, mypos_y);
        applyconstraintset.applyTo(constraintLayout);
    }
    public void hisMoveToPlace(){
        TransitionManager.beginDelayedTransition(constraintLayout);
        hispos_y += 11;
        applyconstraintset.setMargin(R.id.hischaracter,ConstraintSet.BOTTOM, hispos_y);
        applyconstraintset.applyTo(constraintLayout);
    }
}
