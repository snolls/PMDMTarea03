package com.example.pmdmtarea03;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pmdmtarea03.databinding.ItemPokemonBinding;

import java.util.ArrayList;
import java.util.List;

public class CapturadosAdapter extends RecyclerView.Adapter<CapturadosAdapter.ViewHolder> {

    public interface OnPokemonDeleteListener {
        void onPokemonDelete();
    }


    private final List<Pokemon> capturedPokemonList; // Lista de Pokémon capturados
    private final CapturadosAdapter.OnPokemonDeleteListener listener; // Callback al fragmento

    public CapturadosAdapter(List<Pokemon> capturedPokemonList, OnPokemonDeleteListener listener) {
        this.capturedPokemonList = capturedPokemonList;
        this.listener = listener;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Usa View Binding para inflar el layout
        ItemPokemonBinding binding = ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = capturedPokemonList.get(position);
        holder.binding.tvname.setText(pokemon.getName());
        holder.binding.tvnumero.setText(String.valueOf(pokemon.getOrder()));

        // Cargar la imagen con Glide o Picasso
        Glide.with(holder.binding.getRoot().getContext())
                .load(pokemon.getSprites().getOther().getHome().getFrontDefault())
                .into(holder.binding.pokemonImage);

    }

    @Override
    public int getItemCount() {
        return capturedPokemonList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // View Binding para acceder a las vistas
        final ItemPokemonBinding binding;

        public ViewHolder(@NonNull ItemPokemonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
