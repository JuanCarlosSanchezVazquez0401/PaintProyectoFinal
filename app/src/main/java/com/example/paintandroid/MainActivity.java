package com.example.paintandroid;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.TextView;
import android.widget.Button;


public class MainActivity extends Activity {

    private PaintView paintView;
    private FrameLayout frameLayout;

    private int anchoPx = 300;
    private boolean borradorSeleccionado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frameLayout = new FrameLayout(this);
        paintView = new PaintView(this);
        frameLayout.addView(paintView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(frameLayout);

        setupColorPalette();

        Button btnBorrar = new Button(this);
        btnBorrar.setText("Borrar");

        // Ajustar el tamaño del botón
        FrameLayout.LayoutParams buttonParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        buttonParams.gravity = Gravity.BOTTOM | Gravity.START; // Ajustar la posición del botón
        buttonParams.setMargins(16, 16, 16, 16); // Establecer márgenes
        btnBorrar.setLayoutParams(buttonParams);

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the eraser mode
                borradorSeleccionado = !borradorSeleccionado;
                if (borradorSeleccionado) {
                    paintView.setBorrador(true);
                } else {
                    paintView.setBorrador(false);
                }
            }
        });
        frameLayout.addView(btnBorrar);
    }

    private void setupColorPalette() {
        GridView paletadeColores = new GridView(this);
        paletadeColores.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        paletadeColores.setNumColumns(GridView.AUTO_FIT);
        //paletadeColores;
        paletadeColores.setColumnWidth(anchoPx); //(GridView.STRETCH_COLUMN_WIDTH); para automatico
        paletadeColores.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); // (GridView.STRETCH_COLUMN_WIDTH)
        paletadeColores.setGravity(Gravity.CENTER);
        paletadeColores.setHorizontalSpacing(0);
        paletadeColores.setVerticalSpacing(10);
        paletadeColores.setPadding(10, 10, 10, 10);

        final String[] colors = new String[]{"Negro", "Rojo", "Verde", "Azul", "Amarillo", "Magenta", "Cian"};
        final int[] colorValues = new int[]{Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, colors) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                int color = colorValues[position];
                textView.setBackgroundColor(color);
                textView.setTextColor(Color.WHITE); // Color del texto a mostrar
                return textView;
            }
        };

        paletadeColores.setAdapter(adapter);

        paletadeColores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String colorName = colors[position];
                int colorValue = colorValues[position];
                paintView.setPincelColor(colorValue);
                Toast.makeText(MainActivity.this, "Color seleccionado: " + colorName, Toast.LENGTH_SHORT).show();
            }
        });

        frameLayout.addView(paletadeColores);
    }
}