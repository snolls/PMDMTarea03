package com.example.pmdmtarea03;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.pmdmtarea03.databinding.ItemPokemonBinding;
import java.util.List;

public class CapturadosAdapter extends RecyclerView.Adapter<CapturadosAdapter.ViewHolder> {

    private List<Pokemon> pokemons;

    public CapturadosAdapter(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPokemonBinding binding = ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = pokemons.get(position);

        holder.binding.tvname.setText(pokemon.getName());
        holder.binding.tvnumero.setText(String.valueOf(pokemon.getOrder()));
        Glide.with(holder.binding.getRoot().getContext())
                .load(pokemon.getSprites().getOther().getHome().getFrontDefault())
                .into(holder.binding.pokemonImage);
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemPokemonBinding binding;

        public ViewHolder(@NonNull ItemPokemonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
