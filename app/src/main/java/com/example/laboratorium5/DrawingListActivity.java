package com.example.laboratorium5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DrawingListActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> drawings = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_list);

        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, drawings);
        listView.setAdapter(adapter);

        loadDrawings();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileName = drawings.get(position);
                File directory = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Drawings");
                File file = new File(directory, fileName);
                Intent intent = new Intent(DrawingListActivity.this, MainActivity.class);
                intent.putExtra("filePath", file.getAbsolutePath());
                startActivity(intent);
            }
        });

    }

    private void loadDrawings() {
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Drawings");
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    Log.d("DrawingListActivity", "File found: " + file.getAbsolutePath());
                    drawings.add(file.getName());
                }
                adapter.notifyDataSetChanged();
            } else {
                Log.e("DrawingListActivity", "No files found in directory");
            }
        } else {
            Log.e("DrawingListActivity", "Directory does not exist");
        }
    }
}
