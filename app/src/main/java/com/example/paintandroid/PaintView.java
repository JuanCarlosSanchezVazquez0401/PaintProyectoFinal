package com.example.paintandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaintView extends View {

    private Map<Integer, List<Path>> pathsByColor;
    private Paint pincel;
    private Path path;

    public PaintView(Context context) {
        super(context);
        pathsByColor = new HashMap<>();
        pincel = new Paint();
        pincel.setColor(Color.BLACK);
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Map.Entry<Integer, List<Path>> entry : pathsByColor.entrySet()) {
            for (Path path : entry.getValue()) {
                pincel.setColor(entry.getKey());
                canvas.drawPath(path, pincel);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xPos = event.getX();
        float yPos = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                path.moveTo(xPos, yPos);
                addPathForColor(pincel.getColor(), path);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(xPos, yPos);
                break;
            case MotionEvent.ACTION_UP:
                // No hacer nada
                break;
            default:
                return false;
        }

        // Invalidar la vista para volver a dibujarla
        invalidate();
        return true;
    }

    public void setPincelColor(int color) {
        pincel.setColor(color);
    }

    private void addPathForColor(int color, Path path) {
        if (!pathsByColor.containsKey(color)) {
            pathsByColor.put(color, new ArrayList<Path>());
        }
        pathsByColor.get(color).add(path);
    }
}
