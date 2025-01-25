package com.example.pmdmtarea03;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.pmdmtarea03.databinding.ItemPokemonBinding;
import java.util.List;

public class CapturadosAdapter extends RecyclerView.Adapter<CapturadosAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(PokemonCaptured pokemon);
    }

    private List<PokemonCaptured> pokemons;
    private OnItemClickListener listener;

    public CapturadosAdapter(List<PokemonCaptured> pokemons, OnItemClickListener listener) {
        this.pokemons = pokemons;
        this.listener = listener;
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
        PokemonCaptured pokemon = pokemons.get(position);

        holder.binding.tvname.setText(pokemon.getName().toUpperCase());
        holder.binding.tvnumero.setText(String.format("#%s", String.valueOf(pokemon.getOrder())));
        Glide.with(holder.binding.getRoot().getContext())
                .load(pokemon.getImage())
                .into(holder.binding.pokemonImage);

        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(pokemon);
            }
        });
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
