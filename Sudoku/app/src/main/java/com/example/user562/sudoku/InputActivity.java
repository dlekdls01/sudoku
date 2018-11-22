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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class InputActivity extends Activity {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    // MNIST 모델 불러오기
    private static final String MODEL_FILE = "file:///android_asset/mnist.pb";

    private PredictionTF mnist;
    ArrayList<Point> points = new ArrayList<Point>();
    Draw draw;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        draw = new Draw(this);

        layout = (LinearLayout)findViewById(R.id.layout);
        layout.addView(draw);

        mnist = new PredictionTF(getAssets(),MODEL_FILE);
    }

    public void onClickOK(View v){
        layout.setDrawingCacheEnabled(true);
        layout.buildDrawingCache();
        Bitmap bitmap = layout.getDrawingCache();

        if(bitmap == null) return;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap bmOut = Bitmap.createBitmap(width, height, bitmap.getConfig());
        int pixel, A, R, G, B;

        for(int x=0; x<height; x++){
            for(int y =0; y<width; y++){
                pixel = bitmap.getPixel(x, y);

                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                R = R^255;
                G = G^255;
                B = B^255;

                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        Bitmap b = Bitmap.createScaledBitmap(bmOut,28,28,true);

        int[] digit = mnist.getPredict(b);
        String res = String.valueOf(digit[0]);

        Intent intent = new Intent();
        intent.putExtra("DIGIT", digit[0]);

        setResult(RESULT_OK,intent);

        finish();
    }
    public void onClickCancel(View v){
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }
    class Point {
        float x;
        float y;
        boolean isDraw;

        public Point(float x, float y, boolean isDraw) {
            this.x = x;
            this.y = y;
            this.isDraw = isDraw;
        }
    }
    class Draw extends View{
        public Draw(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);

            Paint paint = new Paint(){
                {
                    setStyle(Paint.Style.STROKE);
                    setStrokeCap(Paint.Cap.ROUND);
                    setStrokeWidth(3.0f);
                    setAntiAlias(true);
                    setStrokeWidth(50);
                }
            };

            for(int i=1; i<points.size(); i++){
                paint.setColor(Color.BLACK);
                if(!points.get(i).isDraw)
                    continue;
                canvas.drawLine(points.get(i-1).x,points.get(i-1).y,points.get(i).x,points.get(i).y,paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                points.add(new Point(x,y,false ));
            case MotionEvent.ACTION_MOVE :
                points.add(new Point(x,y,true ));
                break;
            case MotionEvent.ACTION_UP :
                break;
        }

        draw.invalidate();
        return true;

    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
