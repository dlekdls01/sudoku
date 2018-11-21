package com.example.user562.sudoku;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }

    public void onClick(View v){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_start);

        TextView txtv_stage = (TextView)dialog.findViewById(R.id.txt_stage);
        txtv_stage.setText(((TextView)v).getText().toString());
        Button btn_start = (Button)dialog.findViewById(R.id.btn_start);
        Button btn_exit = (Button)dialog.findViewById(R.id.btn_exit);
        final String stage = ((TextView)v).getText().toString().substring(5);

        btn_start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("STAGE",stage);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();



    }
}
