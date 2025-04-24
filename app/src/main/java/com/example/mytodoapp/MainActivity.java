package com.example.mytodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Set your layout

        // Find the buttons by ID
        Button routineButton = findViewById(R.id.button);
        Button tasksButton = findViewById(R.id.button2);

        // Set onClick listener for the "Routine" button
        routineButton.setOnClickListener(v -> {
            // Create an Intent to start SecondActivity
            Intent intent = new Intent(MainActivity.this,MainActivity2.class);
            startActivity(intent);  // Start the new activity
        });

        // Set onClick listener for the "Tasks" button
        tasksButton.setOnClickListener(v -> {
            // Create an Intent to start AnotherActivity (ensure you create this activity first)
            Intent intent = new Intent(MainActivity.this,MainActivity3.class);
            startActivity(intent);  // Start the new activity
        });
    }
}
