package com.example.laboratorium5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class DrawingDetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private static final String TAG = "DrawingDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_detail);

        imageView = findViewById(R.id.imageView);

        String fileName = getIntent().getStringExtra("fileName");
        if (fileName != null) {
            File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (directory != null) {
                File file = new File(directory, "Drawings/" + fileName);
                Log.d(TAG, "Looking for file at: " + file.getAbsolutePath());
                if (file.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    if (bitmap != null) {
                        Log.d(TAG, "Loaded bitmap: width=" + bitmap.getWidth() + ", height=" + bitmap.getHeight());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    } else {
                        Log.e(TAG, "Bitmap is null, failed to decode file: " + file.getAbsolutePath());
                    }
                } else {
                    Log.e(TAG, "File not found: " + file.getAbsolutePath());
                }
            } else {
                Log.e(TAG, "Directory is null");
            }
        } else {
            Log.e(TAG, "fileName is null");
        }
    }
}
