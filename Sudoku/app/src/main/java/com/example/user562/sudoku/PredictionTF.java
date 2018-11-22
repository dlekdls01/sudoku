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

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;


public class PredictionTF {
    private static final String TAG = "PredictionTF";

    private static final int IN_COL = 1;
    private static final int IN_ROW = 28*28;
    private static final int OUT_COL = 1;
    private static final int OUT_ROW = 1;

    private static final String inputName = "input/x_input";
    private static final String outputName = "output";

    TensorFlowInferenceInterface inferenceInterface;
    static {
        System.loadLibrary("tensorflow_inference");

    }

    PredictionTF(AssetManager assetManager, String modePath) {
        inferenceInterface = new TensorFlowInferenceInterface(assetManager,modePath);
    }

    /**
     * @param bitmap
     * @return
     */
    public int[] getPredict(Bitmap bitmap) {
        float[] inputdata = bitmapToFloatArray(bitmap,28,28);

        inferenceInterface.feed(inputName, inputdata, IN_COL, IN_ROW);

        String[] outputNames = new String[] {outputName};
        inferenceInterface.run(outputNames);

        int[] outputs = new int[OUT_COL*OUT_ROW];
        inferenceInterface.fetch(outputName, outputs);
        return outputs;
    }

    /**
     * @param bitmap
     * @param rx
     * @param ry
     * @return
     */
    public static float[] bitmapToFloatArray(Bitmap bitmap, int rx, int ry){
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        float scaleWidth = ((float) rx) / width;
        float scaleHeight = ((float) ry) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

        height = bitmap.getHeight();
        width = bitmap.getWidth();

        float[] result = new float[height*width];
        int k = 0;

        for(int j = 0;j < height;j++){
            for (int i = 0;i < width;i++){
                int argb = bitmap.getPixel(i,j);
                int r = Color.red(argb);
                int g = Color.green(argb);
                int b = Color.blue(argb);
                int a = Color.alpha(argb);

                assert(r==g && g==b);

                result[k++] = r / 255.0f;
            }
        }
        return result;
    }
}
