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

public class MainActivity extends Activity {

    private PaintView paintView;
    private FrameLayout frameLayout;

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
    }

    private void setupColorPalette() {
        GridView paletadeColores = new GridView(this);
        paletadeColores.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        paletadeColores.setNumColumns(GridView.AUTO_FIT);
        paletadeColores.setColumnWidth(GridView.AUTO_FIT);
        paletadeColores.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        paletadeColores.setGravity(Gravity.CENTER);
        paletadeColores.setHorizontalSpacing(10);
        paletadeColores.setVerticalSpacing(10);
        paletadeColores.setPadding(10, 10, 10, 10);

        final String[] colors = new String[]{"Black", "Red", "Green", "Blue", "Yellow", "Purple", "Cyan"};
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