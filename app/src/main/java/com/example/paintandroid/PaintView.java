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
import android.widget.Button;
import android.graphics.RectF;


public class PaintView extends View {

    private Map<Integer, List<Path>> pathsByColor;
    private Paint pincel;
    private Path path;
    private Map<Path, Integer> pathColors; // Mantener el color de cada trazo en un mapa separado
    private boolean borrador = false;
    public PaintView(Context context) {
        super(context);
        pathsByColor = new HashMap<>();
        pathColors = new HashMap<>(); // Inicializar el mapa de colores de trazo
        pincel = new Paint();
        pincel.setColor(Color.BLACK);
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(5);
    }

    public void setBorrador(boolean borrador) {
        this.borrador = borrador;
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
                pathColors.put(path, pincel.getColor()); // Guardar el color actual del trazo
                break;
            case MotionEvent.ACTION_MOVE:
                if (!borrador) {
                    path.lineTo(xPos, yPos);
                } else {
                    // Borrar trazos que están dentro del área de contacto del dedo del usuario
                    for (Map.Entry<Integer, List<Path>> entry : pathsByColor.entrySet()) {
                        for (Path path : entry.getValue()) {
                            RectF bounds = new RectF();
                            path.computeBounds(bounds, true);
                            if (bounds.contains(xPos, yPos)) {
                                // Si el trazo está dentro del área de contacto, lo eliminamos
                                pathsByColor.get(entry.getKey()).remove(path);
                                break;
                            }
                        }
                    }
                }
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
