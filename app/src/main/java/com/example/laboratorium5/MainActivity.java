package com.example.laboratorium5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity{

    private DrawingSurface drawingSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the activity title
        getSupportActionBar().setTitle("AndroidGrafika2");

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

        // Check if there is an intent with a file path and load the image
        Intent intent = getIntent();
        if (intent.hasExtra("filePath")) {
            String filePath = intent.getStringExtra("filePath");
            loadImage(filePath);
        }
    }

    private void loadImage(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        if (bitmap != null) {
            Log.d("MainActivity", "Bitmap loaded successfully from path: " + filePath);
            drawingSurface.setBitmap(bitmap);
        } else {
            Log.e("MainActivity", "Failed to load bitmap from path: " + filePath);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            saveDrawing();
            return true;
        } else if (id == R.id.action_list) {
            Intent intent = new Intent(this, DrawingListActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveDrawing() {
        Bitmap bitmap = drawingSurface.getBitmap();
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Drawings");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String fileName = "drawing_" + System.currentTimeMillis() + ".png";
        File file = new File(directory, fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(this, "Drawing saved: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "Drawing saved at: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save drawing", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Failed to save drawing", e);
        }
    }
}
