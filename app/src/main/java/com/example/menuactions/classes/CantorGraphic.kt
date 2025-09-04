package com.example.menuactions.classes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class CantorGraphic(context: Context) :  View(context){
    private val paint = Paint();
    private val TAG : String = "Graphic";


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.strokeWidth = 5f

        val width = canvas.width
        val height = canvas.height
        var red = 227;
        var green = 225;
        var blue = 240;

        paint.color = Color.rgb(red, green, blue);
        drawCantor(
            startX = 0f,
            endX = width.toFloat(),
            y = height / 2f - 50,
            nivel = 10,
            dy = 100f,
            canvas = canvas
        )
    }

    private fun drawCantor(
        startX: Float,
        endX: Float,
        y: Float,
        nivel: Int,
        dy: Float,
        canvas: Canvas
    ) {
        if (nivel <= 0) return
        canvas.drawLine(startX, y, endX, y, paint)

        val t = (endX - startX) / 3
        val nextDy = dy * 0.7f;


        drawCantor(startX, startX + t, y + dy, nivel - 1, nextDy, canvas)
        drawCantor(startX + 2 * t, endX, y + dy, nivel - 1, nextDy, canvas)
    }
}