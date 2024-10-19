package com.example.x1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ScoreBoardActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score_board);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView = findViewById(R.id.scoreTextView);
        imageView  =findViewById(R.id.home);

        try {
            score = getIntent().getIntExtra("score", 0);
            Log.d("ScoreBoardActivity", "Received Score: " + score);

            textView.setText("Your Score: " + score);
        } catch (Exception e) {
            Log.e("ScoreBoardActivity", "Error in onCreate: " + e.getMessage());
        }

        setScoreMessage(score);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ScoreBoardActivity.this, Opening.class);
                startActivity(i);
            }
        });


    }


    private void setScoreMessage(int score) {
        String message;

        if (score < 20) {
            message = "Your score is low. Try exploring the Pokédex for more information!";
        } else if (score < 40) {
            message = "Good job! Keep practicing to improve your score!";
        } else if (score < 60) {
            message = "Great work! You're getting better at this!";
        } else if (score < 80) {
            message = "Excellent! You're really mastering the Pokémon!";
        } else {
            message = "Outstanding! You're a true Pokémon master!";
        }

        // Set the message to a TextView or however you want to display it
        TextView scoreMessageTextView = findViewById(R.id.remarkTextView); // Replace with your actual TextView ID
        scoreMessageTextView.setText(message);
    }
}