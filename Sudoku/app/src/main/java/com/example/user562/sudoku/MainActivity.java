/*
   Copyright 2018 A+Gang. All Rights Reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.example.user562.sudoku;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
