package com.example.laboratorium5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DrawingSurface drawingSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingSurface = findViewById(R.id.drawingSurface);

        Button buttonRed = findViewById(R.id.buttonRed);
        Button buttonYellow = findViewById(R.id.buttonYellow);
        Button buttonBlue = findViewById(R.id.buttonBlue);
        Button buttonGreen = findViewById(R.id.buttonGreen);
        Button buttonClear = findViewById(R.id.buttonClear);

        buttonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingSurface.setColor(0xFFFF0000);
            }
        });

        buttonYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingSurface.setColor(0xFFFFFF00); // Ustaw kolor na żółty
            }
        });

        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingSurface.setColor(0xFF0000FF);
            }
        });

        buttonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingSurface.setColor(0xFF00FF00);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingSurface.clear();
            }
        });
    }
}
