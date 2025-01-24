package com.example.pmdmtarea03;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmdmtarea03.databinding.ItemPokemonBinding;

import java.util.ArrayList;

public class CapturadosAdapter extends RecyclerView.Adapter<CapturadosViewHolder> {

    private ArrayList<Pokemon> pokemonList;
    private NavController navController;

    public CapturadosAdapter(ArrayList<Pokemon> pokemonList, NavController navController) {
        this.pokemonList = pokemonList;
        this.navController = navController;
    }

    @NonNull
    @Override
    public CapturadosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CapturadosViewHolder(ItemPokemonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CapturadosViewHolder holder, int position) {
        Pokemon pokemon = this.pokemonList.get(position);
        holder.bind(pokemon,navController);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }
}
