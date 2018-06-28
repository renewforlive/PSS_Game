package com.mycaculate.pss_game;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class GameInterface extends AppCompatActivity implements View.OnClickListener{
    ImageButton btn_paper,btn_sci,btn_stone;
    Button btn_rdn;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_interface);

        btn_sci = findViewById(R.id.btn_scissor);
        btn_stone = findViewById(R.id.btn_stone);
        btn_paper = findViewById(R.id.btn_paper);
        btn_sci.setOnClickListener(this);
        btn_stone.setOnClickListener(this);
        btn_paper.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.fight_area,null);
        builder = new AlertDialog.Builder(this);

    }
}
