package com.example.user562.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class StartActivity extends Dialog {


    public StartActivity(@NonNull Context context) {
        super(context);
        setContentView(R.layout.activity_start);
    }
}
