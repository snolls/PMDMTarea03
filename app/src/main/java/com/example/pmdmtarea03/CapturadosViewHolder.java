package com.example.pmdmtarea03;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmdmtarea03.databinding.ItemPokemonBinding;

public class CapturadosViewHolder extends RecyclerView.ViewHolder {

    private final ItemPokemonBinding binding;
    public CapturadosViewHolder(ItemPokemonBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Pokemon pokemon, NavController navController) {
        binding.tvname.setText(pokemon.getName());
        binding.tvnumero.setText(pokemon.getId());
    }
}
