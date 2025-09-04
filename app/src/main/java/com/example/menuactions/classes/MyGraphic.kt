package com.example.menuactions.classes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.View

class MyGraphic(context: Context) : View(context){
    private val paint = Paint();
    private val TAG : String = "Graphic";

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.BLACK;
        paint.strokeWidth = 5f;
//        canvas.drawLine(100f, 100f, 500f, 500f, paint);
//        for (i in 0..5){
//
//        }
        val width = canvas.width;
        val height = canvas.height;

        canvas.drawLine(0f, height/2f, width.toFloat(), height/2f, paint);
        canvas.drawLine(width/2f, 0f, width/2f, height.toFloat(), paint);


        val limInx = -20f;
        val limSpX = 20f;
        val limInY= -20f;
        val limSpY = 20f;
        var x = limInx;

        var radius = 10;

        while (x <= limSpX){
            //val y = x * (1/Math.tan(x.toDouble()));
            val y = - Math.sqrt(Math.pow(radius.toDouble(), 2.0) - Math.pow(x.toDouble(), 2.0));
            val xt = ((x - limInx)/(limSpX - limInx) )* width;
            val yt = height - ((y - limInY)/(limSpY - limInY) )* height;

            canvas.drawCircle(xt, yt.toFloat(), 5f, paint);
            //Log.d(TAG, "x: $x, y: $y,xt => $xt, yt => $yt");
            x += 0.001f;
        }

    }
}