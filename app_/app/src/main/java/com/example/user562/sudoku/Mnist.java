package com.example.user562.sudoku;

/**
 * Created by user562 on 2018-11-21.
 */

import android.content.res.AssetManager;
import android.content.Context;
import android.util.Log;

import java.io.IOException;

import static android.util.Log.*;

public class Mnist {


    static {
        System.loadLibrary("tensorflow_mnist");
    }
    private native int init(AssetManager assetManager, String model);
    public native int detectDigit(int[] pixels);

    public boolean setup(Context context){
        Log.d("super","FFFFFFFFFFFFFF");
        AssetManager assetManager = context.getAssets();
        int ret = 0;
        ret = init(assetManager, "file:///android_asset/expert-graph.pb");
        Log.d("super",String.valueOf(ret));
        return ret >= 0;
    }
}
