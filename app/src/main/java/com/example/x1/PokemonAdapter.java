package com.example.x1;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<Pokemon> pokemonList;
    private OnItemClickListener onItemClickListener;
//    private List<PokemonStats> pokemonStats;

    public PokemonAdapter(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
//        this.pokemonStats = pokemonStats;
    }

    @NonNull
    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon,parent,false);
        return new PokemonViewHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.PokemonViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.pokemonName.setText(pokemon.getName());
        holder.pokemonId.setText("# "+pokemon.getId());
        Glide.with(holder.itemView.getContext())
                .load(pokemon.getImageUrl()) // Assuming you have image URLs
                .into(holder.pokemonImage);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {

        return pokemonList.size();

    }

    static class PokemonViewHolder extends RecyclerView.ViewHolder{

        TextView pokemonName, pokemonId;
        ImageView pokemonImage;
        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemonId = itemView.findViewById(R.id.pokemon_id);
            pokemonName = itemView.findViewById(R.id.pokemon_name);
            pokemonImage = itemView.findViewById(R.id.pokemon_image);
        }


    }
}
