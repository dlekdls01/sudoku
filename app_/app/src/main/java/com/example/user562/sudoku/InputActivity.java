package com.example.user562.sudoku;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import java.util.ArrayList;

public class InputActivity extends Activity {

    private Mnist mnist = new Mnist();
    ArrayList<Point> points = new ArrayList<Point>();
    Draw draw;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        
        boolean ret = mnist.setup(this);
        if(!ret)
            Log.d("super","3fail");

        draw = new Draw(this);
        layout = (LinearLayout)findViewById(R.id.layout);
        layout.addView(draw);

    }

    public void onClickOK(View v){
        Log.d("super","1111111");
        layout.setDrawingCacheEnabled(true);
        layout.buildDrawingCache();
        Bitmap bitmap = layout.getDrawingCache();
        //Bitmap bitmap = v.getDrawingCache();
        if(bitmap == null) {
            Log.d("super","rere");
            return;
        }

        Log.d("super","2222222222");
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int[] retPixels = new int[pixels.length];

        Log.d("super","333333333");
        for (int i = 0; i < pixels.length; ++i) {
            int pix = pixels[i];
            int b = pix & 0xff;
            retPixels[i] = 0xff - b;
        }
        Log.d("super","33ffffffffff");
        int digit = mnist.detectDigit(retPixels);

        Log.d("super","444444444");
        Intent intent = new Intent();
        intent.putExtra("DIGIT",digit);
        setResult(RESULT_OK,intent);
        finish();

    }
    public void onClickCancel(View v){
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
            Paint paint = new Paint();
            paint.setStrokeWidth(10);
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


}
