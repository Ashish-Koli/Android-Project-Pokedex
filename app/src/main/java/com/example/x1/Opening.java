package com.example.x1;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class Opening extends AppCompatActivity {

    private CardView btnStart, btnQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_opening);


        btnStart = findViewById(R.id.button);
        btnQuiz = findViewById(R.id.button1);

        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(Opening.this, MainActivity.class);
            startActivity(intent);
        });
        btnQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(Opening.this, GameActivity.class);
            startActivity(intent);
        });
    }






}