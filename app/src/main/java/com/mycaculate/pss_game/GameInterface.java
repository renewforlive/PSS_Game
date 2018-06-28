package com.mycaculate.pss_game;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    ImageButton btn_paper,btn_sci,btn_stone,btn_rdn;
    ImageView myCharacter,hisCharacter;
    ImageView pic1,pic2;
    TextView showFoot;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    private int pss_id,his_pss_id,my_decision,his_decision,random, foot_rdn;
    private int myrandom,hisrandom;
    //隨機步數按鈕，贏、輸、或平手
    boolean bool_rdn_btn = false,win=false,lose=false,dual=false;
    //角色圖片變動
    private int[] char_id = new int[]{R.mipmap.lady_01,R.mipmap.lady_02,R.mipmap.lady_03,R.mipmap.lady_04};;

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


        if(win == true){
            Thread characterMove = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(1000);
                        for (int i =0; i<4; i++){
                            Thread.sleep(500);
                            handler.sendEmptyMessage(3);
                        }


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            characterMove.start();
        }
        if(lose== true){

        }

        if(dual==true){

        }

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
                    for (int i=0; i<10; i++){
                        Thread.sleep(100);
                        handler.sendEmptyMessage(1);
                    }
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
                winnerCondition();
            }
            else if(msg.what==3){


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
}
