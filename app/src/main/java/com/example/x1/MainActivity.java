package com.example.x1;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String currentPokemonUrl;
    private String currentPokemonName;
    private String id;


    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;

    private List<Pokemon> pokemonList;

    private List<PokemonStats> pokemonDetails;

    private Button btnNext, btnPrevious;

    private SearchView searchView;

    private int offset = 0;
    private static final int LIMIT = 50;
    private static final int MAX_OFFSET = 1300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        searchView = findViewById(R.id.searchView);

        pokemonList = new ArrayList<>();
        pokemonDetails = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pokemonAdapter = new PokemonAdapter(pokemonList);
        recyclerView.setAdapter(pokemonAdapter);
        pokemonAdapter.setOnItemClickListener(new PokemonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Pokemon selectedPokemon = pokemonList.get(position);
                PokemonStats selectedStats = pokemonDetails.get(position);

                // Create an Intent to start the new activity
                Intent intent = new Intent(MainActivity.this, PokemonDetailActivity.class);
                intent.putExtra("selectedPokemon", selectedPokemon);
                intent.putExtra("selectedStats", selectedStats);
                startActivity(intent);
            }
        });


        loadPokemonNames(offset, LIMIT);


        btnNext.setOnClickListener(v -> {
            offset += LIMIT;
            loadPokemonNames(offset, LIMIT); // Load next set of Pokémon
        });

        btnPrevious.setOnClickListener(v -> {
            if (offset > 0) {
                offset -= LIMIT;
                loadPokemonNames(offset, LIMIT); // Load previous set of Pokémon
            }
        });

    }



    private void loadPokemon(String pokemonUrl){
        String url = pokemonUrl;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        id = response.getString("id");
                        currentPokemonName = response.getString("name");

                        JSONObject sprites = response.getJSONObject("sprites");
                        JSONObject other = sprites.getJSONObject("other");
                        JSONObject officialArtwork = other.getJSONObject("official-artwork");

                        currentPokemonUrl = officialArtwork.getString("front_default");

//                        Log.d("PokemonName", currentPokemonName);
//                        Log.d("PokemonImage", currentPokemonUrl);

                        Pokemon pokemon = new Pokemon(id,currentPokemonName, currentPokemonUrl);
                        pokemonList.add(pokemon);

                        // Sort the list by Pokémon name
                        Collections.sort(pokemonList, Comparator.comparingInt(p -> Integer.parseInt(p.getId())));

                        JSONArray resultsArray = response.getJSONArray("stats");

                        JSONObject HpObject = resultsArray.getJSONObject(0);
                        String Hp = HpObject.getString("base_stat");

                        JSONObject AttackObject = resultsArray.getJSONObject(1);
                        String Attack = AttackObject.getString("base_stat");

                        JSONObject DefenseObject = resultsArray.getJSONObject(2);
                        String Defense = DefenseObject.getString("base_stat");

                        JSONObject SpecialAttackObject = resultsArray.getJSONObject(3);
                        String SpecialAttack = SpecialAttackObject.getString("base_stat");

                        JSONObject SpecialDefenseObject = resultsArray.getJSONObject(4);
                        String SpecialDefense = SpecialDefenseObject.getString("base_stat");

                        JSONObject SpeedObject = resultsArray.getJSONObject(5);
                        String Speed = SpeedObject.getString("base_stat");

                        PokemonStats stats = new PokemonStats(id, Hp,Attack,Defense,SpecialAttack,SpecialDefense,Speed);
                        pokemonDetails.add(stats);

                        pokemonAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void loadPokemonNames(int offset, int limit) {
        String url = "https://pokeapi.co/api/v2/pokemon?offset=" + offset + "&limit=" + limit; // Example API URL

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        // Get the 'results' array from the response
                        JSONArray resultsArray = response.getJSONArray("results");
                        pokemonList.clear();
                        for (int i = 0; i < resultsArray.length(); i++) {
                            JSONObject pokemonObject = resultsArray.getJSONObject(i);
                            String pokemonUrl = pokemonObject.getString("url");
                            loadPokemon(pokemonUrl);
                        }
                        pokemonAdapter.notifyDataSetChanged();
                        btnPrevious.setEnabled(offset > 0);
                        btnNext.setEnabled(offset < MAX_OFFSET);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }


}