package com.example.x1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    ImageView imageView;
    ProgressBar loadingSpinner;
    private Button btnOption1, btnOption2, btnOption3, btnOption4;
    TextView textViewQuestionNo, textViewScore;

    private List<QuizQuestion> questionList = new ArrayList<>();
    private int currentQuestionIndex = 0;

    private int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.pokemon);
        loadingSpinner = findViewById(R.id.loading_spinner);
        btnOption1 = findViewById(R.id.button1);
        btnOption2 = findViewById(R.id.button2);
        btnOption3 = findViewById(R.id.button3);
        btnOption4 = findViewById(R.id.button4);

        textViewQuestionNo = findViewById(R.id.textViewQuestionNo);
        textViewScore = findViewById(R.id.textViewScore);


        showLoading(true);
        generateQuizQuestions();



        btnOption1.setOnClickListener(v -> handleAnswerClick(btnOption1));
        btnOption2.setOnClickListener(v -> handleAnswerClick(btnOption2));
        btnOption3.setOnClickListener(v -> handleAnswerClick(btnOption3));
        btnOption4.setOnClickListener(v -> handleAnswerClick(btnOption4));



    }

    private void generateQuizQuestions() {
        for (int i = 0; i < 10; i++) {
            createQuizQuestion();
        }
    }
    private void createQuizQuestion() {
        int correctPokemonId = getRandomPokemonId();

        fetchPokemonData(correctPokemonId, (correctName, imageUrl) -> {
            List<String> options = new ArrayList<>();
            options.add(correctName);

            // Fetch three more random names for the options
            for (int i = 0; i < 3; i++) {
                int randomId = getRandomPokemonId();
                fetchPokemonData(randomId, (name, img) -> {
                    options.add(name);

                    if (options.size() == 4) {
                        // Shuffle options
                        Collections.shuffle(options);

                        // Now you have a complete question with 4 options
                        QuizQuestion question = new QuizQuestion(imageUrl, correctName, options);
                        questionList.add(question);
                        if (questionList.size() == 10) {
                            // Display the first question when all are ready
                            showLoading(false);
                            displayQuestion(0);
                        }
                    }
                });
            }
        });
    }
    private void displayQuestion(int questionIndex) {
        QuizQuestion question = questionList.get(questionIndex);

        // Load the image using Glide
        Glide.with(this).load(question.getImageUrl()).into(imageView);

        // Set the text of the buttons to the options
        btnOption1.setText(question.getOptions().get(0));
        btnOption2.setText(question.getOptions().get(1));
        btnOption3.setText(question.getOptions().get(2));
        btnOption4.setText(question.getOptions().get(3));
        imageView.setColorFilter(Color.argb(255, 0, 0, 0));

        btnOption1.setEnabled(true);
        btnOption2.setEnabled(true);
        btnOption3.setEnabled(true);
        btnOption4.setEnabled(true);

        resetButtonColors();

    }

//    private void checkAnswer(String selectedAnswer) {
//        QuizQuestion currentQuestion = questionList.get(currentQuestionIndex);
//
//        if (selectedAnswer.equals(currentQuestion.getCorrectName())) {
////            textViewScore.setText(String.valueOf(Integer.parseInt(textViewScore.getText().toString())+10));
//            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
//
//        } else {
//            Toast.makeText(this, "Wrong! The correct answer was: " + currentQuestion.getCorrectName(), Toast.LENGTH_SHORT).show();
//        }
//
//        // Move to the next question
//        currentQuestionIndex++;
//        String Qno = String.valueOf(currentQuestionIndex+1);
//        textViewQuestionNo.setText("Question: "+Qno+"/10");
//        if (currentQuestionIndex < questionList.size()) {
//            displayQuestion(currentQuestionIndex);
//        } else {
//            Toast.makeText(this, "Quiz Over!", Toast.LENGTH_LONG).show();
//        }
//    }
    private void handleAnswerClick(Button selectedButton) {
        QuizQuestion currentQuestion = questionList.get(currentQuestionIndex);
        String selectedAnswer = selectedButton.getText().toString();
        String correctAnswer = currentQuestion.getCorrectName();

        if (selectedAnswer.equals(correctAnswer)) {
            // Correct answer: Set selected button to green
            selectedButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            score = score+10;
            String scoreTotal = String.valueOf(score);
            textViewScore.setText("Score: "+scoreTotal);

        } else {
            // Wrong answer: Set selected button to red, correct button to green
            selectedButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            highlightCorrectAnswer(correctAnswer);
        }

        imageView.clearColorFilter();

        btnOption1.setEnabled(false);
        btnOption2.setEnabled(false);
        btnOption3.setEnabled(false);
        btnOption4.setEnabled(false);

        // Delay for 3 seconds before moving to the next question
        new Handler().postDelayed(() -> {

            currentQuestionIndex++;
            if (currentQuestionIndex < questionList.size()) {
                String Qno = String.valueOf(currentQuestionIndex+1);
                textViewQuestionNo.setText("Question: "+Qno+"/10");

                displayQuestion(currentQuestionIndex);


            } else {

                Intent intent = new Intent(GameActivity.this, ScoreBoardActivity.class);

                intent.putExtra("score",score);
                Log.d("GameActivity", "Navigating to ScoreboardActivity with score: " + score);

                startActivity(intent);
            }
        }, 1000); // 3000 milliseconds = 3 seconds
    }

    private void highlightCorrectAnswer(String correctAnswer) {
        // Highlight the correct answer in green
        if (btnOption1.getText().toString().equals(correctAnswer)) {
            btnOption1.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else if (btnOption2.getText().toString().equals(correctAnswer)) {
            btnOption2.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else if (btnOption3.getText().toString().equals(correctAnswer)) {
            btnOption3.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else if (btnOption4.getText().toString().equals(correctAnswer)) {
            btnOption4.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        }
    }

    private void resetButtonColors() {
        // Reset button colors to default
        btnOption1.setBackgroundColor(getResources().getColor(android.R.color.white));
        btnOption2.setBackgroundColor(getResources().getColor(android.R.color.white));
        btnOption3.setBackgroundColor(getResources().getColor(android.R.color.white));
        btnOption4.setBackgroundColor(getResources().getColor(android.R.color.white));
    }

    private int getRandomPokemonId() {
        Random random = new Random();
        return random.nextInt(898) + 1; // Pokémon IDs range from 1 to 898
    }

    private void fetchPokemonData(int pokemonId, OnPokemonFetchedListener listener) {
        String url = "https://pokeapi.co/api/v2/pokemon/" + pokemonId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        String name = response.getString("name");
                        JSONObject sprites = response.getJSONObject("sprites");
                        JSONObject other = sprites.getJSONObject("other");
                        JSONObject officialArtwork = other.getJSONObject("official-artwork");
                        String imageUrl = officialArtwork.getString("front_default");

                        // Pass the data to the listener
                        listener.onPokemonFetched(name, imageUrl);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(GameActivity.this, "Failed to load Pokémon data", Toast.LENGTH_SHORT).show();
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    interface OnPokemonFetchedListener {
        void onPokemonFetched(String name, String imageUrl);
    }

    // Method to show/hide the loading spinner
    private void showLoading(boolean isLoading) {
        if (isLoading) {
            loadingSpinner.setVisibility(View.VISIBLE);
            findViewById(R.id.quiz_layout).setVisibility(View.GONE);
        } else {
            loadingSpinner.setVisibility(View.GONE);
            findViewById(R.id.quiz_layout).setVisibility(View.VISIBLE);
        }
    }



}