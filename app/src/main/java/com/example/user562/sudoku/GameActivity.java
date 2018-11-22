package com.example.user562.sudoku;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    TextView [][]arr = null;
    Chronometer time = null;
    int stage ;
    int [][][]p = {{{0,0,3,7,0,4,5,0,0},{0,2,0,0,0,0,0,6,0},{0,8,0,3,1,6,0,2,0},
                     {0,0,0,0,0,0,0,0,0},{3,7,0,0,0,0,0,9,2},{2,0,4,0,0,0,8,0,6},
                     {0,4,0,1,3,5,0,7,0},{0,5,0,0,0,0,0,4,0},{0,0,1,6,0,7,2,0,0}},
                    {{9,4,1,0,0,2,0,0,0},{0,0,0,0,0,0,0,5,0},{0,0,0,7,0,0,1,3,0},
                     {8,0,0,0,0,3,0,6,0},{3,6,2,1,0,7,5,9,4},{0,9,0,5,0,0,0,0,8},
                     {0,5,7,0,0,1,0,0,0},{0,3,0,0,0,0,0,0,0},{0,0,0,2,0,0,6,7,1}}};
    int [][][]s = {{{6,9,3,7,2,4,5,1,8},{1,2,7,8,5,9,3,6,4},{4,8,5,3,1,6,9,2,7},
                     {5,6,9,4,8,2,7,3,1},{3,7,8,5,6,1,4,9,2},{2,1,4,9,7,3,8,5,6},
                     {8,4,2,1,3,5,6,7,9},{7,5,6,2,9,8,1,4,3},{9,3,1,6,4,7,2,8,5}},
                    {{9,4,1,3,5,2,7,8,9},{6,7,3,8,1,9,4,5,2},{5,2,8,7,6,4,1,2,9},
                     {8,1,5,9,4,3,2,6,7},{3,6,2,1,8,7,5,9,4},{7,9,4,5,2,6,3,1,8},
                     {2,5,7,6,9,1,8,4,3},{1,3,6,4,7,8,9,2,5},{4,8,9,2,3,4,6,7,1}}};;

    public void SetTextView(){

        arr[0][0] = (TextView)findViewById(R.id.txt1);
        arr[0][1] = (TextView)findViewById(R.id.txt2);
        arr[0][2] = (TextView)findViewById(R.id.txt3);
        arr[0][3] = (TextView)findViewById(R.id.txt4);
        arr[0][4] = (TextView)findViewById(R.id.txt5);
        arr[0][5] = (TextView)findViewById(R.id.txt6);
        arr[0][6] = (TextView)findViewById(R.id.txt7);
        arr[0][7] = (TextView)findViewById(R.id.txt8);
        arr[0][8] = (TextView)findViewById(R.id.txt9);
        arr[1][0] = (TextView)findViewById(R.id.txt11);
        arr[1][1] = (TextView)findViewById(R.id.txt12);
        arr[1][2] = (TextView)findViewById(R.id.txt13);
        arr[1][3] = (TextView)findViewById(R.id.txt14);
        arr[1][4] = (TextView)findViewById(R.id.txt15);
        arr[1][5] = (TextView)findViewById(R.id.txt16);
        arr[1][6] = (TextView)findViewById(R.id.txt17);
        arr[1][7] = (TextView)findViewById(R.id.txt18);
        arr[1][8] = (TextView)findViewById(R.id.txt19);
        arr[2][0] = (TextView)findViewById(R.id.txt21);
        arr[2][1] = (TextView)findViewById(R.id.txt22);
        arr[2][2] = (TextView)findViewById(R.id.txt23);
        arr[2][3] = (TextView)findViewById(R.id.txt24);
        arr[2][4] = (TextView)findViewById(R.id.txt25);
        arr[2][5] = (TextView)findViewById(R.id.txt26);
        arr[2][6] = (TextView)findViewById(R.id.txt27);
        arr[2][7] = (TextView)findViewById(R.id.txt28);
        arr[2][8] = (TextView)findViewById(R.id.txt29);
        arr[3][0] = (TextView)findViewById(R.id.txt31);
        arr[3][1] = (TextView)findViewById(R.id.txt32);
        arr[3][2] = (TextView)findViewById(R.id.txt33);
        arr[3][3] = (TextView)findViewById(R.id.txt34);
        arr[3][4] = (TextView)findViewById(R.id.txt35);
        arr[3][5] = (TextView)findViewById(R.id.txt36);
        arr[3][6] = (TextView)findViewById(R.id.txt37);
        arr[3][7] = (TextView)findViewById(R.id.txt38);
        arr[3][8] = (TextView)findViewById(R.id.txt39);
        arr[4][0] = (TextView)findViewById(R.id.txt41);
        arr[4][1] = (TextView)findViewById(R.id.txt42);
        arr[4][2] = (TextView)findViewById(R.id.txt43);
        arr[4][3] = (TextView)findViewById(R.id.txt44);
        arr[4][4] = (TextView)findViewById(R.id.txt45);
        arr[4][5] = (TextView)findViewById(R.id.txt46);
        arr[4][6] = (TextView)findViewById(R.id.txt47);
        arr[4][7] = (TextView)findViewById(R.id.txt48);
        arr[4][8] = (TextView)findViewById(R.id.txt49);
        arr[5][0] = (TextView)findViewById(R.id.txt51);
        arr[5][1] = (TextView)findViewById(R.id.txt52);
        arr[5][2] = (TextView)findViewById(R.id.txt53);
        arr[5][3] = (TextView)findViewById(R.id.txt54);
        arr[5][4] = (TextView)findViewById(R.id.txt55);
        arr[5][5] = (TextView)findViewById(R.id.txt56);
        arr[5][6] = (TextView)findViewById(R.id.txt57);
        arr[5][7] = (TextView)findViewById(R.id.txt58);
        arr[5][8] = (TextView)findViewById(R.id.txt59);
        arr[6][0] = (TextView)findViewById(R.id.txt61);
        arr[6][1] = (TextView)findViewById(R.id.txt62);
        arr[6][2] = (TextView)findViewById(R.id.txt63);
        arr[6][3] = (TextView)findViewById(R.id.txt64);
        arr[6][4] = (TextView)findViewById(R.id.txt65);
        arr[6][5] = (TextView)findViewById(R.id.txt66);
        arr[6][6] = (TextView)findViewById(R.id.txt67);
        arr[6][7] = (TextView)findViewById(R.id.txt68);
        arr[6][8] = (TextView)findViewById(R.id.txt69);
        arr[7][0] = (TextView)findViewById(R.id.txt71);
        arr[7][1] = (TextView)findViewById(R.id.txt72);
        arr[7][2] = (TextView)findViewById(R.id.txt73);
        arr[7][3] = (TextView)findViewById(R.id.txt74);
        arr[7][4] = (TextView)findViewById(R.id.txt75);
        arr[7][5] = (TextView)findViewById(R.id.txt76);
        arr[7][6] = (TextView)findViewById(R.id.txt77);
        arr[7][7] = (TextView)findViewById(R.id.txt78);
        arr[7][8] = (TextView)findViewById(R.id.txt79);
        arr[8][0] = (TextView)findViewById(R.id.txt81);
        arr[8][1] = (TextView)findViewById(R.id.txt82);
        arr[8][2] = (TextView)findViewById(R.id.txt83);
        arr[8][3] = (TextView)findViewById(R.id.txt84);
        arr[8][4] = (TextView)findViewById(R.id.txt85);
        arr[8][5] = (TextView)findViewById(R.id.txt86);
        arr[8][6] = (TextView)findViewById(R.id.txt87);
        arr[8][7] = (TextView)findViewById(R.id.txt88);
        arr[8][8] = (TextView)findViewById(R.id.txt89);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        stage = Integer.parseInt(intent.getStringExtra("STAGE"));

        TextView txt_stage = (TextView)findViewById(R.id.txt_stage);
        txt_stage.setText(txt_stage.getText().toString() + intent.getStringExtra("STAGE"));

        time = (Chronometer)findViewById(R.id.time);
        time.start();

        arr = new TextView[9][9];
        SetTextView();
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                arr[i][j].setHeight(100);
                arr[i][j].setGravity(Gravity.CENTER);
                if(p[stage-1][i][j] != 0)
                    arr[i][j].setText(String.valueOf(p[stage - 1][i][j]));
                else {
                    arr[i][j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(GameActivity.this, InputActivity.class);
                            startActivity(intent);

                        }
                    });
                }
            }
        }


    }
}
