package com.example.x1;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class PokemonDetailActivity extends AppCompatActivity {


    private ImageView imageView;

    private TextView textViewName,textViewHp,textViewAttack,textViewDefence,textViewSpecialAttack,textViewSpecialDefence,textViewSpeed;

    ProgressBar progressBarHp,progressBarAttack,progressBarDefense,progressBarSpecialAttack,progressBarSpecialDefense,progressBarSpeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pokemon_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pokemonDetails), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        imageView = findViewById(R.id.imageViewPokemon);
        textViewName = findViewById(R.id.textViewName);
        textViewHp = findViewById(R.id.textViewHp);
        textViewAttack = findViewById(R.id.textViewAttack);
        textViewDefence = findViewById(R.id.textViewDefense);
        textViewSpecialAttack = findViewById(R.id.textViewSpecialAttack);
        textViewSpecialDefence = findViewById(R.id.textViewSpecialDefence);
        textViewSpeed = findViewById(R.id.textViewSpeed);

        progressBarHp = findViewById(R.id.progressBarHP);
        progressBarAttack = findViewById(R.id.progressBarAttack);
        progressBarDefense = findViewById(R.id.progressBarDefense);
        progressBarSpecialAttack = findViewById(R.id.progressBarSpAttack);
        progressBarSpecialDefense = findViewById(R.id.progressBarSpDefense);
        progressBarSpeed = findViewById(R.id.progressBarSpeed);



        // Get data from intent
        Pokemon selectedPokemon = (Pokemon) getIntent().getSerializableExtra("selectedPokemon");
        PokemonStats selectedStats = (PokemonStats) getIntent().getSerializableExtra("selectedStats");

        // Set data to views
        textViewName.setText(selectedPokemon.getName().toUpperCase());
        textViewHp.setText(selectedStats.getHP());
        textViewAttack.setText(selectedStats.getAttack());
        textViewDefence.setText(selectedStats.getDefense());
        textViewSpecialAttack.setText( selectedStats.getSpecialAttack());
        textViewSpecialDefence.setText( selectedStats.getSpecialDefence());
        textViewSpeed.setText(selectedStats.getSpeed());
        Glide.with(this).load(selectedPokemon.getImageUrl()).into(imageView); // Load Pokémon image



        progressBarHp.setProgress(Integer.parseInt(selectedStats.getHP()));
        progressBarAttack.setProgress(Integer.parseInt(selectedStats.getAttack()));
        progressBarDefense.setProgress(Integer.parseInt(selectedStats.getDefense()));
        progressBarSpecialAttack.setProgress(Integer.parseInt(selectedStats.getSpecialAttack()));
        progressBarSpecialDefense.setProgress(Integer.parseInt(selectedStats.getSpecialDefence()));
        progressBarSpeed.setProgress(Integer.parseInt(selectedStats.getSpeed()));




    }


}