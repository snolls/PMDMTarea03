package com.example.pmdmtarea03;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmdmtarea03.databinding.ItemCapturadosBinding;

public class CapturadosViewHolder extends RecyclerView.ViewHolder {

    private final ItemCapturadosBinding binding;
    public CapturadosViewHolder(ItemCapturadosBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Pokemon pokemon, NavController navController) {
        binding.tvname.setText(pokemon.getNombre());
        binding.tvnumero.setText(pokemon.getNumero());
        binding.tvaltura.setText(pokemon.getImagen());
        binding.tvpeso.setText(pokemon.getPeso());
    }
}
